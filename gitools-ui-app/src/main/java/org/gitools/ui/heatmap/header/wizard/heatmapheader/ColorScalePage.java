/*
 *  Copyright 2011 Universitat Pompeu Fabra.
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

package org.gitools.ui.heatmap.header.wizard.heatmapheader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitools.heatmap.Heatmap;
import org.gitools.heatmap.header.HeatmapColoredLabelsHeader;
import org.gitools.heatmap.header.HeatmapDataHeatmapHeader;
import org.gitools.matrix.model.element.IElementAdapter;
import org.gitools.model.decorator.ElementDecorator;
import org.gitools.model.decorator.ElementDecoratorDescriptor;
import org.gitools.model.decorator.ElementDecoratorFactory;
import org.gitools.ui.panels.decorator.ElementDecoratorPanelFactory;
import org.gitools.ui.platform.wizard.AbstractWizardPage;

import javax.swing.*;

public class ColorScalePage extends AbstractWizardPage {

	private HeatmapDataHeatmapHeader header;

    private Heatmap heatmap;
    
    private Map<ElementDecoratorDescriptor,ElementDecorator> decoratorCache;

    public ColorScalePage(HeatmapDataHeatmapHeader header) {
		super();

        this.header = header;

        initComponents();

        decoratorCache = new HashMap<ElementDecoratorDescriptor, ElementDecorator>();

        final List<ElementDecoratorDescriptor> descList =
                ElementDecoratorFactory.getDescriptors();
        final ElementDecoratorDescriptor[] descriptors =
                new ElementDecoratorDescriptor[descList.size()];
        descList.toArray(descriptors);

        cellDecoratorCb.setModel(new DefaultComboBoxModel(descriptors));
        cellDecoratorCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDecoratorPanel((ElementDecoratorDescriptor) cellDecoratorCb.getSelectedItem());
            }
        });

        /*if (header.getHeaderHeatmap() != null) {
            this.heatmap = header.getHeaderHeatmap();
            ElementDecoratorDescriptor d =
                    ElementDecoratorFactory.getDescriptor(heatmap.getActiveCellDecorator().getClass());
            decoratorCache.put(d,heatmap.getActiveCellDecorator());

        } */

        setTitle("Select the color scale");
		setComplete(true);
    }
    
    public void setHeatmap(Heatmap heatmap) {
        this.heatmap = heatmap;
        ElementDecoratorDescriptor d =
                ElementDecoratorFactory.getDescriptor(heatmap.getActiveCellDecorator().getClass());
        changeDecoratorPanel(d);
        cellDecoratorCb.setSelectedItem(d);
    }


    private void changeDecoratorPanel(ElementDecoratorDescriptor descriptor) {

        ElementDecorator[] decorators = new ElementDecorator[1];
        decorators[0] = decoratorCache.get(descriptor);
        heatmap.setCellDecorators(decorators);

        createNewDecoratorPanel(descriptor);
    }

    private void createNewDecoratorPanel(ElementDecoratorDescriptor descriptor) {
        final JPanel confPanel = new JPanel();
        confPanel.setLayout(new BorderLayout());

        Class<? extends ElementDecorator> decoratorClass = descriptor.getDecoratorClass();

        JComponent c = ElementDecoratorPanelFactory.create(decoratorClass, heatmap);
        confPanel.add(c, BorderLayout.CENTER);

        decoPanel.removeAll();
        decoPanel.setLayout(new BorderLayout());

        decoPanel.add(c, BorderLayout.CENTER);
    }

    @Override
	public void updateControls() {
        System.out.println("update controls");
        if (this.heatmap == null && header.getHeaderHeatmap() != null)
        {
            this.heatmap = header.getHeaderHeatmap();
            
            ElementDecoratorDescriptor descriptor;
            for (int i = 0; i < cellDecoratorCb.getItemCount(); i++) {
                descriptor = (ElementDecoratorDescriptor) cellDecoratorCb.getItemAt(i);
                ElementDecorator decorator  = ElementDecoratorFactory.create(descriptor, heatmap.getMatrixView().getCellAdapter());
                decoratorCache.put(descriptor,decorator);
            }
            ElementDecoratorDescriptor d =
                    ElementDecoratorFactory.getDescriptor(heatmap.getActiveCellDecorator().getClass());
            decoratorCache.put(d,heatmap.getActiveCellDecorator());

            createNewDecoratorPanel(d);
            cellDecoratorCb.setSelectedItem(d);
            //changeDecoratorPanel(d);
        }
		super.updateControls();
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

        colorGroup = new javax.swing.ButtonGroup();
        cellDecoratorCb = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        decoPanel = new javax.swing.JPanel();

        cellDecoratorCb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Color Scale");

        javax.swing.GroupLayout decoPanelLayout = new javax.swing.GroupLayout(decoPanel);
        decoPanel.setLayout(decoPanelLayout);
        decoPanelLayout.setHorizontalGroup(
            decoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );
        decoPanelLayout.setVerticalGroup(
                decoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 270, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cellDecoratorCb, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(248, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cellDecoratorCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(decoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cellDecoratorCb;
    private javax.swing.ButtonGroup colorGroup;
    private javax.swing.JPanel decoPanel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables


}
