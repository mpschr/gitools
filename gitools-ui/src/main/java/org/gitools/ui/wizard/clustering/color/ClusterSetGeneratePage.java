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
 * ClusterSetGeneratePage.java
 *
 * Created on Oct 1, 2010, 11:17:31 AM
 */

package org.gitools.ui.wizard.clustering.color;

import cern.colt.matrix.ObjectMatrix1D;
import edu.upf.bg.colorscale.util.ColorUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import org.apache.commons.lang.ArrayUtils;
import org.gitools.heatmap.model.Heatmap;
import org.gitools.heatmap.model.HeatmapCluster;
import org.gitools.heatmap.model.HeatmapClusterSet;
import org.gitools.heatmap.model.HeatmapHeader;
import org.gitools.heatmap.model.HeatmapHeaderDecoration;
import org.gitools.matrix.model.AnnotationMatrix;
import org.gitools.matrix.model.IMatrix;
import org.gitools.ui.dialog.ListDialog;
import org.gitools.ui.heatmap.panel.properties.HeatmapPropertiesHeaderPanel;
import org.gitools.ui.platform.wizard.AbstractWizardPage;
import org.gitools.ui.utils.DocumentChangeListener;

/**
 *
 * @author mschroeder
 */
public class ClusterSetGeneratePage extends AbstractWizardPage {

	private HeatmapClusterSet clusterSet;
	private boolean rowMode;
	private Heatmap heatmap;

    /** Creates new form ClusterSetGeneratePage */
    public ClusterSetGeneratePage(Heatmap heatmap, HeatmapClusterSet clusterSet, boolean rowMode) {
        initComponents();
		this.clusterSet = clusterSet;
		this.rowMode = rowMode;
		this.heatmap = heatmap;


		labelPattern.setText(
				rowMode ? heatmap.getRowHeader().getLabelPattern() :
					heatmap.getColumnHeader().getLabelPattern());
		labelPattern.getDocument().addDocumentListener(new DocumentChangeListener() {

			@Override
			protected void update(DocumentEvent e) {
				validateLabelPattern();
			}
		});

		validateLabelPattern();
    }


	@Override
	public void updateModel() {
		generateColorClusterSet(clusterSet);
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        labelPattern = new javax.swing.JTextField();
        attributePatternBtn = new javax.swing.JButton();

        jLabel11.setText("Pattern");

        attributePatternBtn.setText("...");
        attributePatternBtn.setToolTipText("Select attribute pattern");
        attributePatternBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attributePatternBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(97, 97, 97))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelPattern, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addComponent(attributePatternBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPattern, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attributePatternBtn))
                .addContainerGap(242, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void validateLabelPattern() {
		if (!labelPattern.getText().isEmpty())
			setComplete(true);
				else
			setComplete(false);
	}

	private void generateColorClusterSet(HeatmapClusterSet clusterSet) {


		HeatmapHeader heatmapHeader = rowMode ?
			heatmap.getRowHeader() :
			heatmap.getColumnHeader();
		IMatrix contents = heatmap.getMatrixView().getContents();
		String pattern = labelPattern.getText();

		int count = rowMode ?
			contents.getRowCount() :
			contents.getColumnCount();
		HeatmapHeaderDecoration decoration = new HeatmapHeaderDecoration();

		HeatmapCluster[] clusters = new HeatmapCluster[0];
		int[] clusterIndices = new int[count];
		Map<String, Integer> clusterIndicesMap = new HashMap<String, Integer>();

		String element;
		for (int index = 0; index < count; index++) {
			if (rowMode) {
				String label =
						contents.getRowLabel(index);
				element = heatmapHeader.expandPattern(
						heatmapHeader.getAnnotations(),
						label,
						pattern);
			}
			else {
				String label =
						contents.getColumnLabel(index);
				element = heatmapHeader.expandPattern(
						heatmapHeader.getAnnotations(),
						label,
						pattern);
			}

			if (clusterIndicesMap.get(element) == null) {
				int clusterIndex = clusterIndicesMap.size();

				HeatmapCluster hc = new HeatmapCluster();
				hc.setColor(ColorUtils.getColorForIndex(clusterIndex));
				hc.setName(element);

				clusterIndicesMap.put(element, clusterIndex);
				clusters = (HeatmapCluster[]) ArrayUtils.add(clusters, hc);
			}

			clusterIndices[index] = clusterIndicesMap.get(element);
		}


		clusterSet.setTitle(pattern);
		clusterSet.setLabelRotated(!rowMode);
		clusterSet.setClusters(clusters);
		clusterSet.setClusterIndices(clusterIndices);

	}


	private static class AnnAttr {
		private String name;

		public AnnAttr() { }

		public AnnAttr(String name) {
			this.name = name; }

		public String getName() {
			return name; }

		public String getPattern() {
			return "${" + name + "}"; }

		@Override public String toString() {
			return getName(); }
	}

	private void attributePatternBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attributePatternBtnActionPerformed

		List<AnnAttr> attributes = new ArrayList<AnnAttr>();
		attributes.add(new AnnAttr() {
			@Override public String getName() {
				return "ID"; }

			@Override public String getPattern() {
				return "${id}"; }
		});


		HeatmapHeader h = rowMode ?
			heatmap.getRowHeader() :
			heatmap.getColumnHeader();
		
		AnnotationMatrix annMatrix = h.getAnnotations();

		if (annMatrix != null) {
			ObjectMatrix1D columns = annMatrix.getColumns();

			for (int i = 0; i < columns.size(); i++)
				attributes.add(new AnnAttr(columns.getQuick(i).toString()));
		}

		AnnAttr[] attributesArray = new AnnAttr[attributes.size()];
		attributes.toArray(attributesArray);

		ListDialog<AnnAttr> dlg = new ListDialog<AnnAttr>(null, true, attributesArray);
		dlg.setTitle("Select annotation ...");
		dlg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dlg.setVisible(true);

		if (dlg.getReturnStatus() == ListDialog.RET_OK) {
			AnnAttr attr = dlg.getSelectedObject();
			try {
				//TODO remove selected text before
				labelPattern.getDocument().insertString(labelPattern.getCaretPosition(), attr.getPattern(), null);
			} catch (BadLocationException ex) {
				Logger.getLogger(HeatmapPropertiesHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
}//GEN-LAST:event_attributePatternBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attributePatternBtn;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JTextField labelPattern;
    // End of variables declaration//GEN-END:variables

}
