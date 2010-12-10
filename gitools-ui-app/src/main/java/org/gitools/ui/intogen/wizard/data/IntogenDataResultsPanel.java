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
 * IntogenDataResultsPanel.java
 *
 * Created on September 4, 2009, 5:29 PM
 */

package org.gitools.ui.intogen.wizard.data;

/**
 *
 * @author  cperez
 */
public class IntogenDataResultsPanel extends javax.swing.JPanel {

    /** Creates new form IntogenDataResultsPanel */
    public IntogenDataResultsPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typeLabel = new javax.swing.JLabel();
        typeCbox = new javax.swing.JComboBox();
        conditionLabel = new javax.swing.JLabel();
        conditionCbox = new javax.swing.JComboBox();
        statisticLabel = new javax.swing.JLabel();
        statisticCbox = new javax.swing.JComboBox();

        typeLabel.setText("Type");

        typeCbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CNV", "Transcriptomic", "Mutations", "Predictions" }));

        conditionLabel.setText("Condition");

        conditionCbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gain", "Loss", "Upregulation", "Downregulation", "Oncogen", "Suppressor" }));

        statisticLabel.setText("Statistic");

        statisticCbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "p-value", "corrected p-value", "found", "studied", "rank", "probability" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typeCbox, 0, 376, Short.MAX_VALUE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(conditionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(conditionCbox, 0, 376, Short.MAX_VALUE)
                    .addComponent(statisticLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(statisticCbox, 0, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(typeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeCbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(conditionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conditionCbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(statisticLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statisticCbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox conditionCbox;
    public javax.swing.JLabel conditionLabel;
    public javax.swing.JComboBox statisticCbox;
    public javax.swing.JLabel statisticLabel;
    public javax.swing.JComboBox typeCbox;
    public javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables

}
