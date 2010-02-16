package org.gitools.ui.dialog.filter;

import edu.upf.bg.cutoffcmp.CutoffCmp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.gitools.matrix.filter.ValueFilterCriteria;

class ValueFilterCriteriaTableModel implements TableModel {

	private static final String[] columnName = new String[] {
		"Attribute", "Condition", "Value" };

	private static final Class<?>[] columnClass = new Class<?>[] {
		String.class, CutoffCmp.class, String.class };

	private Map<String, Integer> attrIndexMap = new HashMap<String, Integer>();

	private List<ValueFilterCriteria> criteriaList;

	private List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	
	public ValueFilterCriteriaTableModel(List<ValueFilterCriteria> criteriaList, String[] attributeNames) {
		this.criteriaList = criteriaList;
		for (int i = 0; i < attributeNames.length; i++)
			attrIndexMap.put(attributeNames[i], i);
	}

	public ValueFilterCriteriaTableModel(String[] attributeNames) {
		this(new ArrayList<ValueFilterCriteria>(), attributeNames);
	}

	@Override
	public int getRowCount() {
		return criteriaList.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnName[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClass[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0: return criteriaList.get(rowIndex).getAttributeName();
			case 1: return criteriaList.get(rowIndex).getComparator();
			case 2: return String.valueOf(criteriaList.get(rowIndex).getValue());
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				String attrName = (String) aValue;
				criteriaList.get(rowIndex).setAttributeName(attrName);
				Integer index = attrIndexMap.get(attrName);
				criteriaList.get(rowIndex).setAttributeIndex(index != null ? index : 0);
			break;

			case 1:
				criteriaList.get(rowIndex).setComparator((CutoffCmp) aValue);
			break;

			case 2:
				criteriaList.get(rowIndex).setValue(Double.parseDouble((String) aValue));
			break;
		}
	}

	public List<ValueFilterCriteria> getList() {
		return criteriaList;
	}

	public void addCriteria(final ValueFilterCriteria criteria) {
		criteriaList.add(criteria);
		fireCriteriaChanged();
	}

	void addAllCriteria(List<ValueFilterCriteria> list) {
		int initialRow = criteriaList.size();
		criteriaList.addAll(list);
		fireCriteriaChanged();
	}

	void removeCriteria(int[] selectedRows) {
		List<Object> objects = new ArrayList<Object>(selectedRows.length);
		for (int index : selectedRows)
			objects.add(criteriaList.get(index));

		criteriaList.removeAll(objects);
		fireCriteriaChanged();
	}

	private void fireCriteriaChanged() {
		TableModelEvent e = new TableModelEvent(this);
		for (TableModelListener l : listeners)
			l.tableChanged(e);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}
}