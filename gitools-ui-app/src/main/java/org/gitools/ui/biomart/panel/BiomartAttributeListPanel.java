/*
 *  Copyright 2009 Universitat Pompeu Fabra.
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
 * BiomartAttributeSetPage.java
 *
 * Created on 18-dic-2009, 22:46:07
 */

package org.gitools.ui.biomart.panel;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.gitools.biomart.restful.model.AttributeCollection;
import org.gitools.biomart.restful.model.AttributeDescription;
import org.gitools.biomart.restful.model.AttributeGroup;
import org.gitools.biomart.restful.model.AttributePage;

import org.gitools.ui.biomart.dialog.BiomartAttributeDialog;
import org.gitools.ui.platform.AppFrame;

public class BiomartAttributeListPanel extends JPanel {

	public static interface AttributeListChangeListener {
		void listChanged();
	}

	private static class AttributeDescWrapper {
		private AttributeDescription attr;
		private String label;

		public AttributeDescWrapper(AttributeDescription attr, String label) {
			this.attr = attr;
			this.label = label;
		}

		public AttributeDescription getAttribute() {
			return attr;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	private List<AttributePage> attrPages;

	private List<AttributeListChangeListener> attributeListChangeListeners =
			new ArrayList<AttributeListChangeListener>();

    public BiomartAttributeListPanel() {
		initComponents();

		attrList.setModel(new DefaultListModel());
		attrList.getModel().addListDataListener(new ListDataListener() {
			@Override public void intervalAdded(ListDataEvent e) {
				contentsChanged(e); }
			@Override public void intervalRemoved(ListDataEvent e) {
				contentsChanged(e); }
			@Override
			public void contentsChanged(ListDataEvent e) {
				updateButtons();
				for (AttributeListChangeListener l : attributeListChangeListeners)
					l.listChanged();
			}
		});
		attrList.addListSelectionListener(new ListSelectionListener() {
			@Override public void valueChanged(ListSelectionEvent e) {
				updateButtons();
				for (AttributeListChangeListener l : attributeListChangeListeners)
					l.listChanged();
			}
		});

		updateButtons();
    }

	public List<AttributePage> getAttributePages() {
		return attrPages;
	}

	public void setAttributePages(List<AttributePage> attrPages) {
		this.attrPages = attrPages;

		DefaultListModel model = (DefaultListModel) attrList.getModel();
		if (model.size() == 0 && attrPages != null) {
			for (AttributePage p : attrPages)
				for (AttributeGroup g : p.getAttributeGroups())
					for (AttributeCollection c : g.getAttributeCollections())
						for (AttributeDescription d : c.getAttributeDescriptions())
							if (!d.isHidden() && !d.isHideDisplay() && d.isDefault())
								model.addElement(new AttributeDescWrapper(d,
										p.getDisplayName() + " > " +
										g.getDisplayName() + " > " +
										c.getDisplayName() + " > " +
										d.getDisplayName()));
		}

		updateButtons();
	}

	public void addAttributeListChangeListener(AttributeListChangeListener listener) {
		attributeListChangeListeners.add(listener);
	}

	public void removeAttributeListChangeListener(AttributeListChangeListener listener) {
		attributeListChangeListeners.remove(listener);
	}

	//FIXME
	@Deprecated
	public void setAddBtnEnabled(boolean enabled) {
		addBtn.setEnabled(enabled);
	}

	public void updateButtons() {
		addBtn.setEnabled(attrPages != null);

		int len = attrList.getModel().getSize();
		int selIndex = attrList.getSelectedIndex();
		removeBtn.setEnabled(len > 0);
		upBtn.setEnabled(selIndex > 0);
		downBtn.setEnabled(len > 1 && selIndex < len - 1);
		loadBtn.setEnabled(false);
		saveBtn.setEnabled(false);
	}

	public int getAttributeListSize() {
		return attrList.getModel().getSize();
	}

	public List<AttributeDescription> getAttributeList() {
		DefaultListModel model = (DefaultListModel) attrList.getModel();
		Enumeration<?> e = model.elements();
		List<AttributeDescription> list = new ArrayList<AttributeDescription>(model.getSize());
		while (e.hasMoreElements())
			list.add(((AttributeDescWrapper) e.nextElement()).getAttribute());
		return list;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        attrList = new javax.swing.JList();
        addBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        upBtn = new javax.swing.JButton();
        downBtn = new javax.swing.JButton();
        loadBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();

        jScrollPane1.setViewportView(attrList);

        addBtn.setText("Add...");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        removeBtn.setText("Remove");
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        upBtn.setText("Up");
        upBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upBtnActionPerformed(evt);
            }
        });

        downBtn.setText("Down");
        downBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downBtnActionPerformed(evt);
            }
        });

        loadBtn.setText("Load...");
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });

        saveBtn.setText("Save...");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(saveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(upBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(downBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(upBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveBtn)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
		BiomartAttributeDialog dlg =
				new BiomartAttributeDialog(AppFrame.instance(), attrPages);

		dlg.open();

		if (!dlg.isCancelled()) {
			List<AttributeDescription> attributes = dlg.getSelectedAttributes();
			List<String> names = dlg.getSelectedAttributeNames();

			if (attributes != null && attributes.size() > 0) {
				DefaultListModel model = (DefaultListModel) attrList.getModel();
				for (int i = 0; i < attributes.size(); i++) {
					model.addElement(new AttributeDescWrapper(
							attributes.get(i), names.get(i)));
				}
			}
		}
	}//GEN-LAST:event_addBtnActionPerformed

	private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
		DefaultListModel model = (DefaultListModel) attrList.getModel();
		Object[] objects = attrList.getSelectedValues();
		for (Object o : objects)
			model.removeElement(o);
	}//GEN-LAST:event_removeBtnActionPerformed

	public void removeAllListAttributes(){

		if (attrList.getModel() == null) return;

		DefaultListModel model = (DefaultListModel) attrList.getModel();

		if (model != null) model.removeAllElements();
	

	}
	private void upBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upBtnActionPerformed
		DefaultListModel model = (DefaultListModel) attrList.getModel();
		int[] indices = attrList.getSelectedIndices();
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < indices.length; i++)
			pos = pos > indices[i] ? indices[i] : pos;
		pos--;
		if (pos >= 0 && pos < model.getSize()) {
			Object[] objects = attrList.getSelectedValues();
			int i = 0;
			for (Object o : objects) {
				model.removeElement(o);
				indices[i++] = pos;
				model.insertElementAt(o, pos++);
			}
			attrList.setSelectedIndices(indices);
		}

	}//GEN-LAST:event_upBtnActionPerformed

	private void downBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downBtnActionPerformed
		DefaultListModel model = (DefaultListModel) attrList.getModel();
		int[] indices = attrList.getSelectedIndices();
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < indices.length; i++)
			pos = pos > indices[i] ? indices[i] : pos;
		pos++;
		if (pos >= 0 && (pos + indices.length <= model.getSize())) {
			Object[] objects = attrList.getSelectedValues();
			for (Object o : objects)
				model.removeElement(o);

			int i = 0;
			for (int j = objects.length - 1; j >= 0; j--) {
				Object o = objects[j];
				model.insertElementAt(o, pos);
				indices[i++] = pos + j;
				
			}
			attrList.setSelectedIndices(indices);
		}
	}//GEN-LAST:event_downBtnActionPerformed

	private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_loadBtnActionPerformed

	private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_saveBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JList attrList;
    private javax.swing.JButton downBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadBtn;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton upBtn;
    // End of variables declaration//GEN-END:variables

}
