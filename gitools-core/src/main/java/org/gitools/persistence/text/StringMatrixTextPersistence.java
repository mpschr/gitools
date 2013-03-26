/*
 *  Copyright 2010 Universitat Pompeu Fabra.
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

package org.gitools.persistence.text;

import cern.colt.matrix.ObjectFactory1D;
import cern.colt.matrix.ObjectFactory2D;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import edu.upf.bg.csv.CSVReader;
import edu.upf.bg.progressmonitor.IProgressMonitor;
import org.gitools.matrix.model.StringMatrix;
import org.gitools.persistence.AbstractResourcePersistence;
import org.gitools.persistence.IResourceLocator;
import org.gitools.persistence.PersistenceException;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class StringMatrixTextPersistence
        extends AbstractResourcePersistence<StringMatrix> {

    @Override
    public StringMatrix read(IResourceLocator resourceLocator, IProgressMonitor monitor) throws PersistenceException {

        StringMatrix matrix = new StringMatrix();

        try {
            Reader reader = resourceLocator.getReader();
            CSVReader parser = new CSVReader(reader);

            // header
            String[] hdr = parser.readNext();
            int numColumns = hdr.length - 1;
            ObjectMatrix1D columns = ObjectFactory1D.dense.make(numColumns);
            for (int i = 0; i < numColumns; i++)
                columns.set(i, hdr[i + 1]);
            matrix.setColumns(columns);

            // body
            List<String> rawData = new ArrayList<String>();
            String[] fields;
            while ((fields = parser.readNext()) != null) {
                if (fields.length > hdr.length)
                    throw new PersistenceException("Number of fields greater than number of header fields at line " + parser.getLineNumber());

                for (int i = 0; i < fields.length; i++)
                    rawData.add(fields[i]);

                for (int i = 0; i < (hdr.length - fields.length); i++)
                    rawData.add(new String(""));
            }

            int numRows = rawData.size() / hdr.length;
            ObjectMatrix1D rows = ObjectFactory1D.dense.make(numRows);
            ObjectMatrix2D data = ObjectFactory2D.dense.make(numRows, numColumns);
            int offs = 0;
            for (int row = 0; row < numRows; row++) {
                rows.setQuick(row, rawData.get(offs++));
                for (int col = 0; col < numColumns; col++)
                    data.setQuick(row, col, rawData.get(offs++));
            }

            matrix.setRows(rows);
            matrix.setCells(data);

            rawData.clear();

            reader.close();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }

        return matrix;
    }

}
