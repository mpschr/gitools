/*
 *  Copyright 2010 mschroeder.
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
 * ClusterSetEditorPage.java
 *
 * Created on Oct 1, 2010, 2:50:12 PM
 */

package org.gitools.ui.wizard.clustering.color;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.gitools.heatmap.model.HeatmapCluster;
import org.gitools.heatmap.model.HeatmapClusterSet;
import org.gitools.ui.platform.component.ColorChooserLabel.ColorChangeListener;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.gitools.ui.utils.DocumentChangeListener;

/**
 *
 * @author mschroeder
 */
public class ClusterSetEditorPage extends AbstractWizardPage {

	HeatmapClusterSet clusterSet;

    /** Creates new form ClusterSetEditorPage */
    public ClusterSetEditorPage(final HeatmapClusterSet clusterSet) {
        initComponents();
		this.clusterSet = clusterSet;
		setComplete(true);

		clusterList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = clusterList.getSelectedIndex();
				HeatmapCluster hc = clusterSet.getClusters()[selected];
				clusterName.setText(hc.getName());
				fgColor.setColor(hc.getColor());
			}
		});

		clusterName.getDocument().addDocumentListener(new DocumentChangeListener() {
			@Override
			protected void update(DocumentEvent e) {
				int selected = clusterList.getSelectedIndex();
				HeatmapCluster[] clusters = clusterSet.getClusters();
				HeatmapCluster hc = clusters[selected];
				hc.setName(clusterName.getText());
				clusters[selected] = hc;
				clusterSet.setClusters(clusters);
				clusterList.updateUI();
			}
		});

		fgColor.addColorChangeListener(new ColorChangeListener() {
			@Override public void colorChanged(Color color) {
				int selected = clusterList.getSelectedIndex();
				HeatmapCluster[] clusters = clusterSet.getClusters();
				HeatmapCluster hc = clusters[selected];
				hc.setColor(fgColor.getColor());
				clusters[selected] = hc;
				clusterSet.setClusters(clusters);
				clusterList.updateUI();
			}
		});

		clusterSetName.getDocument().addDocumentListener(new DocumentChangeListener() {
			@Override
			protected void update(DocumentEvent e) {
				clusterSet.setTitle(clusterSetName.getText());
				clusterList.updateUI();
			}
		});

		size.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SpinnerNumberModel m = (SpinnerNumberModel) size.getModel();
				clusterSet.setSize(m.getNumber().intValue());
			}
		});

    }

	@Override
	public void updateControls() {
		HeatmapCluster[] clusters = this.clusterSet.getClusters();

		clusterSetName.setText(this.clusterSet.getTitle());

		size.setValue(clusterSet.getSize());

		visible.setSelected(clusterSet.isVisible());

		DefaultListModel model = new DefaultListModel();
		clusterList.removeAll();
		clusterList.setModel(model);

		for (int x = 0; x < clusters.length; x++)
			model.addElement(clusters[x]);
	}


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        fgColor = new org.gitools.ui.platform.component.ColorChooserLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        clusterSetName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        clusterList = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        clusterName = new javax.swing.JTextField();
        headerSizeLabel = new javax.swing.JLabel();
        size = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        visible = new javax.swing.JCheckBox();
        headerSizeLabel1 = new javax.swing.JLabel();

        jLabel1.setText("Name");

        jLabel4.setText("Name");

        jLabel12.setText(" List");

        clusterList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(clusterList);

        jLabel3.setText("Color");

        clusterName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clusterNameActionPerformed(evt);
            }
        });

        headerSizeLabel.setText("Width");

        jLabel5.setFont(new java.awt.Font("DejaVu LGC Sans", 1, 11));
        jLabel5.setText("Cluster Set");

        jLabel6.setFont(new java.awt.Font("DejaVu LGC Sans", 1, 11));
        jLabel6.setText("Cluster");

        visible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visibleActionPerformed(evt);
            }
        });

        headerSizeLabel1.setText("Visible");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(clusterSetName, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(headerSizeLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(visible)
                            .addComponent(headerSizeLabel1)))
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                                .addGap(247, 247, 247))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fgColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                .addGap(244, 244, 244))
                            .addComponent(clusterName, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clusterSetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerSizeLabel)
                            .addComponent(headerSizeLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(visible)
                            .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clusterName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fgColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void clusterNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clusterNameActionPerformed
		// TODO add your handling code here:
}//GEN-LAST:event_clusterNameActionPerformed

	private void visibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visibleActionPerformed
		clusterSet.setVisible(visible.isSelected());
	}//GEN-LAST:event_visibleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList clusterList;
    private javax.swing.JTextField clusterName;
    private javax.swing.JTextField clusterSetName;
    private org.gitools.ui.platform.component.ColorChooserLabel fgColor;
    private javax.swing.JLabel headerSizeLabel;
    private javax.swing.JLabel headerSizeLabel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner size;
    private javax.swing.JCheckBox visible;
    // End of variables declaration//GEN-END:variables

}
