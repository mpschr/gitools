/*
 *  Copyright 2009 chris.
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
 * HeaderPropertiesDetailsPanel.java
 *
 * Created on 12-dic-2009, 17:29:25
 */

package org.gitools.ui.view.properties.panels;

import java.beans.PropertyChangeEvent;
import javax.swing.event.DocumentEvent;
import org.gitools.ui.utils.DocumentChangeListener;

public class HeatmapPropertiesDocumentPanel extends HeatmapPropertiesAbstractPanel {

    /** Creates new form HeaderPropertiesDetailsPanel */
    public HeatmapPropertiesDocumentPanel() {
        initComponents();
    }

	@Override
	protected void initControls() {
		title.getDocument().addDocumentListener(new DocumentChangeListener() {
			@Override protected void update(DocumentEvent e) {
				hm.setTitle(title.getText()); }
		});

		description.getDocument().addDocumentListener(new DocumentChangeListener() {
			@Override protected void update(DocumentEvent e) {
				hm.setDescription(description.getText()); }
		});
	}

	@Override
	protected void updateControls() {
		title.setText(hm.getTitle());
		description.setText(hm.getDescription());
	}

	@Override
	protected void heatmapPropertyChange(PropertyChangeEvent evt) {
		/*if (evt.getPropertyName().equals(Artifact.TITLE_CHANGED))
			title.setText(hm.getTitle());
		if (evt.getPropertyName().equals(Artifact.DESC_CHANGED))
			description.setText(hm.getDescription());*/
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
        jScrollPane1 = new javax.swing.JScrollPane();
        title = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();

        jLabel1.setText("Title");

        title.setColumns(10);
        title.setRows(2);
        jScrollPane1.setViewportView(title);

        jLabel2.setText("Description");

        description.setColumns(10);
        description.setLineWrap(true);
        description.setRows(5);
        description.setWrapStyleWord(true);
        jScrollPane2.setViewportView(description);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea description;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea title;
    // End of variables declaration//GEN-END:variables

}
