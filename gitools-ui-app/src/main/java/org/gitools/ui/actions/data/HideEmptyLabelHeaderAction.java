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
package org.gitools.ui.actions.data;

import com.google.common.base.Predicate;
import org.gitools.ui.heatmap.drawer.HeatmapPosition;
import org.gitools.core.heatmap.header.HeatmapHeader;
import org.gitools.ui.actions.HeatmapAction;
import org.gitools.ui.heatmap.popupmenus.dynamicactions.IHeatmapHeaderAction;

import java.awt.event.ActionEvent;

import static com.google.common.base.Strings.isNullOrEmpty;


public class HideEmptyLabelHeaderAction extends HeatmapAction implements IHeatmapHeaderAction {

    private HeatmapHeader header;

    public HideEmptyLabelHeaderAction() {
        super("Hide empty values in header");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        header.getHeatmapDimension().show(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return !isNullOrEmpty(header.getIdentifierTransform().apply(input));
            }
        });

    }

    @Override
    public void onConfigure(HeatmapHeader header, HeatmapPosition position) {
        setName("Hide empty values in '" + header.getTitle() + "'");
        this.header = header;
    }
}
