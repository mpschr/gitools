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

/*
 * LabelHeaderPage.java
 *
 * Created on 25-feb-2011, 21:01:35
 */

package org.gitools.ui.heatmap.header.wizard.heatmapheader;

import org.gitools.heatmap.HeatmapDim;
import org.gitools.matrix.model.AnnotationMatrix;
import org.gitools.ui.platform.dialog.MessageStatus;
import org.gitools.ui.platform.wizard.AbstractWizardPage;

import javax.swing.*;

public class AnnotationSourcePage extends AbstractWizardPage {

	protected HeatmapDim hdim;
    public  String infoMessage = "";
    private int[] selectedIndices;

    public AnnotationSourcePage(HeatmapDim hdim, String infoMessage) {
		this.hdim = hdim;

        initComponents();

        this.selectedIndices = new int[0];
        this.infoMessage = infoMessage;
		setTitle("Select the annotation");
        setMessage(MessageStatus.INFO,infoMessage);
		setComplete(false);
    }



	@Override
	public void updateControls() {
		super.updateControls();

		AnnotationMatrix am = hdim.getAnnotations();
        int seomvar = annList.getModel().getSize();
		if (am != null && am.getColumnCount() > 0 && annList.getModel().getSize() != am.getColumnCount()) {
			DefaultListModel model = new DefaultListModel();
			for (int i = 0; i < am.getColumnCount(); i++)
				model.addElement(am.getColumnLabel(i));
			annList.setModel(model);
		}
        annList.setSelectedIndices(selectedIndices);
	}

	@Override
	public void updateModel() {
		super.updateModel();
	}

    public String getSelectedPattern() {

        StringBuilder sb = new StringBuilder();
        Object[] values = annList.getSelectedValues();
        if (values.length == 0)
            return "";

        sb.append("${");
        sb.append(values[0]);
        sb.append("}");

        return sb.toString();
    }

	public String getSelectedAnnotation() {
		if (annList.getSelectedIndex() != -1)
			return (String) annList.getSelectedValue();
		else
			return "";
	}


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        annList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();

        annList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        annList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                annListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(annList);

        jLabel1.setText("Available annotations");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void annListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_annListValueChanged
        boolean complete = annList.getSelectedIndices().length > 0;
        setComplete(complete);
        
        if (complete) {
            int oldvalue = selectedIndices.length > 0 ? selectedIndices[0]: -1;
            selectedIndices = annList.getSelectedIndices();
            int newvalue = selectedIndices.length > 0 ? selectedIndices[0] : -1;
            if (oldvalue != selectedIndices[0])
                setMessage(MessageStatus.INFO,this.infoMessage);
        }
    }//GEN-LAST:event_annListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList annList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.ButtonGroup optGroup;
    // End of variables declaration//GEN-END:variables

}
