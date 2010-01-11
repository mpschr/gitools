package org.gitools.ui.actions.data;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

import org.gitools.ui.IconNames;
import org.gitools.ui.actions.BaseAction;
import org.gitools.ui.platform.AppFrame;

import org.gitools.matrix.MatrixUtils;
import org.gitools.heatmap.model.Heatmap;
import org.gitools.matrix.model.IMatrixView;
import org.gitools.ui.actions.ActionUtils;

public class FastSortRowsAction extends BaseAction {

	private static final long serialVersionUID = -582380114189586206L;

	public FastSortRowsAction() {
		super("Sort rows");
		
		setDesc("Sort rows");
		setSmallIconFromResource(IconNames.sortSelectedColumns16);
		setLargeIconFromResource(IconNames.sortSelectedColumns24);
		setMnemonic(KeyEvent.VK_S);
	}
	
	@Override
	public boolean isEnabledByModel(Object model) {
		return model instanceof Heatmap
			|| model instanceof IMatrixView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*List<SortCriteria> criteriaList = 
			new ArrayList<SortCriteria>(indices.length);
		
		for (int i = 0; i <  indices.length; i++)
			criteriaList.add(new SortCriteria(
					indices[i], selParamIndex, true));*/
		
		final IMatrixView matrixView = ActionUtils.getMatrixView();
		
		if (matrixView == null)
			return;

		final int propIndex = matrixView.getSelectedPropertyIndex();
		
		final int rowCount = matrixView.getRowCount();
		int[] selColumns = matrixView.getSelectedColumns();
		if (selColumns.length == 0) {
			selColumns = new int[matrixView.getColumnCount()];
			for (int i = 0; i < selColumns.length; i++)
				selColumns[i] = i;
		}
		
		final Integer[] indices = new Integer[rowCount];
		for (int i = 0; i < rowCount; i++)
			indices[i] = i;

		final int[] selectedColumns = selColumns;
		Arrays.sort(indices, new Comparator<Integer>() {
			@Override
			public int compare(Integer idx1, Integer idx2) {
				double se1 = 0, s1 = 0, ss1 = 0;
				double se2 = 0, s2 = 0, ss2 = 0;
				double m1 = 1.0, m2 = 1.0;
				
				int N = selectedColumns.length;
				
				int n1 = 0;
				int n2 = 0;
				
				for (int i = 0; i < N; i++) {
					int col = selectedColumns[i];
					
					Object value1 = matrixView.getCellValue(idx1, col, propIndex);
					double v1 = MatrixUtils.doubleValue(value1);

					if (!Double.isNaN(v1)) {
						m1 *= v1;
						s1 += v1;
						ss1 += v1 * v1;
						se1 += Math.exp(s1);
						n1++;
					}
					
					Object value2 = matrixView.getCellValue(idx2, col, propIndex);
					double v2 = MatrixUtils.doubleValue(value2);
					
					if (!Double.isNaN(v2)) {
						m2 *= v2;
						s2 += v2;
						ss2 += v2 * v2;
						se2 += Math.exp(s2);
						n2++;
					}
				}
				
				if (n1 == 0 || n2 == 0)
					return n1 == 0 ? 1 : -1;
				
				double var1 = (n1 * ss1) - (s1 * s1) / (n1 * n1);
				
				double var2 = (n2 * ss2) - (s2 * s2) / (n2 * n2);
				
				//int res = (int) Math.signum(se1/n1 - se2/n2);
				//int res = (int) Math.signum(se1 - se2);
				int res = (int) Math.signum(m1 - m2);
				return res != 0 ? res : (int) Math.signum(var1 - var2);
			}
		});
		
		final int[] visibleRows = matrixView.getVisibleRows();
		final int[] sortedVisibleRows = new int[rowCount];
		
		for (int i = 0; i < rowCount; i++)
			sortedVisibleRows[i] = visibleRows[indices[i]];
		
		matrixView.setVisibleRows(sortedVisibleRows);
		
		AppFrame.instance()
			.setStatusText("Rows sorted.");
	}
}
