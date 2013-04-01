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
package org.gitools.ui.wizard.common;

import org.gitools.ui.platform.wizard.AbstractWizardPage;

public class ExportHeatmapLabelsPage extends AbstractWizardPage
{

    public enum WhichLabels
    {
        VISIBLE_ROWS, VISIBLE_COLUMNS,
        HIDDEN_ROWS, HIDDEN_COLUMNS
    }

    public ExportHeatmapLabelsPage()
    {
        initComponents();

        setTitle("What do you want to export ?");
        setComplete(true);
    }

    public WhichLabels getWhichLabels()
    {
        if (vRowsRb.isSelected())
        {
            return WhichLabels.VISIBLE_ROWS;
        }
        if (vColsRb.isSelected())
        {
            return WhichLabels.VISIBLE_COLUMNS;
        }
        if (hRowsRb.isSelected())
        {
            return WhichLabels.HIDDEN_ROWS;
        }
        if (hColsRb.isSelected())
        {
            return WhichLabels.HIDDEN_COLUMNS;
        }
        return WhichLabels.VISIBLE_ROWS;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        buttonGroup1 = new javax.swing.ButtonGroup();
        vRowsRb = new javax.swing.JRadioButton();
        vColsRb = new javax.swing.JRadioButton();
        hRowsRb = new javax.swing.JRadioButton();
        hColsRb = new javax.swing.JRadioButton();

        buttonGroup1.add(vRowsRb);
        vRowsRb.setSelected(true);
        vRowsRb.setText("Visible rows");

        buttonGroup1.add(vColsRb);
        vColsRb.setText("Visible columns");

        buttonGroup1.add(hRowsRb);
        hRowsRb.setText("Hidden rows");

        buttonGroup1.add(hColsRb);
        hColsRb.setText("Hidden columns");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(vRowsRb)
                                        .addComponent(vColsRb)
                                        .addComponent(hRowsRb)
                                        .addComponent(hColsRb))
                                .addContainerGap(249, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(vRowsRb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(vColsRb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hRowsRb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hColsRb)
                                .addContainerGap(174, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton hColsRb;
    private javax.swing.JRadioButton hRowsRb;
    private javax.swing.JRadioButton vColsRb;
    private javax.swing.JRadioButton vRowsRb;
    // End of variables declaration//GEN-END:variables

}
