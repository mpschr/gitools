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
package org.gitools.ui.scale;

import org.gitools.ui.platform.dialog.MessageStatus;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.gitools.ui.utils.DocumentChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScaleExportConfigPage extends AbstractWizardPage
{

    /**
     * Creates new form ScaleExportConfigPage
     */
    public ScaleExportConfigPage()
    {
        initComponents();

        setTitle("Scale export settings");

        DocumentChangeListener docListener = new DocumentChangeListener()
        {
            @Override
            protected void update(DocumentEvent e)
            {
                validateValues();
            }
        };

        partialRangeCheck.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                rangeMin.setEnabled(partialRangeCheck.isSelected());
                rangeMax.setEnabled(partialRangeCheck.isSelected());
            }
        });

        scaleSize.getDocument().addDocumentListener(docListener);
        rangeMin.getDocument().addDocumentListener(docListener);
        rangeMax.getDocument().addDocumentListener(docListener);

        setComplete(true);
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

        jLabel1 = new javax.swing.JLabel();
        partialRangeCheck = new javax.swing.JCheckBox();
        rangeMin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        rangeMax = new javax.swing.JTextField();
        scaleSize = new javax.swing.JTextField();

        jLabel1.setText("Size (in pixels)");

        partialRangeCheck.setText("Export only values from");

        rangeMin.setColumns(5);
        rangeMin.setText("0");
        rangeMin.setEnabled(false);

        jLabel2.setText("to");

        rangeMax.setColumns(5);
        rangeMax.setText("0");
        rangeMax.setEnabled(false);

        scaleSize.setColumns(6);
        scaleSize.setText("1024");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scaleSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(partialRangeCheck)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rangeMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rangeMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(175, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(scaleSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(partialRangeCheck)
                                        .addComponent(rangeMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(rangeMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(340, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public JComponent createControls()
    {
        return this;
    }

    protected void validateValues()
    {
        setComplete(true);
        setMessage(MessageStatus.INFO, "");

        try
        {
            Double.parseDouble(rangeMin.getText());
        } catch (NumberFormatException ex)
        {
            setComplete(false);
            setMessage(MessageStatus.ERROR, "Invalid range value: " + rangeMin.getText());
        }

        try
        {
            Double.parseDouble(rangeMax.getText());
        } catch (NumberFormatException ex)
        {
            setComplete(false);
            setMessage(MessageStatus.ERROR, "Invalid range value: " + rangeMax.getText());
        }

        try
        {
            Integer.parseInt(scaleSize.getText());
        } catch (NumberFormatException ex)
        {
            setComplete(false);
            setMessage(MessageStatus.ERROR, "Invalid size: " + scaleSize.getText());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JCheckBox partialRangeCheck;
    private javax.swing.JTextField rangeMax;
    private javax.swing.JTextField rangeMin;
    private javax.swing.JTextField scaleSize;
    // End of variables declaration//GEN-END:variables

    public void setRange(double min, double max)
    {
        rangeMin.setText(Double.toString(min));
        rangeMax.setText(Double.toString(max));
    }

    public boolean isPartialRange()
    {
        return partialRangeCheck.isSelected();
    }

    public double getRangeMin()
    {
        return Double.parseDouble(rangeMin.getText());
    }

    public double getRangeMax()
    {
        return Double.parseDouble(rangeMax.getText());
    }

    public int getScaleSize()
    {
        return Integer.parseInt(scaleSize.getText());
    }
}
