package org.gitools.ui.editor.heatmap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.gitools.model.IModel;
import org.gitools.model.decorator.ElementDecorator;
import org.gitools.model.decorator.HeaderDecorator;
import org.gitools.model.figure.HeatmapFigure;
import org.gitools.model.matrix.IMatrixView;
import org.gitools.ui.editor.AbstractEditor;
import org.gitools.ui.editor.heatmap.HeaderConfigPage.HeaderType;
import org.gitools.ui.panels.heatmap.HeatmapPanel;
import org.gitools.ui.panels.matrix.MatrixPanel;
import org.gitools.ui.platform.AppFrame;


public class HeatmapEditor extends AbstractEditor {

	private static final long serialVersionUID = -540561086703759209L;

	public enum WorkbenchLayout {
		LEFT, RIGHT, TOP, BOTTOM
	}
	
	private static final int defaultDividerLocation = 280;
	
	private HeatmapFigure model;
	
	private CellConfigPage cellsConfigPage;
	
	private MatrixPanel matrixPanel;
	
	private JTabbedPane tabbedPane;
	
	protected boolean blockSelectionUpdate;

	private PropertyChangeListener modelListener;
	private PropertyChangeListener cellDecoratorListener;

	private HeaderConfigPage rowsConfigPage;

	private HeaderConfigPage columnsConfigPage;

	private GeneralConfigPage generalConfigPage;

	private PropertyChangeListener rowDecoratorListener;

	private PropertyChangeListener colDecoratorListener;

	private JSplitPane configSplitPane;

	public HeatmapEditor(HeatmapFigure model) {
		
		this.model = model;
		
		final IMatrixView matrixView = model.getMatrixView();
	
		this.blockSelectionUpdate = false;
		
		createComponents();
		
		modelListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				modelPropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
			}
		};
		
		cellDecoratorListener = new PropertyChangeListener() {
			@Override public void propertyChange(PropertyChangeEvent evt) {
				matrixPanel.refresh(); }
		};
		
		rowDecoratorListener = new PropertyChangeListener() {
			@Override public void propertyChange(PropertyChangeEvent evt) {
				matrixPanel.refresh(); }
		};
		
		colDecoratorListener = new PropertyChangeListener() {
			@Override public void propertyChange(PropertyChangeEvent evt) {
				matrixPanel.refresh(); }
		};
		
		model.addPropertyChangeListener(modelListener);
		
		model.getCellDecorator().addPropertyChangeListener(cellDecoratorListener);
		model.getRowDecorator().addPropertyChangeListener(rowDecoratorListener);
		model.getColumnDecorator().addPropertyChangeListener(colDecoratorListener);
		
		matrixView.addPropertyChangeListener(new PropertyChangeListener() {
			@Override public void propertyChange(PropertyChangeEvent evt) {
				matrixPropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()); }
		});
		
		setSaveAsAllowed(true);
	}

	protected void modelPropertyChange(
			String propertyName, Object oldValue, Object newValue) {
		
		if (HeatmapFigure.CELL_DECORATOR_CHANGED.equals(propertyName)) {
			final ElementDecorator prevDecorator = (ElementDecorator) oldValue;
			prevDecorator.removePropertyChangeListener(cellDecoratorListener);
			final ElementDecorator nextDecorator = (ElementDecorator) newValue;
			nextDecorator.addPropertyChangeListener(cellDecoratorListener);
			matrixPanel.setCellDecorator(model.getCellDecorator());
		}
		else if (HeatmapFigure.COLUMN_DECORATOR_CHANGED.equals(propertyName)) {
			final HeaderDecorator prevDecorator = (HeaderDecorator) oldValue;
			prevDecorator.removePropertyChangeListener(colDecoratorListener);
			final HeaderDecorator nextDecorator = (HeaderDecorator) newValue;
			nextDecorator.addPropertyChangeListener(colDecoratorListener);
			matrixPanel.setColumnDecorator(model.getColumnDecorator());
		}
		else if (HeatmapFigure.ROW_DECORATOR_CHANGED.equals(propertyName)) {
			final HeaderDecorator prevDecorator = (HeaderDecorator) oldValue;
			prevDecorator.removePropertyChangeListener(rowDecoratorListener);
			final HeaderDecorator nextDecorator = (HeaderDecorator) newValue;
			nextDecorator.addPropertyChangeListener(rowDecoratorListener);
			matrixPanel.setRowDecorator(model.getRowDecorator());
		}
		else if (HeatmapFigure.PROPERTY_CHANGED.equals(propertyName)) {
			matrixPanel.setShowGrid(model.isShowGrid());
			matrixPanel.setGridColor(model.getGridColor());
			matrixPanel.setCellSize(model.getCellWidth(), model.getCellHeight());
			matrixPanel.setRowsHeaderWidth(model.getRowHeaderSize());
			matrixPanel.setColumnsHeaderHeight(model.getColumnHeaderSize());
		}
		
		matrixPanel.refresh();
	}
	
	protected void matrixPropertyChange(
			String propertyName, Object oldValue, Object newValue) {

		/*if (ITable.CELL_DECORATION_PROPERTY.equals(propertyName)) {
			cellDecorator.setConfig(
					table.getCellDecoration(
							table.getCurrentProperty()));
			
			refreshColorMatrixWidth();
			colorMatrixPanel.refresh();
		}*/
		/*else if (ITable.SELECTION_MODE_PROPERTY.equals(propertyName)) {
			SelectionMode mode = (SelectionMode) newValue;
			colorMatrixPanel.setSelectionMode(mode);
			colorMatrixPanel.refresh();
			refreshActions();
		}*/
		if (IMatrixView.SELECTION_CHANGED.equals(propertyName)
			|| IMatrixView.VISIBLE_COLUMNS_CHANGED.equals(propertyName)) {
			
			if (!blockSelectionUpdate) {
				blockSelectionUpdate = true;
				if (IMatrixView.VISIBLE_COLUMNS_CHANGED.equals(propertyName))
					matrixPanel.refreshColumns();
				
				//System.out.println("Start selection change:");
				matrixPanel.setSelectedCells(
						getMatrixView().getSelectedColumns(),
						getMatrixView().getSelectedRows());
				matrixPanel.refresh();
				//System.out.println("End selection change.");
				
				blockSelectionUpdate = false;
			}
		}
		else if (IMatrixView.SELECTED_LEAD_CHANGED.equals(propertyName)) {
			refreshCellDetails();
		}
		else if (IMatrixView.VISIBLE_COLUMNS_CHANGED.equals(propertyName)) {
			matrixPanel.refresh();
		}
		else if (IMatrixView.VISIBLE_ROWS_CHANGED.equalsIgnoreCase(propertyName)) {
			matrixPanel.refresh();
		}
		else if (IMatrixView.CELL_VALUE_CHANGED.equals(propertyName)) {
			matrixPanel.refresh();
		}
		else if (IMatrixView.CELL_DECORATION_CONTEXT_CHANGED.equals(propertyName)) {
			if (oldValue != null)
				((IModel) oldValue).removePropertyChangeListener(modelListener);
			
			((IModel) newValue).addPropertyChangeListener(modelListener);
		}
	}

	private void refreshCellDetails() {
		AppFrame.instance().getDetailsView().update(model);
	}

	private void createComponents() {
		
		final IMatrixView matrixView = getMatrixView();
		
		/* Heatmap panel */
		
		matrixPanel = new MatrixPanel();
		matrixPanel.setMinimumSize(new Dimension(200, 200));
		matrixPanel.setModel(matrixView);
		matrixPanel.setShowGrid(model.isShowGrid());
		matrixPanel.setGridColor(model.getGridColor());
		matrixPanel.setCellSize(model.getCellWidth(), model.getCellHeight());
		matrixPanel.setRowsHeaderWidth(model.getRowHeaderSize());
		matrixPanel.setColumnsHeaderHeight(model.getColumnHeaderSize());
		
		matrixPanel.setCellDecorator(model.getCellDecorator());
		matrixPanel.setRowDecorator(model.getRowDecorator());
		matrixPanel.setColumnDecorator(model.getColumnDecorator());
		
		ListSelectionListener selListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel src = (ListSelectionModel) e.getSource();
				src.hashCode();
				
				if (!e.getValueIsAdjusting() && !blockSelectionUpdate) {
					blockSelectionUpdate = true;
					
					//System.out.println("Selection listener.");
					
					matrixView.setSelectedRows(
							matrixPanel.getSelectedRows());
					matrixView.setSelectedColumns(
							matrixPanel.getSelectedColumns());
					
					int colIndex = matrixPanel.getSelectedLeadColumn();
					int rowIndex = matrixPanel.getSelectedLeadRow();
					
					matrixView.setLeadSelection(rowIndex, colIndex);
					
					blockSelectionUpdate = false;
				}
			}
		};
		
		matrixPanel.getTableSelectionModel().addListSelectionListener(selListener);
		matrixPanel.getColumnSelectionModel().addListSelectionListener(selListener);

		refreshColorMatrixWidth();

		/* Configuration panels */

		generalConfigPage = new GeneralConfigPage(model);
		
		rowsConfigPage = new HeaderConfigPage(model, HeaderType.rows);
		
		columnsConfigPage = new HeaderConfigPage(model, HeaderType.columns);
		
		cellsConfigPage = new CellConfigPage(model);		
		cellsConfigPage.refresh();
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setMinimumSize(new Dimension(0, 170));
		tabbedPane.setPreferredSize(new Dimension(0, 180));
		tabbedPane.addTab("General", generalConfigPage);
		tabbedPane.addTab("Rows", rowsConfigPage);
		tabbedPane.addTab("Columns", columnsConfigPage);
		tabbedPane.addTab("Cells", cellsConfigPage);
		
		configSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		configSplitPane.setResizeWeight(1.0);
		//configSplitPane.setDividerLocation(defaultDividerLocation);
		configSplitPane.setDividerLocation(1.0);
		configSplitPane.setOneTouchExpandable(true);
		configSplitPane.setContinuousLayout(true);
		//configSplitPane.add(matrixPanel);
		configSplitPane.add(new HeatmapPanel(model)); //FIXME
		configSplitPane.add(tabbedPane);
		//configSplitPane.add(detailsPanel);
		
		/*final JSplitPane splitPaneLayout = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPaneLayout.setResizeWeight(1.0);
		splitPaneLayout.setDividerLocation(defaultDividerLocation);
		splitPaneLayout.setOneTouchExpandable(true);
		splitPaneLayout.setContinuousLayout(true);
		splitPaneLayout.add(configSplitPane);
		splitPaneLayout.add(tabbedPane);*/
			
		setLayout(new BorderLayout());
		//add(splitPaneLayout, BorderLayout.CENTER);
		add(configSplitPane, BorderLayout.CENTER);
	}
	
	private void refreshColorMatrixWidth() {
		/*CellDecorationConfig config = 
			getTable().getCellDecoration(
					getTable().getCurrentProperty());
		
		colorMatrixPanel.setColumnsWidth(
				config.showColors ? 
						defaultColorColumnsWidth 
						: defaultValueColumnsWidth);*/
		
		/*tablePanel.setColumnsWidth(
				configPanel.getCellDecorator()
					.getPreferredWidth());*/
	}

	protected IMatrixView getMatrixView() {
		return model.getMatrixView();
	}

	@Override
	public Object getModel() {
		return model;
	}

	@Override
	public void refresh() {
		matrixPanel.refresh();
	}

	@Override
	public void doVisible() {
		AppFrame.instance().getPropertiesView().update(model);
	}

	
	/*@Override
	public void refreshActions() {
		MenuActionSet.editActionSet.setTreeEnabled(true);
		MenuActionSet.dataActionSet.setTreeEnabled(true);
		MenuActionSet.mtcActionSet.setTreeEnabled(true);
		DataActionSet.fastSortRowsAction.setEnabled(true);
		
		FileActionSet.closeAction.setEnabled(true);
		FileActionSet.exportActionSet.setTreeEnabled(true);
		
		MenuActionSet.mtcActionSet.setTreeEnabled(true);
	}*/
}
