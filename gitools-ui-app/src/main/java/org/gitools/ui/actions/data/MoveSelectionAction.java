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

package org.gitools.ui.actions.data;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.gitools.ui.IconNames;
import org.gitools.ui.platform.actions.BaseAction;

import org.gitools.heatmap.model.Heatmap;
import org.gitools.matrix.model.IMatrixView;
import org.gitools.ui.actions.ActionUtils;

public class MoveSelectionAction extends BaseAction {

	private static final long serialVersionUID = 2499014276737037571L;

	public enum MoveDirection {
		ROW_UP, ROW_DOWN, COL_LEFT, COL_RIGHT
	}
	
	protected MoveDirection dir;
	
	public MoveSelectionAction(MoveDirection dir) {
		super(null);
	
		this.dir = dir;
		
		switch (dir) {
		case ROW_UP:
			setName("Move row up");
			setDesc("Move row up");
			setSmallIconFromResource(IconNames.moveRowsUp16);
			setLargeIconFromResource(IconNames.moveRowsUp24);
			setMnemonic(KeyEvent.VK_U);
			break;
		case ROW_DOWN:
			setName("Move row down");
			setDesc("Move row down");
			setSmallIconFromResource(IconNames.moveRowsDown16);
			setLargeIconFromResource(IconNames.moveRowsDown24);
			setMnemonic(KeyEvent.VK_D);
			break;
		case COL_LEFT:
			setName("Move column left");
			setDesc("Move column left");
			setSmallIconFromResource(IconNames.moveColsLeft16);
			setLargeIconFromResource(IconNames.moveColsLeft24);
			setMnemonic(KeyEvent.VK_L);
			break;
		case COL_RIGHT:
			setName("Move column right");
			setDesc("Move column right");
			setSmallIconFromResource(IconNames.moveColsRight16);
			setLargeIconFromResource(IconNames.moveColsRight24);
			setMnemonic(KeyEvent.VK_R);
			break;
		}
	}
	
	@Override
	public boolean isEnabledByModel(Object model) {
		return model instanceof Heatmap
			|| model instanceof IMatrixView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		IMatrixView matrixView = ActionUtils.getMatrixView();
		
		if (matrixView == null)
			return;
		
		switch (dir) {
		case ROW_UP:
			matrixView.moveRowsUp(
					matrixView.getSelectedRows());
			break;
		case ROW_DOWN:
			matrixView.moveRowsDown(
					matrixView.getSelectedRows());
			break;
		case COL_LEFT:
			matrixView.moveColumnsLeft(
					matrixView.getSelectedColumns());
			break;
		case COL_RIGHT:
			matrixView.moveColumnsRight(
					matrixView.getSelectedColumns());
			break;
		}
	}
}
