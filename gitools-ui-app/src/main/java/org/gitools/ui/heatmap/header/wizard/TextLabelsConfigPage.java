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
package org.gitools.ui.heatmap.header.wizard;

import org.gitools.heatmap.header.HeatmapHeader;
import org.gitools.ui.platform.dialog.FontChooserDialog;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.gitools.ui.utils.FontUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextLabelsConfigPage extends AbstractWizardPage
{

    private HeatmapHeader header;

    private Font font;

    public TextLabelsConfigPage(HeatmapHeader header)
    {
        this.header = header;

        initComponents();

        fontBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectFont();
            }
        });

        setTitle("Header configuration");
        setComplete(true);
    }

    @Override
    public void updateControls()
    {
        font = header.getFont();
        fontChanged();
        fgColor.setColor(header.getLabelColor());
    }

    @Override
    public void updateModel()
    {
        header.setFont(font);
        header.setLabelColor(fgColor.getColor());
    }

    private void fontChanged()
    {
        fontField.setText(FontUtils.fontText(font));
        fontField.setFont(font);
    }

    private void selectFont()
    {
        FontChooserDialog dlg =
                new FontChooserDialog(null, header.getFont());
        dlg.setVisible(true);

        if (dlg.isCancelled())
        {
            return;
        }

        font = dlg.getFont();
        fontChanged();
    }

    public void setFgColorEnabled(boolean enabled)
    {
        fgColor.setEnabled(enabled);
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

        jLabel2 = new javax.swing.JLabel();
        fontField = new javax.swing.JTextField();
        fontBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fgColor = new org.gitools.ui.platform.component.ColorChooserLabel();

        jLabel2.setText("Font");

        fontField.setEditable(false);

        fontBtn.setText("...");

        jLabel1.setText("Color");

        fgColor.setColor(new java.awt.Color(1, 1, 1));
        fgColor.setPreferredSize(new java.awt.Dimension(28, 28));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fontField, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fontBtn))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fgColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(fontField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(fontBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(fgColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addGap(318, 318, 318))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.gitools.ui.platform.component.ColorChooserLabel fgColor;
    private javax.swing.JButton fontBtn;
    private javax.swing.JTextField fontField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

}
