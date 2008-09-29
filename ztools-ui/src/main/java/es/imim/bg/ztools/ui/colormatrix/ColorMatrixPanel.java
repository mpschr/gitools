package es.imim.bg.ztools.ui.colormatrix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class ColorMatrixPanel extends JPanel {

	private static final long serialVersionUID = 1122420366217373359L;
	
	public enum SelectionMode {
		columns, rows, cells
	}

	public interface ColorMatrixListener {
		void selectionChanged();
	}
	
	private class ColorMatrixModelAddapter implements TableModel {

		private ColorMatrixModel model;
		
		public ColorMatrixModelAddapter(ColorMatrixModel model) {
			this.model = model;
		}
		
		public int getRowCount() {
			return model.getRowCount();
		}
		
		public int getColumnCount() {
			return model.getColumnCount() + 1;
		}

		public String getColumnName(int col) {
			return col < model.getColumnCount() ? 
					model.getColumnName(col) : " ";
		}

		public Object getValueAt(int row, int col) {
			return col < model.getColumnCount() ?
					model.getValue(row, col) :
					model.getRowName(row);
		}
		
		public void setValueAt(Object value, int row, int col) {
		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}

		public void addTableModelListener(TableModelListener arg0) {
		}
		
		public void removeTableModelListener(TableModelListener arg0) {
		}
		
		public Class<?> getColumnClass(int col) {
			return col < model.getColumnCount() ? 
					Double.class : String.class;
		}
	}
	
	public class TableCellRendererAdapter implements TableCellRenderer {
		
		private ColorMatrixCellDecorator decorator;
		
		private DefaultTableCellRenderer tableRenderer = 
			new DefaultTableCellRenderer();
		
		public TableCellRendererAdapter(ColorMatrixCellDecorator decorator) {
			this.decorator = decorator;
		}
		
		public Component getTableCellRendererComponent(
				JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) {
			
			JLabel label = (JLabel) tableRenderer
					.getTableCellRendererComponent(
							table, value, isSelected, hasFocus, row, column);
			
			configureRenderer(tableRenderer, value);
			
			if (isSelected)
				label.setBackground(label.getBackground().darker());
			
			return label;
		}

		private void configureRenderer(
				JLabel label,
				Object value) {

			CellDecoration decoration = new CellDecoration();
			decorator.decorate(decoration, (Double) value);
			label.setText(decoration.getText());
			label.setToolTipText(decoration.getToolTip());
			label.setForeground(decoration.getFgColor());
			label.setBackground(decoration.getBgColor());

			switch (decoration.textAlign) {
			case left: label.setHorizontalAlignment(SwingConstants.LEFT); break;
			case right: label.setHorizontalAlignment(SwingConstants.RIGHT); break;
			case center: label.setHorizontalAlignment(SwingConstants.CENTER); break;
			}
			
		}
	}

	public class RotatedTableCellRenderer
			extends JLabel implements TableCellRenderer {

		private static final long serialVersionUID = 8878769979396041532L;

		protected static final double radianAngle = (-90.0 / 180.0) * Math.PI;
		
		protected boolean highlightSelected;
		
		protected boolean isSelected;
		
		public RotatedTableCellRenderer(boolean highlightSelected) {
			this.highlightSelected = highlightSelected;
		}

		public Component getTableCellRendererComponent(
				JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) {
			
			this.setText(value.toString());
			
			int[] selColumns = table.getSelectedColumns();
			Arrays.sort(selColumns);
			int i = Arrays.binarySearch(selColumns, column);
			
			//System.out.println(column + " : " + i + " : cols:" + Arrays.toString(selColumns));
			
			this.isSelected = i >= 0;
			
			return this;
		}

		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			final int w = this.getWidth();
			final int h = this.getHeight();
			
			g2.setClip(0, 0, w, h);
			g2.setFont(this.getFont());
			
			if (highlightSelected && isSelected)
				g2.setBackground(Color.ORANGE);
			
			g2.clearRect(0, 0, w, h);
			
			g2.setColor(Color.GRAY);
			g2.drawRect(0, 0, w, h);
			
			AffineTransform at = new AffineTransform();
			at.setToTranslation(this.getWidth(), this.getHeight());
			g2.transform(at);
			at.setToRotation(radianAngle);
			g2.transform(at);
			
			Rectangle2D r = g2.getFontMetrics().getStringBounds(this.getText(), g2);
			float textHeight = (float) r.getHeight();

			g2.setColor(Color.BLACK);
			g2.drawString(this.getText(), 4.0f, -(w + 8 - textHeight) / 2);
		}
	}
	
	private JTable table;
	
	private SelectionMode selMode;
	
	private int columnsHeight;
	private int columnsWidth;
	private int rowsHeight;
	
	private int selectedLeadColumn;
	private int selectedLeadRow;
	
	private List<ColorMatrixListener> listeners;
	
	public ColorMatrixPanel() {
	
		this.selMode = SelectionMode.cells;
		
		this.columnsHeight = 160;
		this.columnsWidth = 30;
		this.rowsHeight = 30;
	
		this.selectedLeadColumn = this.selectedLeadRow = -1;
	
		this.listeners = new ArrayList<ColorMatrixListener>(1);
		
		createComponents();
	}
	
	private void createComponents() {
		
		table = new JTable();
		
		table.setFillsViewportHeight(true);
		
		table.getTableHeader().setPreferredSize(new Dimension(columnsWidth, columnsHeight));
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionBackground(Color.ORANGE);
		
		final ListSelectionListener listener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedLeadRow = table.getSelectionModel().getLeadSelectionIndex();
				selectedLeadColumn = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
				
				for (ColorMatrixListener listener : listeners)
					listener.selectionChanged();
				
				refresh();
			}
		};
		
		table.getSelectionModel().addListSelectionListener(listener);
		table.getColumnModel().getSelectionModel().addListSelectionListener(listener);
		
		refreshSelectionMode();
		
		final JScrollPane scroll = new JScrollPane(table);
		
		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
	}
	
	private void refreshSelectionMode() {
		switch(selMode) {
		case columns:
			table.setRowSelectionAllowed(false);
			table.setColumnSelectionAllowed(true);
			break;
		case rows:
			table.setRowSelectionAllowed(true);
			table.setColumnSelectionAllowed(false);
			break;
		case cells:
			table.setRowSelectionAllowed(false);
			table.setColumnSelectionAllowed(false);
			break;
		}
		
		refreshTableColumnsRenderer();
	}
	
	private void refreshTableColumnsWidth() {
		TableColumnModel colModel = table.getColumnModel();
		final int lastColumn = colModel.getColumnCount() - 1;
		for (int i = 0; i < lastColumn; i++) {
			TableColumn col = colModel.getColumn(i);
			col.setPreferredWidth(columnsWidth);
		}
		TableColumn col = colModel.getColumn(lastColumn);
		col.setResizable(true);
		col.setMinWidth(400);
	}
	
	private void refreshTableColumnsRenderer() {
		TableColumnModel colModel = table.getColumnModel();
		final int lastColumn = colModel.getColumnCount();
		for (int i = 0; i < lastColumn; i++) {
			TableColumn col = colModel.getColumn(i);
			col.setHeaderRenderer(
					new RotatedTableCellRenderer(
							selMode == SelectionMode.columns));
		}
	}
	
	public void refresh() {
		table.repaint();
		table.getTableHeader().repaint();
	}
	
	public void setModel(ColorMatrixModel model) {
		table.setModel(new ColorMatrixModelAddapter(model));

		refreshTableColumnsRenderer();
		refreshTableColumnsWidth();
		table.setRowHeight(rowsHeight);
		
		table.repaint();
	}

	public void setCellDecorator(ColorMatrixCellDecorator decorator) {
		table.setDefaultRenderer(Double.class, new TableCellRendererAdapter(decorator));
		table.repaint();
	}
	
	public int getColumnsWidth() {
		return columnsWidth;
	}
	
	public void setColumnsWidth(int columnsWidth) {
		this.columnsWidth = columnsWidth;
		refreshTableColumnsWidth();
	}
	
	public int getRowsHeight() {
		return rowsHeight;
	}
	
	public void setRowsHeight(int rowsHeight) {
		this.rowsHeight = rowsHeight;
		table.setRowHeight(rowsHeight);
	}
	
	public ListSelectionModel getTableSelectionModel() {
		return table.getSelectionModel();
	}
	
	public ListSelectionModel getColumnSelectionModel() {
		return table.getColumnModel().getSelectionModel();	
	}
	
	public SelectionMode getSelectionMode() {
		return selMode;
	}
	
	public void setSelectionMode(SelectionMode mode) {
		this.selMode = mode;
		refreshSelectionMode();
	}

	public int getSelectedLeadColumn() {
		return selectedLeadColumn;
	}
	
	public int getSelectedLeadRow() {
		return selectedLeadRow;
	}
	
	public void addListener(ColorMatrixListener listener) {
		listeners.add(listener);
	}

	public int[] getSelectedColumns() {
		return table.getSelectedColumns();
	}
	
	public int[] getSelectedRows() {
		return table.getSelectedRows();
	}

	public void clearSelection() {
		table.clearSelection();
	}

	public void selectAll() {
		int lastRowIndex = table.getRowCount() - 1;
		table.getSelectionModel()
			.addSelectionInterval(0, lastRowIndex);
		
		int lastColIndex = table.getColumnCount() - 2;
		table.getColumnModel()
			.getSelectionModel()
			.addSelectionInterval(0, lastColIndex);
	}
}
