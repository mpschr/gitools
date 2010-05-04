/*
 *  Copyright 2010 xrafael.
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
 * NewJDialog.java
 *
 * Created on May 4, 2010, 2:45:35 PM
 */

package org.gitools.ui.dialog.clustering;

import java.util.List;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import org.gitools.matrix.model.element.IElementAttribute;

public class ClusteringDialog extends javax.swing.JDialog {

	// model wrappers
	private static class MatrixAttributeWrapper {

		private IElementAttribute attribute;

		public MatrixAttributeWrapper(IElementAttribute a) {
			this.attribute = a;
		}

		public IElementAttribute getMatrixAttribute() {
			return attribute;
		}

		public void setMatrixAttribute(IElementAttribute a) {
			this.attribute = a;
		}

		@Override
		public String toString() {
			return attribute.getName();
		}
	}
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;
	private int returnStatus = RET_CANCEL;

	/** Creates new form clusteringPage */
	public ClusteringDialog(java.awt.Window parent) {

		super(parent);
		setModal(true);

		initComponents();

		validate();

	}


	/** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
	public int getReturnStatus() {
		return returnStatus;
	}


	private void doClose(int retStatus) {
		returnStatus = retStatus;
		setVisible(false);
		dispose();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        okButton = new javax.swing.JButton();
        dataClustCombo = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        algorithmTypeCombo = new javax.swing.JComboBox();
        rowsRadio = new javax.swing.JRadioButton();
        columnsRadio = new javax.swing.JRadioButton();

        setTitle("Data clustering");
        setLocationByPlatform(true);

        cancelButton.setText("Cancel");
        cancelButton.setDefaultCapable(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Values from:");

        jButton1.setText("Config ...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Apply to:");

        jLabel3.setText("Method:");

        algorithmTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hierarchical clustering", "K-means" }));
        algorithmTypeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                algorithmTypeComboActionPerformed(evt);
            }
        });

        rowsRadio.setText("rows");

        columnsRadio.setSelected(true);
        columnsRadio.setText("columns");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(40, 40, 40)
                        .addComponent(algorithmTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dataClustCombo, 0, 288, Short.MAX_VALUE))
                    .addComponent(rowsRadio)
                    .addComponent(columnsRadio)
                    .addComponent(jLabel5))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(algorithmTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton1))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(dataClustCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(14, 14, 14)
                .addComponent(columnsRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rowsRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		doClose(RET_CANCEL);
}//GEN-LAST:event_cancelButtonActionPerformed

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
		doClose(RET_OK);
}//GEN-LAST:event_okButtonActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

		if (algorithmTypeCombo.getSelectedItem().toString().toLowerCase().equals("k-means")){
			KmeansParamsDialog dlg = new KmeansParamsDialog(this);
			dlg.setVisible(true);
		}else{
			CobwebParamsDialog dlg = new CobwebParamsDialog(this);
			dlg.setVisible(true);
		}
	}//GEN-LAST:event_jButton1ActionPerformed

	private void algorithmTypeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_algorithmTypeComboActionPerformed

	}//GEN-LAST:event_algorithmTypeComboActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox algorithmTypeCombo;
    private javax.swing.JButton cancelButton;
    private javax.swing.JRadioButton columnsRadio;
    private javax.swing.JComboBox dataClustCombo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton okButton;
    private javax.swing.JRadioButton rowsRadio;
    // End of variables declaration//GEN-END:variables
private Properties params = new Properties();

	public Properties getParams() {
		return params;
	}

	public void setParams(Properties params) {
		this.params = params;
	}

	public Properties getClusterParameters() {

		Properties clusterParams = new Properties();

		clusterParams.put("method", algorithmTypeCombo.getSelectedItem().toString().toLowerCase());
		clusterParams.put("index", dataClustCombo.getSelectedItem());
		clusterParams.put("transpose", rowsRadio.isSelected()+"");

		if (algorithmTypeCombo.getSelectedItem().toString().toLowerCase().equals("k-means")) {

			clusterParams.put("iterations", params.getProperty("iterations","500"));
			clusterParams.put("seed", params.getProperty("seed","10"));
			clusterParams.put("k", params.getProperty("k","2"));
			clusterParams.put("distance", params.getProperty("distance","euclidean"));

		}else{

			clusterParams.put("cutoff", params.getProperty("cutoff","0.0028"));
			clusterParams.put("seed", params.getProperty("seed","42"));
			clusterParams.put("acuity", params.getProperty("acuity","1.0"));
		}

		return clusterParams;
	}

	public void setAttributes(List<IElementAttribute> cellAttributes) {

		DefaultComboBoxModel model = new DefaultComboBoxModel();
		MatrixAttributeWrapper attrWrapper = null;
		for (IElementAttribute attr : cellAttributes) {
			attrWrapper = new MatrixAttributeWrapper(attr);
			model.addElement(attrWrapper);
		}

		dataClustCombo.setModel(model);

	}

	public boolean isTransposeEnabled() {
		return rowsRadio.isSelected();
	}
}

