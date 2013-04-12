package org.gitools.matrix.model.compressmatrix;

import org.gitools.matrix.MatrixUtils;
import org.gitools.matrix.model.IMatrix;
import org.gitools.matrix.model.element.IElementAdapter;
import org.gitools.utils.progressmonitor.IProgressMonitor;

import java.util.HashMap;
import java.util.Map;

public class MatrixConversion extends AbstractCompressor
{

    public CompressMatrix convert(IMatrix inputMatrix, IProgressMonitor progressMonitor) throws Exception
    {

        if (inputMatrix instanceof CompressMatrix)
        {
            return (CompressMatrix) inputMatrix;
        }

        progressMonitor.end();
        initialize(new MatrixReader(inputMatrix, progressMonitor));

        CompressDimension rows = getRows();
        CompressDimension columns = getColumns();
        IElementAdapter cellAdapter = inputMatrix.getCellAdapter();

        Map<Integer, CompressRow> values = new HashMap<Integer, CompressRow>(inputMatrix.getRowCount());

        progressMonitor.begin("Compressing rows...", rows.size());
        int totalProperties = inputMatrix.getCellAttributes().size();
        for (int r=0; r < rows.size(); r++)
        {
            NotCompressRow notCompressRow = new NotCompressRow( columns );

            int row = inputMatrix.getRowIndex( rows.getLabel(r) );
            for (int c=0; c < columns.size(); c++)
            {
                int column = inputMatrix.getColumnIndex( columns.getLabel(c) );
                Object cell = inputMatrix.getCell(row, column);

                if (cell!=null)
                {
                    StringBuilder line = new StringBuilder(totalProperties*8);
                    line.append( inputMatrix.getColumnLabel(column) ).append(SEPARATOR);
                    line.append( inputMatrix.getRowLabel(row) ).append(SEPARATOR);
                    for (int p=0; p < totalProperties; p++)
                    {
                         line.append( MatrixUtils.doubleValue( cellAdapter.getValue(cell, p) ) ).append(SEPARATOR);
                    }
                    notCompressRow.append(line.toString());
                }

            }

            values.put( r,  compressRow( notCompressRow ));
            progressMonitor.worked(1);
            if (progressMonitor.isCancelled())
            {
                throw new RuntimeException("Cancelled by the user");
            }
        }

        return new CompressMatrix(
                getRows(),
                getColumns(),
                new CompressElementAdapter(
                        getDictionary(),
                        getHeader(),
                        values,
                        getColumns()
                )
        );
    }

    private static class MatrixReader implements IMatrixReader {

        private int numProperties;
        private int row;
        private int col;

        private IMatrix matrix;
        private IProgressMonitor progressMonitor;

        private MatrixReader(IMatrix matrix, IProgressMonitor progressMonitor)
        {
            progressMonitor.begin("Preparing compression...", matrix.getRowCount());
            this.matrix = matrix;
            this.row = -1;
            this.col = -1;
            this.numProperties = matrix.getCellAttributes().size();
            this.progressMonitor = progressMonitor;
        }

        @Override
        public String[] readNext()
        {
            String[] values = new String[numProperties + 2];

            if (row == matrix.getRowCount() )
            {
                return null;
            }

            if (row == -1)
            {
                for (int i=0; i < numProperties; i++)
                {
                    values[0] = "column";
                    values[1] = "row";
                    values[i + 2] = matrix.getCellAttributes().get(i).getId();
                }
                row++; col++;
            }
            else
            {
                for (int i=0; i < numProperties; i++)
                {
                    values[0] = matrix.getColumnLabel(col);
                    values[1] = matrix.getRowLabel(row);
                    values[i + 2] = Double.toString( MatrixUtils.doubleValue( matrix.getCellValue(row, col, i)));
                }
                col++;
            }

            if (col == matrix.getColumnCount())
            {
                row++;
                col = 0;
                progressMonitor.worked(1);
                if (progressMonitor.isCancelled())
                {
                    throw new RuntimeException("Cancelled by the user");
                }
            }

            return values;
        }
    }
}
