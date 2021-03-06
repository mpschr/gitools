/*
 * #%L
 * org.gitools.ui.app
 * %%
 * Copyright (C) 2013 - 2014 Universitat Pompeu Fabra - Biomedical Genomics group
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
package org.gitools.ui.app.commands;

import org.gitools.api.analysis.IProgressMonitor;
import org.gitools.api.matrix.MatrixDimensionKey;
import org.gitools.ui.core.Application;
import org.gitools.ui.core.commands.HeaderCommand;

public class SortByAnnotationsCommand extends HeaderCommand {

    public SortByAnnotationsCommand(String heatmap, MatrixDimensionKey side, String pattern, String sort) {
        super(heatmap, side, sort, pattern);
    }

    @Override
    public void execute(IProgressMonitor monitor) throws CommandException {

        super.execute(monitor);
        if (getExitStatus() > 0) {
            return;
        }

        applySort();

        Application.get().refresh();

        setExitStatus(0);
        return;
    }
}
