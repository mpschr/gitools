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
package org.gitools.matrix.format;

import org.gitools.api.PersistenceException;
import org.gitools.api.matrix.IMatrix;
import org.gitools.resource.AbstractResourceFormat;

public abstract class AbstractMatrixFormat extends AbstractResourceFormat<IMatrix> {


    protected AbstractMatrixFormat(String extension) {
        super(extension, IMatrix.class);
    }

    protected void checkLine(String[] line, String[] header, int count) throws PersistenceException {
        int lengthDiff = line.length - header.length;
        if (lengthDiff != 0) {
            throw new PersistenceException(
                    "Line <i>" + count + "</i> has <i>" +
                            Math.abs(lengthDiff) +
                            (Math.abs(lengthDiff) > 1 ? " columns " : " column ") +
                            (lengthDiff < 0 ? "less" : "more") +
                            "</i> than the header (which has " + (header.length + 2) + " fields)."
            );
        }
    }

}
