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
package org.gitools.plugins.mutex.sort;


import org.gitools.api.analysis.IProgressMonitor;
import org.gitools.api.matrix.*;
import org.gitools.matrix.sort.AggregationFunction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MutualExclusiveComparator implements Comparator<String> {

    private final Map<String, Double> aggregationCache;

    public MutualExclusiveComparator(IMatrix matrix, IMatrixLayer<Double> layer, IMatrixDimension sortDimension, IMatrixPredicate<String> validIdentifiers, AggregationFunction aggregationFunction, IProgressMonitor monitor) {

        aggregationCache = new HashMap<>(sortDimension.size());

        IMatrixPosition position = matrix.newPosition();
        Iterable<Double> aggregatedValues = position
                .iterate(sortDimension)
                .monitor(monitor, "Aggregating values")
                .filter(validIdentifiers)
                .transform(aggregationFunction);

        for (Double value : aggregatedValues) {
            aggregationCache.put(position.get(sortDimension), value);
        }
    }

    @Override
    public int compare(String idx1, String idx2) {

        Double value1 = aggregationCache.get(idx1);
        Double value2 = aggregationCache.get(idx2);

        int res;
        int factor = SortDirection.DESCENDING.getFactor();
        if (value1 == null && value2 == null) {
            res = 0;
        } else if (value1 == null) {
            res = factor;
        } else if (value2 == null) {
            res = -factor;
        } else {
            res = value1.compareTo(value2);
        }

        return res * factor;
    }

}
