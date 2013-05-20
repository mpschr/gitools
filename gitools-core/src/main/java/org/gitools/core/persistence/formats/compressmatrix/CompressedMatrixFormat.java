/*
 * #%L
 * gitools-core
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
package org.gitools.core.persistence.formats.compressmatrix;

import org.apache.commons.io.IOUtils;
import org.gitools.core.matrix.model.compressmatrix.AbstractCompressor;
import org.gitools.core.matrix.model.compressmatrix.CompressDimension;
import org.gitools.core.matrix.model.compressmatrix.CompressMatrix;
import org.gitools.core.matrix.model.compressmatrix.CompressRow;
import org.gitools.core.persistence.IResourceLocator;
import org.gitools.core.persistence.PersistenceException;
import org.gitools.core.persistence.formats.AbstractResourceFormat;
import org.gitools.utils.progressmonitor.IProgressMonitor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CompressedMatrixFormat extends AbstractResourceFormat<CompressMatrix> {

    public static final String EXTENSION = "cmatrix";

    public CompressedMatrixFormat() {
        super(EXTENSION, CompressMatrix.class);
    }

    @Override
    protected CompressMatrix readResource(IResourceLocator resourceLocator, IProgressMonitor progressMonitor) throws PersistenceException {

        try {
            DataInputStream in = new DataInputStream(resourceLocator.openInputStream(progressMonitor));

            // Dictionary
            byte[] dictionary = readBuffer(in);

            // Columns
            String[] columns = splitBuffer(readBuffer(in));

            // Rows
            String[] rows = splitBuffer(readBuffer(in));

            // Headers
            String[] headers = splitBuffer(readBuffer(in));

            // Values
            Map<Integer, CompressRow> values = new HashMap<Integer, CompressRow>(rows.length);

            for (int i = 0; i < rows.length; i++) {
                int row = in.readInt();
                int uncompressLength = in.readInt();
                values.put(row, new CompressRow(uncompressLength, readBuffer(in)));
            }

            in.close();

            CompressDimension rowDim = new CompressDimension("rows", 0, rows);
            CompressDimension colDim = new CompressDimension("columns", 1, columns);
            return new CompressMatrix(rowDim, colDim, dictionary, headers, values);

        } catch (IOException e) {
            throw new PersistenceException(e);
        }


    }

    @Override
    protected void writeResource(IResourceLocator resourceLocator, CompressMatrix resource, IProgressMonitor progressMonitor) throws PersistenceException {

        try {

            DataOutputStream out = new DataOutputStream(resourceLocator.openOutputStream());

            progressMonitor.begin("Writing dictionary...", 1);
            byte[] dictionary = resource.getDictionary();
            out.writeInt(dictionary.length);
            out.write(dictionary);

            progressMonitor.begin("Writing columns...", 1);
            byte[] buffer = AbstractCompressor.stringToByteArray(resource.getColumns().getLabels());
            out.writeInt(buffer.length);
            out.write(buffer);

            progressMonitor.begin("Writing rows...", 1);
            buffer = AbstractCompressor.stringToByteArray(resource.getRows().getLabels());
            out.writeInt(buffer.length);
            out.write(buffer);

            progressMonitor.begin("Writing headers...", 1);
            String[] headers = new String[resource.getLayers().size()];
            for (int i = 0; i < resource.getLayers().size(); i++) {
                headers[i] = resource.getLayers().get(i).getId();
            }
            buffer = AbstractCompressor.stringToByteArray(headers);
            out.writeInt(buffer.length);
            out.write(buffer);


            Map<Integer, CompressRow> compressRowMap = resource.getCompressRows();
            progressMonitor.begin("Writing values...", compressRowMap.size());
            for (Map.Entry<Integer, CompressRow> value : compressRowMap.entrySet()) {
                progressMonitor.worked(1);

                // The row position
                out.writeInt(value.getKey());

                // Compress the row
                CompressRow compressRow = value.getValue();

                // Write the length of the buffer before compression
                out.writeInt(compressRow.getNotCompressedLength());

                // The length of the compressed buffer with the columns
                out.writeInt(compressRow.getContent().length);

                // The buffer with all the columns
                out.write(compressRow.getContent());
            }

            out.close();

        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Read a byte array that starts with an integer that contains the buffer length to read.
     *
     * @param in the input stream
     * @return the byte array
     * @throws IOException
     */
    public static byte[] readBuffer(DataInputStream in) throws IOException {
        int length = in.readInt();
        return IOUtils.toByteArray(in, length);
    }

    private static Pattern TAB = Pattern.compile("\t");

    private static String[] splitBuffer(byte[] buffer) throws UnsupportedEncodingException {
        String line = new String(buffer, "UTF-8");
        return TAB.split(line);
    }

    public static String[] readHeader(File file) {

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));

            // Dictionary
            byte[] dictionary = readBuffer(in);

            // Columns
            String[] columns = splitBuffer(readBuffer(in));

            // Rows
            String[] rows = splitBuffer(readBuffer(in));

            // Headers
            String[] headers = splitBuffer(readBuffer(in));

            in.close();

            return headers;

        } catch (IOException e) {
            throw new PersistenceException(e);
        }


    }
}
