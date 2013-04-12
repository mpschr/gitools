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
package org.gitools.ui.heatmap.header.wizard.textlabels;

import org.gitools.heatmap.HeatmapDimension;
import org.gitools.heatmap.header.HeatmapTextLabelsHeader;
import org.gitools.matrix.model.AnnotationMatrix;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextLabelsSourcePage extends AbstractWizardPage
{

    private final HeatmapDimension hdim;
    private final HeatmapTextLabelsHeader header;

    public TextLabelsSourcePage(HeatmapDimension hdim, HeatmapTextLabelsHeader header)
    {
        this.hdim = hdim;
        this.header = header;

        initComponents();

        ActionListener optionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                optionsChanged();
            }
        };

        idOpt.addActionListener(optionListener);
        annOpt.addActionListener(optionListener);
        patOpt.addActionListener(optionListener);

        setTitle("Select the contents of the header");
        setComplete(true);
    }

    @Override
    public void updateControls()
    {
        super.updateControls();

        switch (header.getLabelSource())
        {
            case ID:
                idOpt.setSelected(true);
                break;
            case ANNOTATION:
                annOpt.setSelected(true);
                break;
            case PATTERN:
                patOpt.setSelected(true);
                break;
        }

        AnnotationMatrix am = hdim.getAnnotations();
        if (am != null && am.getColumnCount() > 0)
        {
            DefaultListModel model = new DefaultListModel();
            for (int i = 0; i < am.getColumnCount(); i++)
                model.addElement(am.getColumnLabel(i));
            annList.setModel(model);

            String label = header.getLabelAnnotation();
            int annIndex = am.getColumnIndex(label);
            if (annIndex > -1)
            {
                annList.setSelectedIndex(annIndex);
            }
        }

        pattText.setText(header.getLabelPattern());

        optionsChanged();
    }

    @Override
    public void updateModel()
    {
        super.updateModel();

        header.setLabelSource(getLabelSource());
        header.setLabelAnnotation(getAnnotation());
        header.setLabelPattern(getPattern());
    }

    @NotNull
    HeatmapTextLabelsHeader.LabelSource getLabelSource()
    {
        if (idOpt.isSelected())
        {
            return HeatmapTextLabelsHeader.LabelSource.ID;
        }
        else if (annOpt.isSelected())
        {
            return HeatmapTextLabelsHeader.LabelSource.ANNOTATION;
        }
        else if (patOpt.isSelected())
        {
            return HeatmapTextLabelsHeader.LabelSource.PATTERN;
        }
        return HeatmapTextLabelsHeader.LabelSource.ID;
    }

    private void optionsChanged()
    {
        annList.setEnabled(annOpt.isSelected());
        pattText.setEnabled(patOpt.isSelected());
    }

    @NotNull
    String getAnnotation()
    {
        if (annList.getSelectedIndex() != -1)
        {
            return (String) annList.getSelectedValue();
        }
        else
        {
            return "";
        }
    }

    String getPattern()
    {
        return pattText.getText();
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

        optGroup = new javax.swing.ButtonGroup();
        idOpt = new javax.swing.JRadioButton();
        annOpt = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        annList = new javax.swing.JList();
        patOpt = new javax.swing.JRadioButton();
        pattText = new javax.swing.JTextField();

        optGroup.add(idOpt);
        idOpt.setText("The ID");

        optGroup.add(annOpt);
        annOpt.setText("An annotation");

        annList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        annList.setEnabled(false);
        jScrollPane1.setViewportView(annList);

        optGroup.add(patOpt);
        patOpt.setText("A pattern");

        pattText.setText("${id}");
        pattText.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGap(24, 24, 24).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(idOpt).addComponent(annOpt))).addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(12, 12, 12).addComponent(pattText, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)).addComponent(patOpt)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(idOpt).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(annOpt).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE).addGap(18, 18, 18).addComponent(patOpt).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(pattText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList annList;
    private javax.swing.JRadioButton annOpt;
    private javax.swing.JRadioButton idOpt;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.ButtonGroup optGroup;
    private javax.swing.JRadioButton patOpt;
    private javax.swing.JTextField pattText;
    // End of variables declaration//GEN-END:variables

}
