/*
 *  Copyright 2010 Universitat Pompeu Fabra.
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

package org.gitools.persistence;

import edu.upf.bg.progressmonitor.IProgressMonitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractResourcePersistence<R> implements IResourcePersistence<R> {

    protected PersistenceManager manager;

    protected Properties properties;

    @Deprecated
    public PersistenceManager getPersistenceManager() {
        return manager != null ? manager : PersistenceManager.getDefault();
    }

    @Deprecated
    @Override
    public void setPersistenceManager(PersistenceManager manager) {
        this.manager = manager;
    }

    @Deprecated
    public Properties getProperties() {
        return properties;
    }

    @Deprecated
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Deprecated
    @Override
    public Map<String, Object> readMetadata(IResourceLocator resourceLocator, String[] keys, IProgressMonitor monitor) throws PersistenceException {
        return new HashMap<String, Object>();
    }

    @Override
    public R read(IResourceLocator resourceLocator, IProgressMonitor progressMonitor) throws PersistenceException {
        throw new UnsupportedOperationException("This resource is NOT readable");
    }

    @Override
    public void write(IResourceLocator file, R resource, IProgressMonitor progressMonitor) throws PersistenceException {
        throw new UnsupportedOperationException("This resource is NOT writable");
    }
}
