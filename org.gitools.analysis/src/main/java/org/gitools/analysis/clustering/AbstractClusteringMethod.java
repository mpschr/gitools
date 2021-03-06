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
package org.gitools.analysis.clustering;

import com.jgoodies.binding.beans.Model;
import org.gitools.api.analysis.Clusters;
import org.gitools.api.analysis.IProgressMonitor;

public abstract class AbstractClusteringMethod extends Model implements ClusteringMethod {

    private String name;
    private String userGivenName;
    private boolean createBookmark;

    protected AbstractClusteringMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Clusters cluster(ClusteringData data, IProgressMonitor monitor) throws ClusteringException;

    @Override
    public String toString() {
        return name;
    }

    public void setUserGivenName(String userGivenName) {
        this.userGivenName = userGivenName;
    }

    public String getUserGivenName() {
        if (userGivenName == null) {
            return "";
        } else {
            return userGivenName;
        }
    }
}
