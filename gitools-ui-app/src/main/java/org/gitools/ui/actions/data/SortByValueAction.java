package org.gitools.ui.actions.data;

import edu.upf.bg.progressmonitor.IProgressMonitor;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import org.gitools.aggregation.IAggregator;

import org.gitools.ui.platform.actions.BaseAction;
import org.gitools.ui.platform.AppFrame;

import org.gitools.aggregation.AggregatorFactory;
import org.gitools.aggregation.MultAggregator;
import org.gitools.matrix.sort.SortCriteria;
import org.gitools.heatmap.model.Heatmap;
import org.gitools.matrix.model.IMatrixView;
import org.gitools.matrix.model.element.IElementAttribute;
import org.gitools.matrix.sort.MatrixViewSorter;
import org.gitools.ui.actions.ActionUtils;
import org.gitools.ui.dialog.sort.ValueSortDialog;
import org.gitools.ui.platform.progress.JobRunnable;
import org.gitools.ui.platform.progress.JobThread;

public class SortByValueAction extends BaseAction {

	private static final long serialVersionUID = -1582437709508438222L;
	
	public SortByValueAction() {
		super("Sort by value ...");

		setDesc("Sort by value ...");
	}
	
	@Override
	public boolean isEnabledByModel(Object model) {
		return model instanceof Heatmap
			|| model instanceof IMatrixView;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		final IMatrixView matrixView = ActionUtils.getMatrixView();
		if (matrixView == null)
			return;

		// Aggregators

		int aggrIndex = -1;
		IAggregator[] aggregators = AggregatorFactory.getAggregatorsArray();
		for (int i = 0; i < aggregators.length && aggrIndex == -1; i++)
			if (aggregators[i].getClass().equals(MultAggregator.class))
				aggrIndex = i;

		// Attributes

		int attrIndex = -1;

		List<IElementAttribute> cellProps = matrixView.getCellAdapter().getProperties();
		String[] attributeNames = new String[cellProps.size()];
		for (int i = 0; i < cellProps.size(); i++) {
			String name = cellProps.get(i).getName();
			attributeNames[i] = name;
			if (attrIndex == -1 && name.contains("p-value"))
				attrIndex = i;
		}

		if (attrIndex == -1)
			attrIndex = 0;

		// Default criteria

		List<SortCriteria> initialCriteria = new ArrayList<SortCriteria>(1);
		if (attributeNames.length > 0) {
			initialCriteria.add(new SortCriteria(
					attributeNames[attrIndex], attrIndex,
					aggregators[aggrIndex],
					SortCriteria.SortDirection.ASCENDING));
		}

		final ValueSortDialog dlg = new ValueSortDialog(
				AppFrame.instance(),
				attributeNames,
				aggregators,
				SortCriteria.SortDirection.values(),
				initialCriteria);
		dlg.setVisible(true);

		if (dlg.isCancelled())
			return;

		final List<SortCriteria> criteriaList = dlg.getCriteriaList();
		if (criteriaList.size() == 0) {
			AppFrame.instance().setStatusText("No criteria specified.");
			return;
		}

		JobThread.execute(AppFrame.instance(), new JobRunnable() {
			@Override
			public void run(IProgressMonitor monitor) {
				monitor.begin("Sorting ...", 1);

				SortCriteria[] criteriaArray =
						new SortCriteria[criteriaList.size()];

				MatrixViewSorter.sortByValue(matrixView,
						criteriaList.toArray(criteriaArray),
						dlg.isApplyToRowsChecked(),
						dlg.isApplyToColumnsChecked());

				monitor.end();
			}
		});

		AppFrame.instance().setStatusText("Sorted.");
	}
}