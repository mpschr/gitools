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
package org.gitools.matrix.model;

import org.gitools.matrix.model.element.IElementAdapter;
import org.gitools.matrix.model.element.IElementAttribute;
import org.gitools.persistence.IResource;

import java.util.List;

public interface IMatrix extends IResource
{

    /**
     * Total number of rows
     *
     * @return the row count
     */
    int getRowCount();

    /**
     * Gets the identifier label at the given position
     *
     * @param index the index
     * @return the row label
     */
    String getRowLabel(int index);

    /**
     * Gets the index position of this row identifier
     *
     * @param label the label
     * @return the row index
     */
    int getRowIndex(String label);

    /**
     * Total number of columns
     *
     * @return the column count
     */
    int getColumnCount();


    /**
     * Gets the identifier label at the given position
     *
     * @param index the index
     * @return the column label
     */
    String getColumnLabel(int index);

    /**
     * Gets the index position of this column identifier
     *
     * @param label the label
     * @return the column index
     */
    int getColumnIndex(String label);

    // cells
    @Deprecated
    Object getCell(int row, int column);

    Object getCellValue(int row, int column, int index);

    Object getCellValue(int row, int column, String id);

    void setCellValue(int row, int column, int index, Object value);

    void setCellValue(int row, int column, String id, Object value);

    @Deprecated
    IElementAdapter getCellAdapter();

    List<IElementAttribute> getCellAttributes();

    int getCellAttributeIndex(String id);

}
