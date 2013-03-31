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

/*
 * FilterDialog.java
 *
 * Created on Jan 19, 2010, 2:04:30 PM
 */

package org.gitools.ui.dialog.filter;

import org.gitools.utils.cutoffcmp.CutoffCmp;
import java.awt.Component;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.gitools.matrix.filter.ValueFilterCriteria;

public class ValueFilterDialog extends javax.swing.JDialog {
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

	private static class ComboBoxCellRenderer extends JComboBox implements TableCellRenderer {

		public ComboBoxCellRenderer(Object[] values) {
			super(values);
		}

		@Override
		public Component getTableCellRendererComponent(
				JTable table, Object value,
				boolean isSelected, boolean hasFocus,
				int row, int column) {

			if (isSelected) {
				setForeground(table.getSelectionForeground());
				super.setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}

			// Select the current value
			setSelectedItem(value);
			return this;
		}
	}

	private static class ComboBoxCellEditor extends DefaultCellEditor {
		public ComboBoxCellEditor(Object[] values) {
			super(new JComboBox(values));
		}
	}

	private String[] attributeNames;
	private CutoffCmp[] comparators;

	private ValueFilterCriteriaTableModel criteriaModel;

    /** Creates new form FilterDialog */
    public ValueFilterDialog(java.awt.Frame parent,
			String[] attributeNames, CutoffCmp[] comparators,
			List<ValueFilterCriteria> initialCriteriaList) {

        super(parent, true);

		this.attributeNames = attributeNames;
		this.comparators = comparators;

		this.criteriaModel = new ValueFilterCriteriaTableModel(attributeNames);

		initComponents();

		table.setModel(criteriaModel);

		criteriaModel.addTableModelListener(new TableModelListener() {
			@Override public void tableChanged(TableModelEvent e) {
				tableRemoveBtn.setEnabled(criteriaModel.getList().size() > 0);
			}
		});

		if (initialCriteriaList != null)
			criteriaModel.addAllCriteria(initialCriteriaList);
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setCellEditor(new ComboBoxCellEditor(attributeNames));
		columnModel.getColumn(1).setCellEditor(new ComboBoxCellEditor(comparators));
		columnModel.getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
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

        applyToGroup = new javax.swing.ButtonGroup();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        tableAddBtn = new javax.swing.JButton();
        tableRemoveBtn = new javax.swing.JButton();
        allCriteriaCheck = new javax.swing.JCheckBox();
        allElementsCheck = new javax.swing.JCheckBox();
        invertCriteriaCheck = new javax.swing.JCheckBox();
        loadBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        applyToRowsRb = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        applyToRowsAndColumnsRb = new javax.swing.JRadioButton();
        applyToColumnsRb = new javax.swing.JRadioButton();

        setTitle("Filter criteria");
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
                "Attribute", "Condition", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        tableAddBtn.setText("Add");
        tableAddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableAddBtnActionPerformed(evt);
            }
        });

        tableRemoveBtn.setText("Remove");
        tableRemoveBtn.setEnabled(false);
        tableRemoveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableRemoveBtnActionPerformed(evt);
            }
        });

        allCriteriaCheck.setText("All criteria should match");

        allElementsCheck.setText("All elements should match");

        invertCriteriaCheck.setText("Invert criteria when filtering");

        loadBtn.setText("Load...");
        loadBtn.setEnabled(false);

        saveBtn.setText("Save...");
        saveBtn.setEnabled(false);

        applyToGroup.add(applyToRowsRb);
        applyToRowsRb.setSelected(true);
        applyToRowsRb.setText("rows");

        jLabel2.setText("Apply to:");

        applyToGroup.add(applyToRowsAndColumnsRb);
        applyToRowsAndColumnsRb.setText("rows and columns");

        applyToGroup.add(applyToColumnsRb);
        applyToColumnsRb.setText("columns");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(saveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tableAddBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tableRemoveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(applyToRowsRb)
                    .addComponent(applyToColumnsRb)
                    .addComponent(applyToRowsAndColumnsRb)
                    .addComponent(jLabel2)
                    .addComponent(allCriteriaCheck)
                    .addComponent(allElementsCheck)
                    .addComponent(invertCriteriaCheck))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tableAddBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tableRemoveBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allCriteriaCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allElementsCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(invertCriteriaCheck)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(applyToRowsRb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyToColumnsRb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyToRowsAndColumnsRb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

	private void tableAddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableAddBtnActionPerformed
		criteriaModel.addCriteria(new ValueFilterCriteria(
				attributeNames[0], 0, comparators[0], 0.0));
	}//GEN-LAST:event_tableAddBtnActionPerformed

	private void tableRemoveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableRemoveBtnActionPerformed
		criteriaModel.removeCriteria(table.getSelectedRows());
	}//GEN-LAST:event_tableRemoveBtnActionPerformed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allCriteriaCheck;
    private javax.swing.JCheckBox allElementsCheck;
    private javax.swing.JRadioButton applyToColumnsRb;
    private javax.swing.ButtonGroup applyToGroup;
    private javax.swing.JRadioButton applyToRowsAndColumnsRb;
    private javax.swing.JRadioButton applyToRowsRb;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox invertCriteriaCheck;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton loadBtn;
    private javax.swing.JButton okButton;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTable table;
    private javax.swing.JButton tableAddBtn;
    private javax.swing.JButton tableRemoveBtn;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;

	public boolean isApplyToRowsChecked() {
		return applyToRowsRb.isSelected() || applyToRowsAndColumnsRb.isSelected();
	}

	public boolean isApplyToColumnsChecked() {
		return applyToColumnsRb.isSelected() || applyToRowsAndColumnsRb.isSelected();
	}

	public boolean isAllCriteriaChecked() {
		return allCriteriaCheck.isSelected();
	}

	public boolean isAllElementsChecked() {
		return allElementsCheck.isSelected();
	}

	public boolean isInvertCriteriaChecked() {
		return invertCriteriaCheck.isSelected();
	}

	public List<ValueFilterCriteria> getCriteriaList() {
		return criteriaModel.getList();
	}
}
