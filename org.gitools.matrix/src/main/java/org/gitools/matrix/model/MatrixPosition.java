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
package org.gitools.matrix.model;

import com.google.common.base.Joiner;
import org.gitools.api.matrix.*;
import org.gitools.matrix.model.iterable.IdentifierSourceIterable;
import org.gitools.matrix.model.iterable.ValueSourceIterable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MatrixPosition implements IMatrixPosition {

    private IMatrix matrix;
    private MatrixPosition parentPosition;

    private MatrixDimensionKey[] dimensions;
    private String[] identifiers;
    private Map<MatrixDimensionKey, Integer> positions;

    public MatrixPosition(MatrixPosition position) {

        this.parentPosition = position;
        this.identifiers = Arrays.copyOf(position.identifiers, position.identifiers.length);
        //0this.identifiers = position.identifiers;

        this.matrix = position.matrix;
        this.dimensions = position.dimensions;
        this.positions = position.positions;



    }

    public MatrixPosition(IMatrix matrix) {
        super();

        this.matrix = matrix;
        this.dimensions = matrix.getDimensionKeys();

        this.identifiers = new String[dimensions.length];
        for (int i = 0; i < dimensions.length; i++) {
            this.identifiers[i] = matrix.getDimension(dimensions[i]).iterator().next();
        }

        this.positions = new HashMap<>(dimensions.length);

        for (int i = 0; i < dimensions.length; i++) {
            this.positions.put(dimensions[i], i);
        }
    }

    @Override
    public IMatrix getMatrix() {
        return matrix;
    }

    @Override
    public String get(IMatrixDimension dimension) {
        return get(dimension.getId());
    }

    @Override
    public String get(MatrixDimensionKey dimension) {
        return identifiers[positions.get(dimension)];
    }

    @Override
    public IMatrixPosition set(IMatrixDimension dimension, String identifier) {
        return set(dimension.getId(), identifier);
    }

    @Override
    public MatrixPosition set(MatrixDimensionKey dimension, String identifier) {

        if (identifier != null) {
            identifiers[positions.get(dimension)] = identifier;

            if (parentPosition != null) {
                parentPosition.identifiers[positions.get(dimension)] = identifier;
            }
        }

        return this;
    }

    @Override
    public String[] toVector() {
        return identifiers;
    }

    public MatrixPosition set(String... identifiers) {
        assert identifiers.length == dimensions.length : "This matrix position has " + dimensions.length + " and your identifier vector " + identifiers.length;
        this.identifiers = Arrays.copyOf(identifiers, identifiers.length);

        if (parentPosition != null) {
            parentPosition.identifiers = Arrays.copyOf(identifiers, identifiers.length);
        }

        return this;
    }

    @Override
    public IMatrixIterable<String> iterate(IMatrixDimension dimension) {
        return new IdentifierSourceIterable(this, dimension);
    }

    @Override
    public <T> IMatrixIterable<T> iterate(ILayerAdapter<T> layerAdapter, IMatrixDimension... dimension) {
        return new AdapterSourceIterable<>(this, layerAdapter, dimension);
    }

    @Override
    public <T> IMatrixIterable<T> iterate(IMatrixLayer<T> layer, IMatrixDimension... dimension) {
        return new ValueSourceIterable<>(this, layer, dimension);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatrixPosition that = (MatrixPosition) o;

        if (!Arrays.equals(identifiers, that.identifiers)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(identifiers);
    }

    @Override
    public String toString() {
        return " (" + Joiner.on(",").join(identifiers) + ")";
    }

}
