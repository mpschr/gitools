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

package org.gitools.ui.platform.editor;

import org.gitools.utils.progressmonitor.IProgressMonitor;
import org.gitools.ui.platform.actions.ActionManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MultiEditor extends AbstractEditor {

	private static final long serialVersionUID = -6013660760909524202L;

	private JTabbedPane tabbedPane;
	
	public MultiEditor() {
		createComponents();
	}
	
	private void createComponents() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent evt) {
				IEditor editor = getSelectedEditor();
				ActionManager.getDefault().updateEnabledByEditor(editor);
				//AppFrame.instance().getEditorsPanel().refreshActions();

				editor.doVisible();
			}
		});
		
		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
	}

	protected void addView(AbstractEditor view, String title) {
		tabbedPane.add(view, title);
	}
	
	protected AbstractEditor getSelectedEditor() {
		return (AbstractEditor) tabbedPane.getSelectedComponent();
	}
	
	@Override
	public Object getModel() {
		return getSelectedEditor().getModel();
	}
	
	@Override
	public boolean isDirty() {
		return getSelectedEditor().isDirty();
	}
	
	@Override
	public boolean isSaveAsAllowed() {
		return getSelectedEditor().isSaveAsAllowed();
	}

    @Override
    public boolean isSaveAllowed() {
        return getSelectedEditor().isSaveAllowed();
    }
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		getSelectedEditor().doSave(monitor);
	}
	
	@Override
	public void doSaveAs(IProgressMonitor monitor) {
		getSelectedEditor().doSaveAs(monitor);
	}

	@Override
	public void doVisible() {
		getSelectedEditor().doVisible();
	}

	@Override
	public void refresh() {
		getSelectedEditor().refresh();
	}
}
