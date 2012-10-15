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

package org.gitools.matrix.model;

import cern.colt.matrix.ObjectFactory1D;
import cern.colt.matrix.ObjectFactory2D;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import org.gitools.matrix.model.element.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

//TODO remove JAXB support
@XmlAccessorType(XmlAccessType.NONE)

public class ObjectMatrix extends BaseMatrix {

	private static final long serialVersionUID = 4077172838934816719L;
	
	protected ObjectMatrix2D cells;
	
	public ObjectMatrix() {
		super();
	}
	
	public ObjectMatrix(
			String title,
			ObjectMatrix1D rows,
			ObjectMatrix1D columns,
			ObjectMatrix2D cells,
			IElementAdapter cellAdapter) {
	    
		super(title, rows, columns, cellAdapter);
		
		this.cells = cells;
	}

    public ObjectMatrix(
            String title,
            String[] rowNames,
            String[] columnNames,
            IElementAdapter cellAdapter) {


        super(title, ObjectFactory1D.dense.make(rowNames), ObjectFactory1D.dense.make(columnNames), cellAdapter);

        makeCells(rowNames.length, columnNames.length);
    }

	// rows and columns
	
	@Override
	public int getRowCount() {
		return cells.rows();
	}
	
	@Override
	public int getColumnCount() {
		return cells.columns();
	}
	
	// cells
	
	public ObjectMatrix2D getCells() {
		return cells;
	}
	
	public void setCells(ObjectMatrix2D cells) {
		this.cells = cells;
	}
	
	@Override
	public Object getCell(int row, int column) {
		return cells.get(row, column);
	}
	
	public void setCell(int row, int column, Object cell) {
		cells.set(row, column, cell);
	}
	
	@Override
	public Object getCellValue(int row, int column, int property) {
		return cellAdapter.getValue(getCell(row, column), property);
	}

	@Override
	public void setCellValue(int row, int column, int property, Object value) {
		cellAdapter.setValue(getCell(row, column), property, value);
	}

	public void makeCells() {
		makeCells(rows.size(), columns.size());
	}

	@Override
	public void makeCells(int rows, int columns) {
		cells = ObjectFactory2D.dense.make(rows, columns);
        if (cellAdapter != null)  {

            IElementFactory elementFactory;
            if (cellAdapter instanceof BeanElementAdapter)
                elementFactory = new BeanElementFactory(cellAdapter.getElementClass());
            else
                elementFactory = new ArrayElementFactory(cellAdapter.getPropertyCount());

            for (int r = 0; r < rows ; r++) {
                for (int c = 0; c < columns; c++) {
                    setCell(r, c,elementFactory.create());
                }
            }
        }
	}
}
