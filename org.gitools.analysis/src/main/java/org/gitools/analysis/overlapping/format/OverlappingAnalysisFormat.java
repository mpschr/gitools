/*
 * #%L
 * gitools-core
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
package org.gitools.analysis.overlapping.format;

import org.gitools.analysis.overlapping.OverlappingAnalysis;
import org.gitools.api.PersistenceException;
import org.gitools.api.analysis.IProgressMonitor;
import org.gitools.api.persistence.FileFormat;
import org.gitools.api.resource.IResourceLocator;
import org.gitools.resource.AbstractXmlFormat;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OverlappingAnalysisFormat extends AbstractXmlFormat<OverlappingAnalysis> {

    public static final String EXTENSION = "overlapping";
    public static final FileFormat FILE_FORMAT = new FileFormat("Overlapping analysis", EXTENSION, true, false, true);

    public OverlappingAnalysisFormat() {
        super(EXTENSION, OverlappingAnalysis.class);
    }

    @Override
    protected void writeResource(IResourceLocator resourceLocator, OverlappingAnalysis resource, IProgressMonitor monitor) throws PersistenceException {

        //TODO Find a better solution for this error
        resource.getSourceData().get();
        resource.getCellResults().get();

        if (resource.getFilteredData() != null) {
            resource.getFilteredData().get();
        }

        super.writeResource(resourceLocator, resource, monitor);
    }
}
