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
package org.gitools.ui.heatmap.header.wizard.textlabels;

import org.gitools.core.heatmap.HeatmapDimension;
import org.gitools.core.heatmap.header.HeatmapTextLabelsHeader;
import org.gitools.ui.heatmap.header.wizard.TextLabelsConfigPage;
import org.gitools.ui.platform.wizard.AbstractWizard;

public class TextLabelsHeaderWizard extends AbstractWizard {

    private final HeatmapDimension hdim;
    private final HeatmapTextLabelsHeader header;

    private TextLabelsSourcePage sourcePage;
    private TextLabelsConfigPage configPage;

    public TextLabelsHeaderWizard(HeatmapDimension hdim, HeatmapTextLabelsHeader header) {
        this.hdim = hdim;
        this.header = header;
    }

    @Override
    public void addPages() {
        sourcePage = new TextLabelsSourcePage(hdim, header);
        addPage(sourcePage);

        configPage = new TextLabelsConfigPage(header);
        addPage(configPage);
    }

}
