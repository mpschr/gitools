package org.gitools.ui.actions.file;

import java.awt.event.ActionEvent;

import org.gitools.ui.actions.BaseAction;
import org.gitools.ui.actions.UnimplementedAction;

public class ImportEnsemblTableAction extends BaseAction {

	private static final long serialVersionUID = 668140963768246841L;

	public ImportEnsemblTableAction() {
		super("Ensembl data table ...");
		setDefaultEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new UnimplementedAction().actionPerformed(e);
	}

}
