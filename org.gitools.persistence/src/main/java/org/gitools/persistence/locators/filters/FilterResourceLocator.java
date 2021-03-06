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
package org.gitools.persistence.locators.filters;

import org.gitools.api.PersistenceException;
import org.gitools.api.analysis.IProgressMonitor;
import org.gitools.api.resource.IResourceFilter;
import org.gitools.api.resource.IResourceLocator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public abstract class FilterResourceLocator implements IResourceLocator {

    private String name;
    private String baseName;
    private final String extension;
    private final IResourceLocator resourceLocator;

    protected FilterResourceLocator(IResourceFilter filter, IResourceLocator resourceLocator) {
        this(filter.removeExtension(resourceLocator.getName()), filter.removeExtension(resourceLocator.getExtension()), resourceLocator);
    }

    protected FilterResourceLocator(String name, String extension, IResourceLocator resourceLocator) {
        this.name = name;
        this.extension = extension;
        this.resourceLocator = resourceLocator;
        setBaseName(name, extension);
    }

    @Override
    public URL getURL() {
        return resourceLocator.getURL();
    }

    @Override
    public File getReadFile() {
        return resourceLocator.getReadFile();
    }

    @Override
    public File getWriteFile() {
        return resourceLocator.getWriteFile();
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    protected void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    protected void setBaseName(String name, String extension) {
        int startExtension = name.lastIndexOf(extension);
        this.baseName = (startExtension == 0 ? name : name.substring(0, startExtension - 1));
    }

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public boolean isWritable() {
        return resourceLocator.isWritable();
    }

    @Override
    public long getContentLength() {
        return getParentLocator().getContentLength();
    }

    @Override
    public IResourceLocator getReferenceLocator(String referenceName) throws PersistenceException {
        return getParentLocator().getReferenceLocator(referenceName);
    }

    @Override
    public InputStream openInputStream(IProgressMonitor progressMonitor) throws IOException {
        return getParentLocator().openInputStream(progressMonitor);
    }

    @Override
    public OutputStream openOutputStream(IProgressMonitor progressMonitor) throws IOException {
        return getParentLocator().openOutputStream(progressMonitor);
    }

    @Override
    public IResourceLocator getParentLocator() {
        return resourceLocator;
    }

    @Override
    public void close(IProgressMonitor monitor) {
        resourceLocator.close(monitor);
    }
}
