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
package org.gitools.ui.heatmap.panel.properties;

import org.gitools.heatmap.Heatmap;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class HeatmapPropertiesAbstractPanel extends javax.swing.JPanel
{

    private Heatmap heatmap;
    protected boolean updatingControls = false;

    public HeatmapPropertiesAbstractPanel(Heatmap heatmap)
    {
        super();

        this.heatmap = heatmap;

        this.heatmap.addPropertyChangeListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                heatmapPropertyChange(evt);
            }
        });
    }

    public Heatmap getHeatmap()
    {
        return heatmap;
    }

    protected void heatmapPropertyChange(PropertyChangeEvent evt)
    {
    }
}
