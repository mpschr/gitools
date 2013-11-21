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
package org.gitools.ui.analysis.wizard;

import org.gitools.ui.IconNames;
import org.gitools.ui.platform.IconUtils;
import org.gitools.ui.platform.dialog.MessageStatus;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.gitools.ui.settings.Settings;
import org.gitools.ui.utils.DocumentChangeListener;
import org.gitools.ui.utils.FileChooserUtils;
import org.gitools.utils.cutoffcmp.CutoffCmp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

/**
 * @noinspection ALL
 */
public class DataFilterPage extends AbstractWizardPage {

    private static final long serialVersionUID = 3840797252370672587L;

    /**
     * Creates new form DataSourcePanel
     */
    public DataFilterPage() {
        setTitle("Select data filtering options");

        setLogo(IconUtils.getImageIconResourceScaledByHeight(IconNames.LOGO_DATA, 96));

        initComponents();

        DocumentChangeListener docCompleteListener = new DocumentChangeListener() {
            @Override
            protected void update(DocumentEvent e) {
                updateState();
            }
        };

        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateState();
            }
        };

        populationCheck.addItemListener(itemListener);
        populationFilePath.getDocument().addDocumentListener(docCompleteListener);

        cutoffEnabledCheck.addItemListener(itemListener);
        String[] cmpNames = new String[CutoffCmp.comparators.length];
        for (int i = 0; i < cmpNames.length; i++)
            cmpNames[i] = CutoffCmp.comparators[i].getLongName();
        cutoffCmpCb.setModel(new DefaultComboBoxModel(cmpNames));
        cutoffCmpCb.setSelectedItem(CutoffCmp.GE.getLongName());

        cutoffValue.getDocument().addDocumentListener(docCompleteListener);
        cutoffValue.setText("1.5");

        discardNonMappedRowsCheck.addItemListener(itemListener);
        discardNonMappedRowsCheck.setVisible(false);
    }

    private void updateState() {
        boolean binaryFilterEnabled = true; //FIXME

        cutoffEnabledCheck.setEnabled(binaryFilterEnabled);
        cutoffCmpCb.setEnabled(binaryFilterEnabled && cutoffEnabledCheck.isSelected());
        cutoffValue.setEnabled(binaryFilterEnabled && cutoffEnabledCheck.isSelected());

        setMessage(MessageStatus.INFO, "");

        boolean complete = true;

        if (cutoffEnabledCheck.isSelected()) {
            boolean fail = false;
            try {
                Double.valueOf(cutoffValue.getText());
            } catch (NumberFormatException e) {
                fail = true;
                setStatus(MessageStatus.ERROR);
                setMessage("Cutoff value should be a real number.");
            }

            complete = !fail && !cutoffValue.getText().isEmpty();
        }

        String rowsFilterPath = populationFilePath.getText();
        if (!rowsFilterPath.isEmpty()) {
            File rowsFilterFile = new File(rowsFilterPath);
            if (!rowsFilterFile.exists()) {
                complete = false;
                setMessage(MessageStatus.ERROR, "File not found: " + rowsFilterPath);
            }
        }

        boolean populationChecked = populationCheck.isSelected();
        populationFilePath.setEnabled(populationChecked);
        populationFileBrowserBtn.setEnabled(populationChecked);
        populationNote.setEnabled(populationChecked);
        populationDefaultValueLabel.setEnabled(populationChecked);
        populationDefaultValueCb.setEnabled(populationChecked);

        if (populationChecked && populationFilePath.getDocument().getLength() == 0) {
            complete = false;
            setMessage(MessageStatus.ERROR, "Select a file containing rows");
        }

        setComplete(complete);
    }


    @Override
    public JComponent createControls() {
        return this;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        cutoffEnabledCheck = new javax.swing.JCheckBox();
        cutoffCmpCb = new javax.swing.JComboBox();
        cutoffValue = new javax.swing.JTextField();
        populationFilePath = new javax.swing.JTextField();
        populationFileBrowserBtn = new javax.swing.JButton();
        discardNonMappedRowsCheck = new javax.swing.JCheckBox();
        populationCheck = new javax.swing.JCheckBox();
        populationNote = new javax.swing.JLabel();
        populationDefaultValueLabel = new javax.swing.JLabel();
        populationDefaultValueCb = new javax.swing.JComboBox();

        cutoffEnabledCheck.setText("Transform to 1 (0 otherwise) cells with value");
        cutoffEnabledCheck.setEnabled(false);

        cutoffCmpCb.setEnabled(false);

        cutoffValue.setColumns(6);
        cutoffValue.setEnabled(false);

        populationFileBrowserBtn.setText("Browse...");
        populationFileBrowserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                populationFileBrowserBtnActionPerformed(evt);
            }
        });

        discardNonMappedRowsCheck.setText("Filter out rows for which no information appears in the modules");

        populationCheck.setText("Population / Background elements:");

        populationNote.setFont(populationNote.getFont().deriveFont(populationNote.getFont().getSize() - 2f));
        populationNote.setText("This should be a file containing one row id per line.");

        populationDefaultValueLabel.setText("Default value to use when a row doesn't exist in the data");

        populationDefaultValueCb.setEditable(true);
        populationDefaultValueCb.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"0", "Empty"}));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(populationCheck).addGroup(layout.createSequentialGroup().addGap(12, 12, 12).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(populationNote).addGroup(layout.createSequentialGroup().addComponent(populationFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(populationFileBrowserBtn)).addGroup(layout.createSequentialGroup().addComponent(populationDefaultValueLabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(populationDefaultValueCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGroup(layout.createSequentialGroup().addComponent(cutoffEnabledCheck).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cutoffCmpCb, 0, 224, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cutoffValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(discardNonMappedRowsCheck)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(populationCheck).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(populationFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(populationFileBrowserBtn)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(populationNote).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(populationDefaultValueLabel).addComponent(populationDefaultValueCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(46, 46, 46).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cutoffEnabledCheck).addComponent(cutoffCmpCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cutoffValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addComponent(discardNonMappedRowsCheck).addContainerGap(141, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void populationFileBrowserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_populationFileBrowserBtnActionPerformed
        File selPath = FileChooserUtils.selectFile("Select file", Settings.getDefault().getLastDataPath(), FileChooserUtils.MODE_OPEN).getFile();

        if (selPath != null) {
            populationFilePath.setText(selPath.getAbsolutePath());
            Settings.getDefault().setLastDataPath(selPath.getAbsolutePath());
        }
    }//GEN-LAST:event_populationFileBrowserBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cutoffCmpCb;
    private javax.swing.JCheckBox cutoffEnabledCheck;
    private javax.swing.JTextField cutoffValue;
    private javax.swing.JCheckBox discardNonMappedRowsCheck;
    private javax.swing.JCheckBox populationCheck;
    private javax.swing.JComboBox populationDefaultValueCb;
    private javax.swing.JLabel populationDefaultValueLabel;
    private javax.swing.JButton populationFileBrowserBtn;
    private javax.swing.JTextField populationFilePath;
    private javax.swing.JLabel populationNote;
    // End of variables declaration//GEN-END:variables

    public void setRowsFilterFileVisible(boolean visible) {
        populationCheck.setVisible(visible);
        populationFilePath.setVisible(visible);
        populationFileBrowserBtn.setVisible(visible);
        populationNote.setVisible(visible);
    }


    public File getRowsFilterFile() {
        String text = populationFilePath.getText();
        return populationCheck.isSelected() && !text.isEmpty() ? new File(text) : null;
    }

    public boolean isBinaryCutoffEnabled() {
        return cutoffEnabledCheck.isSelected();
    }

    public void setBinaryCutoffEnabled(boolean enabled) {
        cutoffEnabledCheck.setSelected(enabled);
    }

    public CutoffCmp getBinaryCutoffCmp() {
        return CutoffCmp.getFromName((String) cutoffCmpCb.getSelectedItem());
    }

    public void setBinaryCutoffCmp(CutoffCmp cmp) {
        cutoffCmpCb.setSelectedItem(cmp.getLongName());
    }

    public double getBinaryCutoffValue() {
        return Double.parseDouble(cutoffValue.getText());
    }

    public void setBinaryCutoffValue(double value) {
        cutoffValue.setText(Double.toString(value));
    }

    public boolean isDiscardNonMappedRowsEnabled() {
        return discardNonMappedRowsCheck.isSelected();
    }

    public void setDiscardNonMappedRowsEnabled(boolean enabled) {
        discardNonMappedRowsCheck.setSelected(enabled);
    }

    public boolean isDiscardNonMappedRowsVisible() {
        return discardNonMappedRowsCheck.isVisible();
    }

    public void setDiscardNonMappedRowsVisible(boolean visible) {
        discardNonMappedRowsCheck.setVisible(visible);
    }

    public Double getPopulationDefaultValue() {
        String value = (String) populationDefaultValueCb.getSelectedItem();
        Double v = Double.NaN;
        try {
            v = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
        }
        return v;
    }


}
