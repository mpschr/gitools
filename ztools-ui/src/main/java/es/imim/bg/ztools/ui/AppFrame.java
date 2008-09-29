package es.imim.bg.ztools.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

import cern.colt.matrix.DoubleFactory3D;
import cern.colt.matrix.DoubleMatrix3D;

import es.imim.bg.progressmonitor.ProgressMonitor;
import es.imim.bg.ztools.model.Analysis;
import es.imim.bg.ztools.model.Results;
import es.imim.bg.ztools.ui.model.ResultsModel;
import es.imim.bg.ztools.ui.utils.Options;
import es.imim.bg.ztools.ui.views.results.ResultsView;

public class AppFrame extends JFrame {

	private static final long serialVersionUID = -6899584212813749990L;

	private String appName;
	private String appVersion;
	
	private WorkspacePanel workspace;
	
	private StatusBar statusBar;
	
	private static AppFrame instance;
	
	public static AppFrame instance() {
		if (instance == null)
			instance = new AppFrame();
		return instance;
	}
	
	private AppFrame() {
		appName = getClass().getPackage().getImplementationTitle();
		if (appName == null)
			appName = "ztools";
		
		appVersion = getClass().getPackage().getImplementationVersion();
		if (appVersion == null)
			appVersion = "SNAPSHOT";
		
		createActions();
		createComponents();
		createDemoView();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Options.instance().save();
				System.exit(0);
			}
		});

		setTitle(appName + " " + appVersion);
		setStatusText("Ok");
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		pack();
	}

	private void createActions() {
		Actions.openAnalysisAction.setEnabled(true);
	}
	
	private void createComponents() {
		setJMenuBar(createMenu());
		
		workspace = new WorkspacePanel();
		
		statusBar = new StatusBar();
		
		add(workspace, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);
	}
	
	private JMenuBar createMenu() {
		
		final JMenu menuFile = new JMenu("File");
		menuFile.add(Actions.openAnalysisAction);
		
		final JMenu editMenu = new JMenu("Edit");
		editMenu.add(Actions.selectAllAction);
		//editMenu.add(Actions.invertSelectionAction);
		editMenu.add(Actions.unselectAllAction);
		editMenu.addSeparator();
		//editMenu.add(Actions.hideSelectedColumnsAction);
		editMenu.add(Actions.sortSelectedColumnsAction);
		editMenu.addSeparator();
		editMenu.add(Actions.hideSelectedRowsAction);
		
		final JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(editMenu);
		
		return menuBar;
	}
	
	private void createDemoView() {
		int rows = 40;
		int cols = 12;
		DoubleMatrix3D data = DoubleFactory3D.dense.random(2, rows, cols);
		
		final String[] rowNames = new String[data.rows()];
		for (int i = 0; i < rowNames.length; i++)
			rowNames[i] = "row " + (i + 1);
		
		final String[] colNames = new String[data.columns()];
		for (int i = 0; i < colNames.length; i++)
			colNames[i] = "col " + (i + 1);
		
		Results results = new Results(
				colNames, 
				rowNames, 
				new String[] {"right-p-value", "param2"}, 
				data);
		
		Analysis analysis = new Analysis();
		analysis.setResults(results);
		
		ResultsView view = 
			new ResultsView(
				new ResultsModel(results));
		view.setName("demo");
		
		workspace.addView(view);
	}
	
	public void start() {
		setLocationByPlatform(true);
		setVisible(true);
	}
	
	public WorkspacePanel getWorkspace() {
		return workspace;
	}
	
	public void setStatusText(String text) {
		statusBar.setText(text);
		repaint();
	}

	public ProgressMonitor createMonitor() {
		return statusBar.createMonitor();
	}

	public void refresh() {
		
	}
}

