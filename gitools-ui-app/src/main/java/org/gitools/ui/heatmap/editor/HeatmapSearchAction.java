/*
 *  Copyright 2011 Universitat Pompeu Fabra.
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

package org.gitools.ui.heatmap.editor;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import org.gitools.ui.IconNames;
import org.gitools.ui.platform.AppFrame;
import org.gitools.ui.platform.actions.BaseAction;
import org.gitools.ui.platform.editor.EditorsPanel;
import org.gitools.ui.platform.editor.IEditor;

public class HeatmapSearchAction extends BaseAction {

	public HeatmapSearchAction() {
		super("Search");
		setDesc("Search for a text in rows or columns");
		setSmallIconFromResource(IconNames.SEARCH16);
		setLargeIconFromResource(IconNames.SEARCH24);
		setMnemonic(KeyEvent.VK_F);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EditorsPanel editorPanel = AppFrame.instance().getEditorsPanel();

		IEditor currentEditor = editorPanel.getSelectedEditor();

		if (!(currentEditor instanceof HeatmapEditor))
			return;

		HeatmapEditor hmEditor = (HeatmapEditor) currentEditor;

		hmEditor.setSearchVisible(true);
	}

}