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

import org.gitools.persistence.formats.FileFormat;
import org.gitools.ui.IconNames;
import org.gitools.ui.platform.IconUtils;
import org.gitools.ui.platform.dialog.MessageStatus;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.gitools.ui.utils.DocumentChangeListener;
import org.gitools.ui.utils.FileChooserUtils;
import org.gitools.ui.utils.FileFormatFilter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @noinspection ALL
 */
public class SelectFilePage extends AbstractWizardPage {

    private static final long serialVersionUID = 3840797252370672587L;

    private static final FileFormat anyFileFormat = new FileFormat("Any file format", "", false, false);

    private static final FileFormat[] defaultFormats = new FileFormat[]{anyFileFormat};


    private final FileFormat[] formats;
    private boolean blankFileAllowed;
    private String lastPath;

    public SelectFilePage(FileFormat[] formats) {
        setTitle("Select file");

        setLogo(IconUtils.getImageIconResourceScaledByHeight(IconNames.LOGO_SELECT_FILE, 96));

        initComponents();

        this.formats = formats != null ? formats : defaultFormats;
        blankFileAllowed = false;

        formatCb.setModel(new DefaultComboBoxModel(formats));
        formatCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateState();
            }
        });

        DocumentChangeListener docCompleteListener = new DocumentChangeListener() {
            @Override
            protected void update(DocumentEvent e) {
                updateState();
            }
        };

        valueCb.setVisible(false);
        valueLabel.setVisible(false);

        filePath.getDocument().addDocumentListener(docCompleteListener);
    }

    void updateState() {
        FileFormat ff = getFileFormat();

        setMessage(MessageStatus.INFO, "");

        boolean complete = true;

        String path = filePath.getText().trim().toLowerCase();
        if (!path.isEmpty()) {
            if (!ff.checkExtension(path)) {
                setMessage(MessageStatus.WARN, "The file extension doesn't match the selected format");
            }
        }

        complete = blankFileAllowed || !filePath.getText().isEmpty();

        path = filePath.getText();
        if (!path.isEmpty()) {
            File rowsFilterFile = new File(path);
            if (!rowsFilterFile.exists()) {
                //complete = false;
                setMessage(MessageStatus.WARN, "File not found: " + path);
            }
        }

        setComplete(complete);
    }


    @Override
    public JComponent createControls() {
        return this;
    }

    public void setBlankFileAllowed(boolean allowed) {
        this.blankFileAllowed = allowed;
        updateState();
    }

    protected String getLastPath() {
        if (lastPath == null) {
            lastPath = new File(".").getAbsolutePath();
        }
        return lastPath;
    }

    protected void setLastPath(String path) {
        this.lastPath = path;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        formatCb = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        filePath = new javax.swing.JTextField();
        fileBrowseBtn = new javax.swing.JButton();
        valueLabel = new javax.swing.JLabel();
        valueCb = new javax.swing.JComboBox();

        jLabel1.setText("Format");

        formatCb.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Binary data matrix", "Continuous data matrix"}));

        jLabel2.setText("File");

        fileBrowseBtn.setText("Browse...");
        fileBrowseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileBrowseBtnActionPerformed(evt);
            }
        });

        valueLabel.setText("Value");
        valueLabel.setEnabled(false);

        valueCb.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Value1", "Value2"}));
        valueCb.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(formatCb, 0, 576, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(filePath, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(fileBrowseBtn)).addGroup(layout.createSequentialGroup().addComponent(valueLabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(valueCb, 0, 586, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(formatCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(fileBrowseBtn).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(filePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel2))).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(valueLabel).addComponent(valueCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(238, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void fileBrowseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileBrowseBtnActionPerformed
        boolean anyFormat = formats.length == 1 && formats[0] == anyFileFormat;

        FileFormatFilter any = new FileFormatFilter(anyFileFormat.getTitle(), anyFileFormat);
        FileFormatFilter[] filters = null;
        if (anyFormat) {
            filters = new FileFormatFilter[]{any};
        } else {
            filters = new FileFormatFilter[formats.length + 2];
            filters[0] = any;
            filters[1] = new FileFormatFilter("Known formats", formats);
            for (int i = 0; i < formats.length; i++)
                filters[i + 2] = new FileFormatFilter(formats[i]);
        }

        File selPath = FileChooserUtils.selectFile("Select file", getLastPath(), FileChooserUtils.MODE_OPEN, filters).getFile();

        if (selPath != null) {
            setFile(selPath);
            setLastPath(selPath.getAbsolutePath());
        }
    }//GEN-LAST:event_fileBrowseBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileBrowseBtn;
    private javax.swing.JTextField filePath;
    private javax.swing.JComboBox formatCb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox valueCb;
    private javax.swing.JLabel valueLabel;
    // End of variables declaration//GEN-END:variables


    public FileFormat getFileFormat() {
        return (FileFormat) formatCb.getSelectedItem();
    }


    public File getFile() {
        String path = filePath.getText();
        return path.isEmpty() ? null : new File(path);
    }

    public void setFile(File file) {
        String fileName = file.getName();
        for (FileFormat f : formats)
            if (f.checkExtension(fileName)) {
                formatCb.setSelectedItem(f);
                break;
            }

        filePath.setText(file.getAbsolutePath());

        updateState();
    }

    void activateValueSelection() {
        valueCb.setVisible(true);
        valueLabel.setVisible(true);
        valueCb.setEnabled(true);
        valueLabel.setEnabled(true);
    }

    void deactivateValueSelection() {
        valueCb.setEnabled(false);
        valueLabel.setEnabled(false);
        valueCb.setVisible(false);
        valueLabel.setVisible(false);
    }

    void setValues(String[] values) {
        valueCb.setModel(new DefaultComboBoxModel(values));
    }

    public int getSelectedValueIndex() {
        if (valueCb.isEnabled() == true) {
            return valueCb.getSelectedIndex();
        } else {
            return -1;  /* return -1 if not enabled */
        }
    }

    /**
     * @noinspection UnusedDeclaration
     */

    public String[] getValues() {
        //if (valueCb.isEnabled() == true)
        //TODO: return items
        return new String[0];
    }
}
