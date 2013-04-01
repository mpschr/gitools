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
package org.gitools.ui.heatmap.header.wizard.coloredlabels;

import org.gitools.clustering.HierarchicalClusteringResults;
import org.gitools.heatmap.header.HeatmapHierarchicalColoredLabelsHeader;
import org.gitools.newick.NewickTree;
import org.gitools.ui.platform.wizard.AbstractWizardPage;

import javax.swing.*;


public class HclLevelPage extends AbstractWizardPage
{

    private HeatmapHierarchicalColoredLabelsHeader header;

    public HclLevelPage(HeatmapHierarchicalColoredLabelsHeader header)
    {
        this.header = header;

        initComponents();

        DefaultListModel model = new DefaultListModel();
        HierarchicalClusteringResults hclResults = header.getClusteringResults();
        NewickTree tree = hclResults.getTree();
        int maxLevels = tree.getDepth() - 1;
        if (maxLevels < 1)
        {
            maxLevels = 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxLevels; i++)
        {
            sb.append("Level ").append(i).append(": ");
            sb.append(tree.getRoot().getLeaves(i).size());
            sb.append(" clusters");
            model.addElement(sb.toString());
            sb.setLength(0);
        }

        levels.setModel(model);

        setTitle("Hierarchical clustering tree level");
        setComplete(true);
    }

    @Override
    public void updateControls()
    {
        super.updateControls();

        levels.setSelectedIndex(header.getTreeLevel());
    }

    public int getLevel()
    {
        int level = levels.getSelectedIndex();
        if (level == -1)
        {
            level = 0;
        }
        return level;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        levels = new javax.swing.JList();

        levels.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(levels);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList levels;
    // End of variables declaration//GEN-END:variables

}
