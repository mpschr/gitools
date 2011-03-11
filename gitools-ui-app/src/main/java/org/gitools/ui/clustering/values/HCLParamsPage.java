/*
 *  Copyright 2011 chris.
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
 * ColoredClustersAnnotationsPage.java
 *
 * Created on 02-mar-2011, 8:08:28
 */

package org.gitools.ui.clustering.values;

import java.util.List;
import org.gitools.clustering.ClusteringMethod;
import org.gitools.clustering.ClusteringMethodDescriptor;
import org.gitools.clustering.ClusteringMethodFactory;
import org.gitools.clustering.method.value.AbstractClusteringValueMethod;
import org.gitools.clustering.method.value.WekaHCLMethod;
import org.gitools.clustering.method.value.WekaHierarchicalClusterer;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import weka.core.EuclideanDistance;
import weka.core.ManhattanDistance;
import weka.core.SelectedTag;

public class HCLParamsPage extends AbstractWizardPage implements ClusteringValueMethodPage {

	public HCLParamsPage() {
		
		initComponents();

		setTitle("Clustering method selection");
		setComplete(true);
	}


	@Override
	public void updateModel() {
		super.updateModel();

	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optGroup = new javax.swing.ButtonGroup();
        linkTypeCombo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        distAlgCombo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();

        linkTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single", "Complete", "Average", "Mean", "Centroid", "Ward", "Adjcomplete", "Neighbor_joining" }));

        jLabel1.setText("Link type: ");
        jLabel1.setToolTipText("Sets the method used to measure the distance between two clusters.");

        distAlgCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Euclidean", "Manhattan" }));

        jLabel7.setText("Distance algorithm: ");
        jLabel7.setToolTipText("The distance function to use for instances comparison");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(distAlgCombo, 0, 290, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(linkTypeCombo, 0, 290, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(distAlgCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(linkTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox distAlgCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox linkTypeCombo;
    private javax.swing.ButtonGroup optGroup;
    // End of variables declaration//GEN-END:variables

	@Override
	public AbstractClusteringValueMethod getMethod() {

		WekaHCLMethod method = null;

		method = (WekaHCLMethod) ClusteringMethodFactory.getDefault().create(getMethodDescriptor());

		method.setLinkType(
					new SelectedTag(linkTypeCombo.getSelectedItem().toString(),
					WekaHierarchicalClusterer.TAGS_LINK_TYPE));

		method.setDistanceIsBranchLength(false);
		method.setNumClusters(1);
		method.setPrintNewick(true);

		if (distAlgCombo.getSelectedItem().toString().equalsIgnoreCase("euclidean"))
			method.setDistanceFunction(new EuclideanDistance());
		else
			method.setDistanceFunction(new ManhattanDistance());

		return method;
	}

	@Override
	public ClusteringMethodDescriptor getMethodDescriptor() {
		List<ClusteringMethodDescriptor> descriptors = ClusteringMethodFactory.getDefault().getDescriptors();

		for (ClusteringMethodDescriptor desc : descriptors)
			if (desc.getMethodClass().equals(WekaHCLMethod.class))
				return desc;

		return null;
	}
}
