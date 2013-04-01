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
package org.gitools.heatmap.header;

import org.gitools.clustering.HierarchicalClusteringResults;
import org.gitools.heatmap.HeatmapDim;

public class HeatmapHierarchicalColoredLabelsHeader extends HeatmapColoredLabelsHeader
{

    public static final String CLUSTERING_RESULTS_CHANGED = "clusteringResults";
    public static final String TREE_LEVEL_CHANGED = "treeLevel";

    private HierarchicalClusteringResults clusteringResults;

    public HeatmapHierarchicalColoredLabelsHeader(HeatmapDim hdim)
    {
        super(hdim);
    }

    public HierarchicalClusteringResults getClusteringResults()
    {
        return clusteringResults;
    }

    public void setClusteringResults(HierarchicalClusteringResults results)
    {
        HierarchicalClusteringResults old = this.clusteringResults;
        this.clusteringResults = results;
        firePropertyChange(CLUSTERING_RESULTS_CHANGED, old, results);
    }

    public int getTreeLevel()
    {
        return clusteringResults.getLevel();
    }

    public void setTreeLevel(int level)
    {
        int old = clusteringResults.getLevel();
        clusteringResults.setLevel(level);
        updateFromClusterResults(clusteringResults);
        firePropertyChange(TREE_LEVEL_CHANGED, old, level);
    }

}
