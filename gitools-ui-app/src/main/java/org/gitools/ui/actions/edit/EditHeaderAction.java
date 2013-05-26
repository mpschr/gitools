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
package org.gitools.ui.actions.edit;

import org.gitools.core.heatmap.Heatmap;
import org.gitools.core.heatmap.HeatmapDimension;
import org.gitools.core.matrix.model.IMatrixView;
import org.gitools.ui.IconNames;
import org.gitools.ui.actions.ActionUtils;
import org.gitools.ui.heatmap.panel.settings.headers.HeadersEditPanel;
import org.gitools.ui.platform.actions.BaseAction;
import org.gitools.ui.utils.HeaderEnum;

import java.awt.event.ActionEvent;

public class EditHeaderAction extends BaseAction {

    private final HeaderEnum.Dimension dim;

    public EditHeaderAction(HeaderEnum.Dimension dim) {
        super("");
        this.dim = dim;

        setSmallIconFromResource(IconNames.edit16);

        switch (dim) {
            case COLUMN:
                setName("Edit column headers");
                setDesc("Edit column headers");
                break;

            case ROW:
                setName("Edit row headers");
                setDesc("Edit row headers");
                break;
        }
    }


    @Override
    public boolean isEnabledByModel(Object model) {
        return model instanceof Heatmap || model instanceof IMatrixView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Heatmap heatmap = ActionUtils.getHeatmap();
        HeatmapDimension heatmapDimension = (dim == HeaderEnum.Dimension.COLUMN) ? heatmap.getColumns() : heatmap.getRows();


        HeadersEditPanel dialog = new HeadersEditPanel(heatmap, heatmapDimension);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
    }

}
