/*
 *  Copyright 2010 cperez.
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

/*
 * LabelFilterDialog.java
 *
 * Created on Jan 19, 2010, 6:05:24 PM
 */

package org.gitools.ui.dialog.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.gitools.ui.platform.dialog.ExceptionDialog;
import org.gitools.ui.settings.Settings;
import org.gitools.ui.utils.FileChooserUtils;

public class LabelFilterDialog extends javax.swing.JDialog {
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

	private DefaultTableModel tableModel = new DefaultTableModel(new String[] {"Values"}, 0);

    /** Creates new form LabelFilterDialog */
    public LabelFilterDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

		tableModel.addTableModelListener(new TableModelListener() {
			@Override public void tableChanged(TableModelEvent e) {
				saveBtn.setEnabled(table.getRowCount() > 0);
			}
		});

		table.setModel(tableModel);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override public void valueChanged(ListSelectionEvent e) {
				removeBtn.setEnabled(table.getSelectedRowCount() > 0);
			}
		});
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        loadBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        applyToRowsCheck = new javax.swing.JCheckBox();
        applyToColumnsCheck = new javax.swing.JCheckBox();
        useRegexCheck = new javax.swing.JCheckBox();

        setTitle("Filter labels...");
        setLocationByPlatform(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        addBtn.setText("Add");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        removeBtn.setText("Remove");
        removeBtn.setEnabled(false);
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        loadBtn.setText("Load...");
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });

        saveBtn.setText("Save...");
        saveBtn.setEnabled(false);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        applyToRowsCheck.setSelected(true);
        applyToRowsCheck.setText("Apply to rows");

        applyToColumnsCheck.setText("Apply to columns");

        useRegexCheck.setText("Use regular expressions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(removeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(applyToRowsCheck)
                    .addComponent(applyToColumnsCheck)
                    .addComponent(useRegexCheck))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(applyToRowsCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyToColumnsCheck)
                .addGap(18, 18, 18)
                .addComponent(useRegexCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

	private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
		tableModel.addRow(new String[] {""});
	}//GEN-LAST:event_addBtnActionPerformed

	private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
		int[] rows = table.getSelectedRows();
		Arrays.sort(rows);
		for (int i = 0; i < rows.length; i++)
			tableModel.removeRow(rows[i] - i);
	}//GEN-LAST:event_removeBtnActionPerformed

	private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.getDataVector().clear(); //start from scratch

		try {
			File file = FileChooserUtils.selectFile(
					"Select the file containing values",
					Settings.getDefault().getLastFilterPath(),
					FileChooserUtils.MODE_OPEN);

			if (file == null)
				return;

			Settings.getDefault().setLastFilterPath(file.getParent());

			Vector<String> columnIdentifiers = new Vector<String>(1);
			columnIdentifiers.add("Values");
			model.setDataVector(readNamesFromFile(file), columnIdentifiers);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}//GEN-LAST:event_loadBtnActionPerformed

	protected Vector<Vector<String>> readNamesFromFile(File file) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String line;

	    Vector<Vector<String>> names = new Vector<Vector<String>>();

	    while ((line = br.readLine()) != null) {
	    	line = line.trim();
	    	if(!line.isEmpty()) {
				final Vector v = new Vector(1);
				v.add(line);
	    		names.add(v);
			}
	    }

	    return names;
	}

	private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		try {
			File file = FileChooserUtils.selectFile(
					"Select file name ...",
					Settings.getDefault().getLastFilterPath(),
					FileChooserUtils.MODE_SAVE);

			if (file == null)
				return;

			Settings.getDefault().setLastFilterPath(file.getParent());

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (Vector<String> v : (Vector<Vector<String>>) model.getDataVector())
				bw.append(v.get(0)).append('\n');
			bw.close();
		}
		catch (Exception ex) {
			ExceptionDialog edlg = new ExceptionDialog(getOwner(), ex);
			edlg.setVisible(true);
		}
	}//GEN-LAST:event_saveBtnActionPerformed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JCheckBox applyToColumnsCheck;
    private javax.swing.JCheckBox applyToRowsCheck;
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadBtn;
    private javax.swing.JButton okButton;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTable table;
    private javax.swing.JCheckBox useRegexCheck;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;

	public boolean isApplyToRowsChecked() {
		return applyToRowsCheck.isSelected();
	}

	public boolean isApplyToColumnsChecked() {
		return applyToColumnsCheck.isSelected();
	}

	public boolean isUseRegexChecked() {
		return useRegexCheck.isSelected();
	}

	public List<String> getValues() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		List<String> values = new ArrayList<String>(model.getRowCount());
		for (Vector<String> v: (Vector<Vector<String>>) model.getDataVector())
			values.add(v.get(0));

		return values;
	}
}
