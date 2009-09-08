package org.gitools.ui.actions.file;

import java.awt.event.ActionEvent;

import org.gitools.ui.actions.BaseAction;
import org.gitools.ui.actions.UnimplementedAction;

public class ImportIntogenFigureAction extends BaseAction {

	private static final long serialVersionUID = 668140963768246841L;

	public ImportIntogenFigureAction() {
		super("IntOGen matrix figure ...");
		setDefaultEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new UnimplementedAction().actionPerformed(e);
	}

}
