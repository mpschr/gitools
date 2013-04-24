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
package org.gitools.ui.modules.wizard;

import org.gitools.core.modules.importer.ModuleCategory;
import org.gitools.core.modules.importer.ModulesImporter;
import org.gitools.core.modules.importer.Version;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class ModulesSourcePage extends AbstractWizardPage {

    private final ModulesImporter importer;

    /**
     * Creates new form ModulesSourcePage
     */
    public ModulesSourcePage(@NotNull ModulesImporter importer) {
        this.importer = importer;

        setTitle("Select category and version");

        initComponents();

        modCategoryCb.setModel(new DefaultComboBoxModel(importer.getModuleCategories()));

        versionCb.setModel(new DefaultComboBoxModel(importer.getVersions()));

        setComplete(true);
    }

    @Override
    public void updateControls() {
        if (importer.getModuleCategory() != null) {
            modCategoryCb.setSelectedItem(importer.getModuleCategory());
        }

        if (importer.getVersion() != null) {
            versionCb.setSelectedItem(importer.getVersion());
        }
    }

    @Override
    public void updateModel() {
        importer.setModuleCategory((ModuleCategory) modCategoryCb.getSelectedItem());
        importer.setVersion((Version) versionCb.getSelectedItem());
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

        jLabel1 = new javax.swing.JLabel();
        modCategoryCb = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        versionCb = new javax.swing.JComboBox();
        notesLabel2 = new javax.swing.JLabel();
        notesLabel3 = new javax.swing.JLabel();
        notesLabel1 = new javax.swing.JLabel();

        jLabel1.setText("Category");

        jLabel2.setText("Ensembl version");

        notesLabel2.setFont(notesLabel2.getFont().deriveFont(notesLabel2.getFont().getSize() - 2f));
        notesLabel2.setText("You can choose which Ensembl version to work with,");

        notesLabel3.setFont(notesLabel3.getFont().deriveFont(notesLabel3.getFont().getSize() - 2f));
        notesLabel3.setText("by default the most recent version is used.");

        notesLabel1.setFont(notesLabel1.getFont().deriveFont(notesLabel1.getFont().getSize() - 2f));
        notesLabel1.setText("The Ensembl database is used to map between identifiers.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(modCategoryCb, 0, 570, Short.MAX_VALUE)).addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(notesLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE).addComponent(versionCb, 0, 523, Short.MAX_VALUE).addComponent(notesLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE).addComponent(notesLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(modCategoryCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(72, 72, 72).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(versionCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(notesLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(notesLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(notesLabel3).addContainerGap(253, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox modCategoryCb;
    private javax.swing.JLabel notesLabel1;
    private javax.swing.JLabel notesLabel2;
    private javax.swing.JLabel notesLabel3;
    private javax.swing.JComboBox versionCb;
    // End of variables declaration//GEN-END:variables

}
