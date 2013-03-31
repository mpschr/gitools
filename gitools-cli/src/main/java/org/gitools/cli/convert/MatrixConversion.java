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

package org.gitools.cli.convert;

import org.gitools.utils.progressmonitor.IProgressMonitor;
import org.gitools.matrix.model.BaseMatrix;
import org.gitools.matrix.model.DoubleBinaryMatrix;
import org.gitools.matrix.model.DoubleMatrix;
import org.gitools.persistence._DEPRECATED.MimeTypes;


public class MatrixConversion implements ConversionDelegate {

	@Override
	public Object convert(String srcMime, Object src, String dstMime, IProgressMonitor monitor) throws Exception {
		BaseMatrix srcMatrix = (BaseMatrix) src;

		BaseMatrix dstMatrix = null;

		Class<? extends BaseMatrix> cls = null;

		if (MimeTypes.DOUBLE_MATRIX.equals(dstMime))
			cls = DoubleMatrix.class;
		else if (MimeTypes.DOUBLE_BINARY_MATRIX.equals(dstMime)
				|| MimeTypes.GENE_MATRIX.equals(dstMime)
				|| MimeTypes.GENE_MATRIX_TRANSPOSED.equals(dstMime))
			cls = DoubleBinaryMatrix.class;
		else
			throw new Exception("Unsupported mime type for destination matrix: " + dstMime);

		int numCols = srcMatrix.getColumnCount();
		int numRows = srcMatrix.getRowCount();

		monitor.begin("Converting matrix ...", numRows);

		if (!cls.equals(src.getClass())) {
			dstMatrix = cls.newInstance();
			dstMatrix.makeCells(numRows, numCols);
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
					Object value = srcMatrix.getCellValue(row, col, 0);
					dstMatrix.setCellValue(row, col, 0, value);
				}

				monitor.worked(1);
			}

			dstMatrix.setColumns(srcMatrix.getColumns());
			dstMatrix.setRows(srcMatrix.getRows());
		}
		else
			dstMatrix = srcMatrix;

		monitor.end();
		
		return dstMatrix;
	}

}
