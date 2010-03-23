/*
 *  Copyright 2010 xavier.
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

import edu.upf.bg.progressmonitor.IProgressMonitor;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.gitools.biomart.cxf.AttributePage;
import org.gitools.biomart.cxf.DatasetInfo;
import org.gitools.biomart.cxf.Mart;
import org.gitools.biomart.cxf.Query;
import org.gitools.biomart.tablewriter.SequentialTableWriter;
import org.gitools.persistence.FileFormat;

public interface IBiomartService {

    List<AttributePage> getAttributes(Mart mart, DatasetInfo dataset) throws BiomartServiceException;

    List<DatasetInfo> getDatasets(Mart mart) throws BiomartServiceException;

    List<Mart> getRegistry() throws BiomartServiceException;

    InputStream queryAsStream(Query query, String format) throws BiomartServiceException;

    void queryModule(Query query, File file, String format, IProgressMonitor monitor) throws BiomartServiceException;

    void queryModule(Query query, SequentialTableWriter writer, IProgressMonitor monitor) throws BiomartServiceException;
    
    FileFormat[] getSupportedFormats();

    /**
     * query a table
     *
     * @param query
     * @param file
     * @param format
     * @param skipRowsWithEmptyValues whether skip or not rows having empty values
     * @param emptyValuesReplacement if empty values not skipped which value to use instead
     * @param monitor
     * @throws BiomartServiceException
     */
    void queryTable(Query query, File file, String format, boolean skipRowsWithEmptyValues, String emptyValuesReplacement, IProgressMonitor monitor) throws BiomartServiceException;

    void queryTable(Query query, SequentialTableWriter writer, boolean skipRowsWithEmptyValues, String emptyValuesReplacement, IProgressMonitor monitor) throws BiomartServiceException;

}