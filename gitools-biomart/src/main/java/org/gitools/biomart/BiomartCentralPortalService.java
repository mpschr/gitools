/*
 *  Copyright 2009 chris.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.gitools.biomart;

import org.gitools.biomart.tablewriter.SequentialTableWriter;
import edu.upf.bg.benchmark.TimeCounter;
import edu.upf.bg.progressmonitor.IProgressMonitor;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import org.biomart._80.martservicesoap.Attribute;
import org.biomart._80.martservicesoap.AttributePage;
import org.biomart._80.martservicesoap.BioMartException_Exception;
import org.biomart._80.martservicesoap.BioMartSoapService;
import org.biomart._80.martservicesoap.Dataset;
import org.biomart._80.martservicesoap.DatasetInfo;
import org.biomart._80.martservicesoap.Mart;
/*import org.gitools.biomart.cxf.Attribute;
import org.gitools.biomart.cxf.AttributePage;
import org.gitools.biomart.cxf.BioMartException_Exception;
import org.gitools.biomart.cxf.BioMartSoapService;
import org.gitools.biomart.cxf.Dataset;
import org.gitools.biomart.cxf.DatasetInfo;
import org.gitools.biomart.cxf.Mart;
import org.gitools.biomart.cxf.MartServiceSoap;
import org.gitools.biomart.cxf.Query;*/import org.biomart._80.martservicesoap.MartServiceSoap;
import org.biomart._80.martservicesoap.Query;

import org.gitools.biomart.tablewriter.TsvTableWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.gitools.persistence.FileFormat;

@Deprecated
public class BiomartCentralPortalService /*implements IBiomartService*/ {

	private static final Logger log = LoggerFactory.getLogger(BiomartCentralPortalService.class.getName());

	public static final String FORMAT_TSV = "TSV";
	public static final String FORMAT_TSV_GZ = "GZ";

	private FileFormat[] supportedFormats = new FileFormat[] {
			new FileFormat("Tab Separated Fields (tsv)", "tsv", FORMAT_TSV),
			new FileFormat("Tab Separated Fields GZip compressed (tsv.gz)", "tsv.gz", FORMAT_TSV_GZ),
		};

	private static final String defaultRestUrl = "http://www.biomart.org/biomart/martservice";

	public static final String defaultWsdlUrl = "/wsdl/mart.wsdl";
        //public static final String defaultWsdlUrl = "http://www.biomart.org/biomart/martwsdl";

	private static BiomartCentralPortalService instance;

	private MartServiceSoap port;

	private final BiomartConfiguration conf;
	//private String restUrl;

	protected BiomartCentralPortalService(BiomartConfiguration conf) {
		this.conf = conf;
		//this.restUrl = conf.getRestUrl();
	}

	public static final BiomartCentralPortalService getDefault() {
		if (instance == null)
			instance = createDefaultService();
		return instance;
	}

	private static BiomartCentralPortalService createDefaultService() {
		BiomartCentralPortalService service = new BiomartCentralPortalService(
				new BiomartConfiguration(defaultWsdlUrl, defaultRestUrl));

		return service;
	}

	private static MartServiceSoap createPort(URL wsdlUrl) {
		BioMartSoapService service = new BioMartSoapService(wsdlUrl);
		return service.getBioMartSoapPort();
	}

	private MartServiceSoap getMartPort() {
		if (port == null)
			port = createPort(this.getClass().getResource(conf.getWdslUrl()));

		return port;
	}

	private String createQueryXml(Query query, String format, boolean encoded) {
		/*JAXBContext context = JAXBContext.newInstance(Query.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		m.setProperty(Marshaller.JAXB_FRAGMENT, false);
		m.marshal(query, sw);
		sw.close();*/

		StringWriter sw = new StringWriter();
		sw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE Query>");
		sw.append("<Query");
		sw.append(" virtualSchemaName=\"").append(query.getVirtualSchemaName()).append('"');
		sw.append(" header=\"").append("" + query.getHeader()).append('"');
		sw.append(" uniqueRows=\"").append("" + query.getUniqueRows()).append('"');
		sw.append(" count=\"").append("" + query.getCount()).append('"');
		sw.append(" formatter=\"").append(format).append('"');
		sw.append(" datasetConfigVersion=\"0.7\">");
		for (Dataset ds : query.getDataset()) {
			sw.append("<Dataset");
			sw.append(" name=\"").append(ds.getName()).append('"');
			sw.append(" interface=\"default\">");
			for (Attribute attr : ds.getAttribute())
				sw.append("<Attribute name=\"").append(attr.getName()).append("\" />");
			sw.append("</Dataset>");
		}
		sw.append("</Query>");

		if (encoded) {
			try {
				return URLEncoder.encode(sw.toString(), "UTF-8");
			}
			catch (UnsupportedEncodingException ex) {
				return null;
			}
		}
		else
			return sw.toString();
	}

    //@Override
	public FileFormat[] getSupportedFormats() {
		return supportedFormats;
	}

    //@Override
	public InputStream queryAsStream(Query query, String format) throws BiomartServiceException {
		final String queryString = createQueryXml(query, format, true);
		final String urlString = conf.getRestUrl() + "?query=" + queryString;

		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			return conn.getInputStream();
		}
		catch (Exception ex) {
			throw new BiomartServiceException("Error opening connection with Biomart service", ex);
		}
	}

    //@Override
	public void queryModule(Query query, File file, String format, IProgressMonitor monitor) throws BiomartServiceException {
		SequentialTableWriter tableWriter = null;
		if (format.equals(FORMAT_TSV) || format.equals(FORMAT_TSV_GZ))
			tableWriter = new TsvTableWriter(file, format.equals(FORMAT_TSV_GZ));

		if (tableWriter == null)
			throw new BiomartServiceException("Unrecognized format: " + format);

		queryModule(query, tableWriter, monitor);

		if (monitor.isCancelled())
			file.delete();
	}

    //@Override
	public void queryModule(Query query, SequentialTableWriter writer, IProgressMonitor monitor) throws BiomartServiceException {
		TimeCounter time = new TimeCounter();

		InputStream in = queryAsStream(query, FORMAT_TSV);
		/*try {
			in = new GZIPInputStream(in);
		}
		catch (IOException ex) {
			throw new BiomartServiceException(ex);
		}*/

		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		try {
			writer.open();
		}
		catch (Exception ex) {
			throw new BiomartServiceException(ex);
		}

		int count = 0;
		try {
			String next = null;
			while ((next = br.readLine()) != null && !monitor.isCancelled()) {
				String[] fields = next.split("\t");
				if (fields.length == 2
						&& !fields[0].isEmpty()
						&& !fields[0].isEmpty())

					writer.write(fields);

				if ((++count % 1000) == 0)
					monitor.info(count + " rows read");
			}
		}
		catch (Exception ex) {
			throw new BiomartServiceException("Error parsing Biomart query results.", ex);
		}
		finally {
			writer.close();
		}

		log.info("queryModule: elapsed time " + time.toString());
	}

	/** query a table
	 *
	 * @param query
	 * @param file
	 * @param format
	 * @param skipRowsWithEmptyValues whether skip or not rows having empty values
	 * @param emptyValuesReplacement if empty values not skipped which value to use instead
	 * @param monitor
	 * @throws BiomartServiceException
	 */
    //@Override
	public void queryTable(Query query, File file, String format,
			boolean skipRowsWithEmptyValues,
			String emptyValuesReplacement,
			IProgressMonitor monitor) throws BiomartServiceException {
		SequentialTableWriter tableWriter = null;
		if (format.equals(FORMAT_TSV) || format.equals(FORMAT_TSV_GZ))
			tableWriter = new TsvTableWriter(file, format.equals(FORMAT_TSV_GZ));

		if (tableWriter == null)
			throw new BiomartServiceException("Unrecognized format: " + format);

		queryTable(query, tableWriter,
				skipRowsWithEmptyValues, emptyValuesReplacement, monitor);

		if (monitor.isCancelled())
			file.delete();
	}

    //@Override
	public void queryTable(Query query, SequentialTableWriter writer,
			boolean skipRowsWithEmptyValues,
			String emptyValuesReplacement,
			IProgressMonitor monitor) throws BiomartServiceException {
		TimeCounter time = new TimeCounter();

		InputStream in = queryAsStream(query, FORMAT_TSV);
		/*try {
			in = new GZIPInputStream(in);
		}
		catch (IOException ex) {
			throw new BiomartServiceException(ex);
		}*/

		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		try {
			writer.open();
		}
		catch (Exception ex) {
			throw new BiomartServiceException(ex);
		}

		int count = 0;
		try {
			String next = null;
			while ((next = br.readLine()) != null && !monitor.isCancelled()) {
				String[] fields = next.split("\t");

				boolean hasEmptyValues = false;
				for (int i = 0; i < fields.length; i++) {
					hasEmptyValues |= fields[i].isEmpty();
					if (fields[i].isEmpty())
						fields[i] = emptyValuesReplacement;
				}

				if (!(skipRowsWithEmptyValues && hasEmptyValues)) {
					writer.write(fields);
					if ((++count % 1000) == 0)
						monitor.info(count + " rows read");
				}
			}
		}
		catch (Exception ex) {
			throw new BiomartServiceException("Error parsing Biomart query results.", ex);
		}
		finally {
			writer.close();
		}

		if (monitor.isCancelled())
			log.info("queryModule: cancelled with " + count + " rows");
		else
			log.info("queryModule: " + count + " rows in " + time.toString());
	}

    //@Override
	public List<Mart> getRegistry() throws BiomartServiceException {
		try {
			return getMartPort().getRegistry();
		}
		catch (BioMartException_Exception ex) {
			throw new BiomartServiceException(ex);
		}
	}

    //@Override
	public List<DatasetInfo> getDatasets(Mart mart) throws BiomartServiceException {
		try {
			return port.getDatasets(mart.getName());
		}
		catch (BioMartException_Exception ex) {
			throw new BiomartServiceException(ex);
		}
	}

    //@Override
	public List<AttributePage> getAttributes(Mart mart, DatasetInfo dataset) throws BiomartServiceException {
		try {
			return getMartPort().getAttributes(
				dataset.getName(), mart.getServerVirtualSchema());
		}
		catch (BioMartException_Exception ex) {
			throw new BiomartServiceException(ex);
		}
	}
}