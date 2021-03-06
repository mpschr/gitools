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
package org.gitools.analysis.clustering.hierarchical;

import org.gitools.analysis.clustering.AbstractClusteringMethod;
import org.gitools.analysis.clustering.ClusteringData;
import org.gitools.analysis.clustering.ClusteringException;
import org.gitools.analysis.clustering.MatrixClusteringData;
import org.gitools.analysis.clustering.distance.DistanceMeasure;
import org.gitools.analysis.clustering.hierarchical.strategy.LinkageStrategy;
import org.gitools.api.analysis.Clusters;
import org.gitools.api.analysis.IAggregator;
import org.gitools.api.analysis.IProgressMonitor;


public class HierarchicalMethod extends AbstractClusteringMethod {

    private LinkageStrategy linkageStrategy;
    private DistanceMeasure distanceMeasure;
    private IAggregator aggregator;

    public HierarchicalMethod() {
        this(null, null, null);
    }

    public HierarchicalMethod(LinkageStrategy linkageStrategy, DistanceMeasure distanceMeasure, IAggregator aggregator) {
        super("Hierarchical");

        this.linkageStrategy = linkageStrategy;
        this.distanceMeasure = distanceMeasure;
        this.aggregator = aggregator;
    }

    public LinkageStrategy getLinkageStrategy() {
        return linkageStrategy;
    }

    public void setLinkageStrategy(LinkageStrategy linkageStrategy) {
        this.linkageStrategy = linkageStrategy;
    }

    public DistanceMeasure getDistanceMeasure() {
        return distanceMeasure;
    }

    public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
        this.distanceMeasure = distanceMeasure;
    }

    public IAggregator getAggregator() {
        return aggregator;
    }

    public void setAggregator(IAggregator aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public Clusters cluster(ClusteringData clusterData, IProgressMonitor monitor) throws ClusteringException {

        if (!(clusterData instanceof MatrixClusteringData)) {
            return null;
        }

        MatrixClusteringData data = (MatrixClusteringData) clusterData;
        HierarchicalClusterer clusterer = new HierarchicalClusterer(linkageStrategy, distanceMeasure, aggregator);
        return clusterer.cluster(data.getMatrix(), data.getLayer(), data.getClusteringDimension(), data.getAggregationDimension(), monitor);

    }


}
