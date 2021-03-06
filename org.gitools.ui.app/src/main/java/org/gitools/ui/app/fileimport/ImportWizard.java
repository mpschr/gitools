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
package org.gitools.ui.app.fileimport;

import org.gitools.api.analysis.IProgressMonitor;
import org.gitools.api.resource.IResource;
import org.gitools.api.resource.IResourceLocator;
import org.gitools.ui.core.commands.Command;
import org.gitools.ui.core.utils.FileFormatFilter;
import org.gitools.ui.platform.progress.JobRunnable;
import org.gitools.ui.platform.wizard.IWizard;

public interface ImportWizard extends IWizard, JobRunnable {

    public interface Callback {
        void afterLoad(IResource resource, IProgressMonitor monitor) throws Command.CommandException;
    }

    FileFormatFilter getFileFormatFilter();

    void setLocator(IResourceLocator locator);

    void setCallback(Callback callbackFunction);
}
