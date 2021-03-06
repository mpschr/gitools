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
package org.gitools.api.matrix;

import java.util.Set;

public interface IMatrixDimension extends Iterable<String> {

    /**
     * Gets the dimension identifier.
     *
     * @return the id
     */
    MatrixDimensionKey getId();

    /**
     * Total number of items in this dimension
     *
     * @return the dimension count
     */
    int size();

    /**
     * Gets the identifier label at the given position
     *
     * @param index the index
     * @return the label
     */
    String getLabel(int index);

    /**
     * Gets the index position of this identifier
     *
     * @param label the label
     * @return the index
     */
    int indexOf(String label);

    boolean isEmpty();

    boolean contains(String identifier);

    /**
     * Returns a view of the current dimension with only the given identifiers.
     * The identifiers can be in a different order depending on the implementation.
     *
     * @param identifiers
     * @return
     */
    IMatrixDimension subset(Set<String> identifiers);

    public IMatrixDimension from(String fromIdentifier);

}
