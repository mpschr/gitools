/*
 * #%L
 * gitools-ui-app
 * %%
 * Copyright (C) 2013 Universitat Pompeu Fabra - Biomedical Genomics group
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.gitools.ui.heatmap.header.wizard.heatmapheader;

import org.gitools.core.heatmap.HeatmapDimension;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.gitools.utils.aggregation.AggregatorFactory;
import org.gitools.utils.aggregation.IAggregator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AggregationDataSourcePage extends AbstractWizardPage {

    private final IAggregator[] aggregatorsArray;


    public AggregationDataSourcePage(HeatmapDimension headerDimension, HeatmapDimension aggregationDimension, List<String> layerNames, int selectedLayer) {


        this.aggregatorsArray = AggregatorFactory.getAggregatorsArray();
        String[] aggregatorNames = new String[aggregatorsArray.length];
        for (int i = 0; i < aggregatorsArray.length; i++)
            aggregatorNames[i] = aggregatorsArray[i].toString();

        initComponents();
        updateModel();

        boolean hasAnnotation = headerDimension.getAnnotations() != null;
        separateAggregationCb.setEnabled(hasAnnotation);

        valueCb.setModel(new DefaultComboBoxModel(layerNames.toArray()));
        valueCb.setSelectedIndex(selectedLayer);
        valueCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateControls();
            }
        });

        aggregatorCb.setModel(new DefaultComboBoxModel(aggregatorNames));
        aggregatorCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateControls();
            }
        });

        useAllRb.setText("Use values from all " + headerDimension.getId());
        useSelectedRb.setText("Use values from selected " + headerDimension.getId());
        separateAggregationCb.setText("aggregate by " + aggregationDimension.getId() + " annotations groups");

        setTitle("Choose the data source for the header to add");

    }

    public IAggregator getAggregator() {
        return aggregatorsArray[aggregatorCb.getSelectedIndex()];
    }

    public int getAggregationLayer() {
        return valueCb.getSelectedIndex();
    }

    public String getSelectedDataValueName() {
        return valueCb.getSelectedItem().toString();
    }

    public boolean useAllColumnsOrRows() {
        return useAllRb.isSelected();
    }

    private void updateCompleted() {
        boolean completed = aggregatorCb.getSelectedIndex() > -1 && valueCb.getSelectedIndex() > -1;
        setComplete(completed);
    }

    public boolean aggregateAnnotationsSeparately() {
        return separateAggregationCb.isEnabled() && separateAggregationCb.isSelected();
    }

    @Override
    public void updateControls() {
        updateCompleted();
    }


    @Override
    public void updateModel() {
        super.updateModel();
    }


    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        allOrSelected = new javax.swing.ButtonGroup();
        valueCb = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        aggregatorCb = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        useAllRb = new javax.swing.JRadioButton();
        useSelectedRb = new javax.swing.JRadioButton();
        separateAggregationCb = new javax.swing.JCheckBox();

        valueCb.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jLabel1.setText("Select data value");

        aggregatorCb.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jLabel2.setText("Value aggregation");

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 0, 11));
        jLabel3.setText("The way the the row/column will be aggregated into one value");

        allOrSelected.add(useAllRb);
        useAllRb.setSelected(true);
        useAllRb.setText("use all");
        useAllRb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useAllRbActionPerformed(evt);
            }
        });

        allOrSelected.add(useSelectedRb);
        useSelectedRb.setText("use selected");
        useSelectedRb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useSelectedRbActionPerformed(evt);
            }
        });

        separateAggregationCb.setText("aggregate sperately for annotation groups");
        separateAggregationCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                separateAggregationCbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(valueCb, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel1).addComponent(jLabel2).addComponent(jLabel3).addComponent(aggregatorCb, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(useAllRb).addComponent(useSelectedRb).addComponent(separateAggregationCb)).addContainerGap(204, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(valueCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(aggregatorCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(15, 15, 15).addComponent(separateAggregationCb).addGap(18, 18, 18).addComponent(useAllRb).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(useSelectedRb).addContainerGap(83, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void useAllRbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useAllRbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_useAllRbActionPerformed

    private void useSelectedRbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useSelectedRbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_useSelectedRbActionPerformed

    private void separateAggregationCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_separateAggregationCbActionPerformed
        useAllRb.setEnabled(!separateAggregationCb.isSelected());
        useSelectedRb.setEnabled(!separateAggregationCb.isSelected());
    }//GEN-LAST:event_separateAggregationCbActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox aggregatorCb;
    private javax.swing.ButtonGroup allOrSelected;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JCheckBox separateAggregationCb;
    private javax.swing.JRadioButton useAllRb;
    private javax.swing.JRadioButton useSelectedRb;
    private javax.swing.JComboBox valueCb;
    // End of variables declaration//GEN-END:variables

}
