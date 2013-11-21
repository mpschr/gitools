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

/**
 * Generic data interface for clustering methods
 */
public interface ClusteringData {

    /**
     * Return the number of elements in the data
     */
    int getSize();

    /**
     * Returns the label associated with the instance at <index>
     */
    String getLabel(int index);

    Iterable<String> getLabels();

    /**
     * Returns an instance of the data
     */
    ClusteringDataInstance getInstance(int index);

    ClusteringDataInstance getInstance(String label);
}
