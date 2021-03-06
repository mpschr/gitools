/*
 * #%L
 * gitools-biomart
 * %%
 * Copyright (C) 2013 Universitat Pompeu Fabra - Biomedical Genomics group
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.gitools.datasources.biomart.restful;

import org.gitools.api.analysis.IProgressMonitor;
import org.gitools.api.persistence.FileFormat;
import org.gitools.datasources.biomart.BiomartService;
import org.gitools.datasources.biomart.BiomartServiceException;
import org.gitools.datasources.biomart.queryhandler.BiomartQueryHandler;
import org.gitools.datasources.biomart.queryhandler.TsvFileQueryHandler;
import org.gitools.datasources.biomart.restful.model.*;
import org.gitools.datasources.biomart.settings.BiomartSource;
import org.gitools.ui.platform.settings.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class BiomartRestfulService implements BiomartService {

    private static final String FORMAT_TSV = "TSV";
    private static final String FORMAT_TSV_GZ = "GZ";

    private final FileFormat[] supportedFormats = new FileFormat[]{new FileFormat("Tab Separated Fields", "tsv", true, false), new FileFormat("Tab Separated Fields GZip compressed", "tsv.gz", true, false)};

    private final BiomartSource source;
    private final String restUrl;

    public BiomartRestfulService(BiomartSource source) throws BiomartServiceException {
        this.source = source;

        restUrl = composeUrl(source.getHost(), source.getPort(), source.getRestPath());
        //System.out.println(">>>>> " + restUrl);
    }

    /**
     * Method for building full url string from a host, port, and a destPath
     */

    private String composeUrl(String host, String port, String destPath) {

        StringBuilder sb = new StringBuilder();

        if (host != null && !host.isEmpty()) {
            sb.append("http://").append(host);
        }

        if (port != null && !port.isEmpty()) {
            sb.append(':').append(port);
        }

        if (destPath != null && !destPath.isEmpty()) {
            if (!destPath.startsWith("/")) {
                sb.append('/');
            }
            sb.append(destPath);
        }

        return sb.toString();
    }


    private <T> T xmlGET(String url, Class<T> responseClass) throws IOException, JAXBException {

        URL u = new URL(url);
        HttpURLConnection conn;
        conn = getHttpURLConnection(u);
        conn.setRequestMethod("GET");
        conn.connect();

        JAXBContext jc = JAXBContext.newInstance(responseClass);
        Unmarshaller unm = jc.createUnmarshaller();
        T response = (T) unm.unmarshal(conn.getInputStream());
        return (T) response;
    }

    private HttpURLConnection getHttpURLConnection(URL u) throws IOException {
        HttpURLConnection conn;
        if (Settings.get().isProxyEnabled()) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Settings.get().getProxyHost(), Settings.get().getProxyPort()));
            conn = (HttpURLConnection) u.openConnection(proxy);
        }  else {
            conn = (HttpURLConnection) u.openConnection();
        }
        return conn;
    }

    /**
     * Given a Biomart service and a data set produces a datasetConfig
     *
     * @param d
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws JAXBException
     */

    @Override
    public DatasetConfig getConfiguration(DatasetInfo d) throws BiomartServiceException {

        final String urlString = restUrl + "?type=configuration&dataset=" + d.getName() + "&virtualSchema=" + d.getInterface();
        DatasetConfig ds = null;

        try {
            ds = xmlGET(urlString, DatasetConfig.class);
        } catch (Throwable cause) {
            throw new BiomartServiceException(cause);
        }

        return ds;
    }

    @Override
    public List<MartLocation> getRegistry() throws BiomartServiceException {

        MartRegistry reg = null;
        final String urlString = restUrl + "?type=registry";

        try {
            reg = xmlGET(urlString, MartRegistry.class);
        } catch (Throwable cause) {
            throw new BiomartServiceException(cause);
        }

        if (reg == null) {
            return new ArrayList<MartLocation>(0);
        }

        return reg.getLocations();
    }


    @Override
    public List<DatasetInfo> getDatasets(MartLocation mart) throws BiomartServiceException {

        final String urlString = restUrl + "?type=datasets&mart=" + mart.getName();

        List<DatasetInfo> ds = new ArrayList<DatasetInfo>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = getHttpURLConnection(url);
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String s = null;
            DatasetInfo d = null;
            while ((s = reader.readLine()) != null) {
                if (!s.equals(" ") && !s.equals("\n")) {
                    String f[] = s.split("\t");
                    d = new DatasetInfo();
                    d.setType(f[0]);
                    d.setName(f[1]);
                    d.setDisplayName(f[2]);
                    d.setVisible(Integer.valueOf(f[3]));
                    d.setInterface(f[7]);
                    ds.add(d);
                }
            }
        } catch (Throwable cause) {
            throw new BiomartServiceException(cause);
        }

        return ds;
    }

    @Override
    public List<AttributePage> getAttributes(MartLocation mart, DatasetInfo dataset) throws BiomartServiceException {

        DatasetConfig dc = getConfiguration(dataset);
        return dc.getAttributePages();
    }

    @Override
    public List<FilterPage> getFilters(MartLocation mart, DatasetInfo dataset) throws BiomartServiceException {

        DatasetConfig dc = getConfiguration(dataset);

        if (dc.getFilterPages() != null && dc.getFilterPages().size() > 0) {
            return dc.getFilterPages();
        } else {
            return new ArrayList<FilterPage>();
        }
    }

    //FIXME Use JAXB !!!
    //FIXME review filter xml parsing
    private String createQueryXml(Query query, String format, boolean encoded) {

        StringWriter sw = new StringWriter();
        sw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE Query>");
        sw.append("<Query");
        sw.append(" virtualSchemaName=\"").append(query.getVirtualSchemaName()).append('"');
        sw.append(" header=\"").append("" + query.getHeader()).append('"');
        sw.append(" uniqueRows=\"").append("" + query.getUniqueRows()).append('"');
        sw.append(" count=\"").append("" + query.getCount()).append('"');
        sw.append(" formatter=\"").append(format).append('"');
        sw.append(" datasetConfigVersion=\"0.7\">");

        for (Dataset ds : query.getDatasets()) {
            sw.append("<Dataset");
            sw.append(" name=\"").append(ds.getName()).append('"');
            sw.append(" interface=\"default\">");
            for (Attribute attr : ds.getAttribute())
                sw.append("<Attribute name=\"").append(attr.getName()).append("\" />");

            for (Filter flt : ds.getFilter()) {
                if (flt.getValue() != null && !flt.getValue().equals("")) {

                    sw.append("<Filter name=\"").append(flt.getName()).append("\" ");

                    if (flt.getRadio()) {
                        sw.append("excluded=\"").append(flt.getValue()).append("\"");
                    } else {
                        sw.append("value=\"").append(flt.getValue()).append("\"");
                    }
                    sw.append(" />");
                }
            }

            sw.append("</Dataset>");
        }
        sw.append("</Query>");

        //System.out.println(sw.toString());

        if (encoded) {
            try {
                return URLEncoder.encode(sw.toString(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                return sw.toString();
            }
        } else {
            return sw.toString();
        }
    }

    @Override
    public InputStream queryAsStream(Query query, String format) throws BiomartServiceException {
        final String queryString = createQueryXml(query, format, true);
        final String urlString = restUrl + "?query=" + queryString;

        //System.out.println(">>> " + urlString);
        //System.out.println(createQueryXml(query, format, false));

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = getHttpURLConnection(url);
            conn.setRequestMethod("GET");
            conn.connect();
            return conn.getInputStream();
        } catch (Exception ex) {
            throw new BiomartServiceException("Error opening connection with Biomart service", ex);
        }
    }

    @Override
    public void queryModule(Query query, File file, String format, IProgressMonitor monitor) throws BiomartServiceException {
        BiomartQueryHandler tableWriter = null;

        if (format.equals(FORMAT_TSV) || format.equals(FORMAT_TSV_GZ)) {
            tableWriter = new TsvFileQueryHandler(file, format.equals(FORMAT_TSV_GZ));
        }

        if (tableWriter == null) {
            throw new BiomartServiceException("Unrecognized format: " + format);
        }

        queryModule(query, tableWriter, monitor);

        if (monitor.isCancelled()) {
            file.delete();
        }
    }

    @Override
    public void queryModule(Query query, BiomartQueryHandler writer, IProgressMonitor monitor) throws BiomartServiceException {
        InputStream in = queryAsStream(query, FORMAT_TSV);

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        try {
            writer.begin();
        } catch (Exception ex) {
            throw new BiomartServiceException(ex);
        }

        TimeCounter speedTimer = new TimeCounter();
        long speedBytes = 0;

        try {
            String next = null;
            while ((next = br.readLine()) != null && !monitor.isCancelled()) {
                String[] fields = next.split("\t");
                if (fields.length == 2 && !fields[0].isEmpty() && !fields[1].isEmpty()) {
                    writer.line(fields);
                }

                speedBytes += next.length();
                double seconds = speedTimer.getElapsedSeconds();
                if (seconds >= 1.0) {
                    double speed = (speedBytes / 1024.0) / seconds;
                    monitor.info(String.format("%.1f Kb/s", speed));
                    speedBytes = 0;
                    speedTimer.reset();
                }
            }
        } catch (Exception ex) {
            throw new BiomartServiceException("Error parsing Biomart query results.", ex);
        } finally {
            writer.end();
        }

        //log.info("queryModule: elapsed time " + time.toString());
    }

    /**
     * query a table
     *
     * @param query
     * @param file
     * @param format
     * @param skipRowsWithEmptyValues whether skip or not rows having empty values
     * @param emptyValuesReplacement  if empty values not skipped which value to use instead
     * @param monitor
     * @throws BiomartServiceException
     */
    @Override
    public void queryTable(Query query, File file, String format, boolean skipRowsWithEmptyValues, String emptyValuesReplacement, IProgressMonitor monitor) throws BiomartServiceException {

        BiomartQueryHandler tableWriter = null;
        if (format.equals(FORMAT_TSV) || format.equals(FORMAT_TSV_GZ)) {
            tableWriter = new TsvFileQueryHandler(file, format.equals(FORMAT_TSV_GZ));
        }

        if (tableWriter == null) {
            throw new BiomartServiceException("Unrecognized format: " + format);
        }

        queryTable(query, tableWriter, skipRowsWithEmptyValues, emptyValuesReplacement, monitor);

        if (monitor.isCancelled()) {
            file.delete();
        }
    }

    @Override
    public void queryTable(Query query, BiomartQueryHandler writer, boolean skipRowsWithEmptyValues, String emptyValuesReplacement, IProgressMonitor monitor) throws BiomartServiceException {

        TimeCounter time = new TimeCounter();

        InputStream in = queryAsStream(query, FORMAT_TSV);

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        try {
            writer.begin();
        } catch (Exception ex) {
            throw new BiomartServiceException(ex);
        }

        TimeCounter speedTimer = new TimeCounter();
        long speedBytes = 0;

        try {
            String next = null;
            while ((next = br.readLine()) != null && !monitor.isCancelled()) {
                String[] fields = next.split("\t");

                boolean hasEmptyValues = false;
                for (int i = 0; i < fields.length; i++) {
                    hasEmptyValues |= fields[i].isEmpty();
                    if (fields[i].isEmpty()) {
                        fields[i] = emptyValuesReplacement;
                    }
                }

                speedBytes += next.length();
                double seconds = speedTimer.getElapsedSeconds();
                if (seconds >= 1.0) {
                    double speed = (speedBytes / 1024.0) / seconds;
                    monitor.info(String.format("%.1f Kb/s", speed));
                    speedBytes = 0;
                    speedTimer.reset();
                }

                if (!(skipRowsWithEmptyValues && hasEmptyValues)) {
                    writer.line(fields);
                }
            }
        } catch (Exception ex) {
            throw new BiomartServiceException("Error parsing Biomart query results.", ex);
        } finally {
            writer.end();
        }
    }
}
