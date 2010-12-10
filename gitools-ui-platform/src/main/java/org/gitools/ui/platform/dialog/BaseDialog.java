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

package org.gitools.ui.platform.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@Deprecated
public abstract class BaseDialog extends JDialog {

	private static final long serialVersionUID = 183914906195441688L;
	
	String dialogTitle;
	String dialogSubtitle;

	public BaseDialog(JFrame owner, String windowTitle, String dialogTitle, String dialogSubtitle) {
		setModal(true);
		setLocationByPlatform(true);				
		setTitle(windowTitle);
		setDialogTitle(dialogTitle);
		setDialogSubtitle(dialogSubtitle);
	}
		
	//Titles
	protected void setDialogTitle(String dialogTitle){
		this.dialogTitle = dialogTitle;
	}
	public void setDialogSubtitle(String dialogSubtitle){
		this.dialogSubtitle = dialogSubtitle;
	}
	abstract void setIcon();
	//Components
	abstract void addButton(JButton button);
	abstract void removeButton(JButton button);
	
	protected void createComponents(JComponent cntComponent){
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JTextArea titleField = new JTextArea();
		titleField.setText(dialogTitle);
		Font titleFont = new Font(
				titleField.getFont().getName(),
				titleField.getFont().getStyle(),
				24);
		titleField.setFont(titleFont);
		titleField.setEditable(false);
		titleField.setOpaque(false);
		titlePanel.add(titleField, BorderLayout.CENTER);
		if (dialogSubtitle != null){
			JTextArea subtitleField = new JTextArea();
			subtitleField.setText(dialogSubtitle);
			Font subtitleFont = new Font(
					subtitleField.getFont().getName(),
					subtitleField.getFont().getStyle(),
					14);
			subtitleField.setFont(subtitleFont);
			subtitleField.setEditable(false);
			subtitleField.setOpaque(false);
			titlePanel.add(subtitleField, BorderLayout.SOUTH);
		}

		this.setMinimumSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		add(titlePanel, BorderLayout.NORTH);
		add(cntComponent, BorderLayout.CENTER);
	}
	
}
