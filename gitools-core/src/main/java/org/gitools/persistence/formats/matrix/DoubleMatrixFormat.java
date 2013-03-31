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

package org.gitools.persistence.formats.matrix;

import org.gitools.utils.progressmonitor.IProgressMonitor;
import org.gitools.matrix.model.DoubleMatrix;
import org.gitools.persistence._DEPRECATED.FileSuffixes;
import org.gitools.persistence.IResourceLocator;
import org.gitools.persistence._DEPRECATED.MimeTypes;
import org.gitools.persistence.PersistenceException;

public class DoubleMatrixFormat extends AbstractTextMatrixFormat<DoubleMatrix> {

    public DoubleMatrixFormat() {
        super(FileSuffixes.DOUBLE_MATRIX, MimeTypes.DOUBLE_MATRIX, DoubleMatrix.class);
    }

    public DoubleMatrixFormat(String extension, int skipLines, Integer... skipColumns) {
        super(extension, MimeTypes.DOUBLE_MATRIX, DoubleMatrix.class, skipLines, skipColumns);
    }

    @Override
    protected DoubleMatrix createEntity() {
        return new DoubleMatrix();
    }

    @Override
    protected DoubleMatrix readResource(IResourceLocator resourceLocator, IProgressMonitor progressMonitor) throws PersistenceException {
        return read(resourceLocator, getValueTranslator(), progressMonitor);
    }

    @Override
    protected void writeResource(IResourceLocator resourceLocator, DoubleMatrix doubleMatrix, IProgressMonitor progressMonitor) throws PersistenceException {
        write(resourceLocator, doubleMatrix, getValueTranslator(), progressMonitor);
    }
}
