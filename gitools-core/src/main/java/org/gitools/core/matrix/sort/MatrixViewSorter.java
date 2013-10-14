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
package org.gitools.core.matrix.sort;

import org.apache.commons.lang.ArrayUtils;
import org.gitools.core.heatmap.Heatmap;
import org.gitools.core.heatmap.HeatmapDimension;
import org.gitools.core.label.AnnotationsPatternProvider;
import org.gitools.core.label.LabelProvider;
import org.gitools.core.label.MatrixDimensionLabelProvider;
import org.gitools.core.matrix.filter.MatrixViewAnnotationsFilter;
import org.gitools.core.matrix.model.IAnnotations;
import org.gitools.core.matrix.model.IMatrixView;
import org.gitools.core.matrix.model.IMatrixViewDimension;
import org.gitools.core.matrix.sort.ValueSortCriteria.SortDirection;
import org.gitools.core.matrix.sort.mutualexclusion.MutualExclusionComparator;
import org.gitools.utils.aggregation.SumAbsAggregator;
import org.gitools.utils.progressmonitor.IProgressMonitor;
import org.gitools.utils.progressmonitor.NullProgressMonitor;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CancellationException;

public abstract class MatrixViewSorter {


    public static void sortByMutualExclusion(@NotNull final Heatmap heatmap, String pattern, IAnnotations am, @NotNull List<String> values, boolean regExChecked, boolean applyToRows, boolean applyToColumns, IProgressMonitor monitor, boolean showProgress) {
        if (applyToRows) {
            sortRowsByMutualExclusion(heatmap, pattern, am, values, regExChecked, monitor, showProgress);
        }

        if (applyToColumns) {
            sortColumnsByMutualExclusion(heatmap, pattern, am, values, regExChecked, monitor, showProgress);
        }
    }

    private static void sortRowsByMutualExclusion(@NotNull final Heatmap heatmap, String pattern, IAnnotations am, @NotNull List<String> values, boolean regExChecked, IProgressMonitor monitor, boolean showProgress) {

        int[] selColumns = heatmap.getColumns().getSelected();

        LabelProvider labelProvider = new AnnotationsPatternProvider(heatmap.getRows(), pattern);
        int[] visibleRows = heatmap.getRows().getVisibleIndices();
        int[] selRows = MatrixViewAnnotationsFilter.filterLabels(labelProvider, values, regExChecked, visibleRows);


        int numRows = selRows.length;
        final Integer[] indices = new Integer[numRows];
        for (int i = 0; i < selRows.length; i++)
            indices[i] = ArrayUtils.indexOf(visibleRows, selRows[i]);

        if (selColumns == null || selColumns.length == 0) {
            selColumns = new int[heatmap.getColumns().size()];
            for (int i = 0; i < selColumns.length; i++)
                selColumns[i] = i;
        }

        final int[] selectedColumns = selColumns;

        Comparator<Integer> comparator = new MutualExclusionComparator(heatmap, numRows, selectedColumns, monitor);

        if (monitor.isCancelled()) {
            return;
        }

        monitor.begin("Sorting...", 0);
        Arrays.sort(indices, comparator);

        //put the chosen indices at the top of the matrix
        int[] rowIndices = new int[indices.length];
        for (int i = 0; i < indices.length; i++) {
            rowIndices[i] = visibleRows[indices[i]];
        }

        for (int i = 0; i < rowIndices.length; i++) {
            int rowPos = ArrayUtils.indexOf(visibleRows, rowIndices[i]);
            int replacingRow = rowIndices[i];
            for (int j = i; j <= rowPos; j++) {
                int rowToReplace = visibleRows[j];
                visibleRows[j] = replacingRow;
                replacingRow = rowToReplace;
            }
        }


        final int[] sortedVisibleRows = visibleRows;
        heatmap.getRows().setVisibleIndices(sortedVisibleRows);

        ValueSortCriteria[] criteriaArray = new ValueSortCriteria[1];
        int index = heatmap.getLayers().getTopLayerIndex();
        criteriaArray[0] = new ValueSortCriteria(index, SumAbsAggregator.INSTANCE, SortDirection.DESCENDING);

        monitor.begin("Sorting rows...", numRows);

        HeatmapDimension sortDimension = heatmap.getColumns();
        PropertyChangeListener[] listeners = sortDimension.getPropertyChangeListeners();

        if (!showProgress) {
            for (PropertyChangeListener listener : listeners) {
                sortDimension.removePropertyChangeListener(listener);
            }
        }

        IProgressMonitor nullProgress = new NullProgressMonitor();
        for (int i = numRows - 1; i >= 0; i--) {
            monitor.worked(1);
            if (monitor.isCancelled()) {
                break;
            }

            int[] exclusiveRow = new int[1];
            exclusiveRow[0] = i;

            sortByValue(heatmap, sortDimension, null, heatmap.getRows(), exclusiveRow, criteriaArray, nullProgress);
        }

        if (!showProgress) {
            for (PropertyChangeListener listener : listeners) {
                sortDimension.addPropertyChangeListener(listener);
            }
        }

        // Force to fire the events
        sortDimension.setVisibleIndices(sortDimension.getVisibleIndices());

    }

    private static void sortColumnsByMutualExclusion(IMatrixView matrixView, String pattern, IAnnotations am, List<String> values, boolean regExChecked, IProgressMonitor monitor, boolean showProgress) {
        throw new UnsupportedOperationException("Mutually exclusive sorting for columns is not yet implemented");
    }


    public static void sortByValue(@NotNull IMatrixView matrixView, ValueSortCriteria[] criteria, boolean applyToRows, boolean applyToColumns, IProgressMonitor progressMonitor) {

        IMatrixViewDimension rows = matrixView.getRows();
        IMatrixViewDimension columns = matrixView.getColumns();

        if (progressMonitor == null) {
            progressMonitor = new NullProgressMonitor();
        }

        if (applyToRows) {
            sortByValue(matrixView, rows, rows.getSelected(), columns, columns.getSelected(), criteria, progressMonitor);
        }

        if (applyToColumns) {
            sortByValue(matrixView, columns, columns.getSelected(), rows, rows.getSelected(), criteria, progressMonitor);
        }
    }

    private static void sortByValue(final IMatrixView matrixView,
                                    final IMatrixViewDimension sortDimension, int[] sortSelection,
                                    final IMatrixViewDimension aggregationDimension, int[] aggregationSelection,
                                    final ValueSortCriteria[] criteriaArray,
                                    final IProgressMonitor progressMonitor
    ) {


        if (criteriaArray == null || criteriaArray.length == 0) {
            return;
        }

        if (aggregationSelection == null || aggregationSelection.length == 0) {
            aggregationSelection = new int[aggregationDimension.size()];
            for (int i = 0; i < aggregationSelection.length; i++)
                aggregationSelection[i] = i;
        }

        if (sortSelection == null || sortSelection.length == 0) {
            sortSelection = new int[sortDimension.size()];
            for (int i = 0; i < sortSelection.length; i++)
                sortSelection[i] = i;
        }

        final int[] aggregatingIndices = aggregationSelection;
        final Integer[] sortingIndices = ArrayUtils.toObject(sortSelection);

        Comparator<Integer> comparator = new SortByValueComparator(sortDimension, criteriaArray, aggregationDimension, aggregatingIndices, matrixView, progressMonitor, sortSelection.length);

        try {
            Arrays.sort(sortingIndices, comparator);
        } catch (CancellationException e) {
            // Cancelled by the user
            return;
        }

        final int[] visibleIndices = sortDimension.getVisibleIndices();
        final int[] sortedVisibleIndices = new int[visibleIndices.length];
        for (int i = 0; i < visibleIndices.length; i++)
            sortedVisibleIndices[i] = visibleIndices[i];

        for (int i = 0; i < sortSelection.length; i++)
            sortedVisibleIndices[sortSelection[i]] = visibleIndices[sortingIndices[i]];

        sortDimension.setVisibleIndices(sortedVisibleIndices);
    }

    public static void sortByLabel(@NotNull Heatmap heatmap, boolean sortRows, @NotNull String rowsPattern, SortDirection rowsDirection, boolean rowsNumeric, boolean sortCols, @NotNull String colsPattern, SortDirection colsDirection, boolean colsNumeric) {

        if (sortRows) {
            LabelProvider labelProvider = new MatrixDimensionLabelProvider(heatmap.getRows());
            if (!rowsPattern.equalsIgnoreCase("${id}")) {
                labelProvider = new AnnotationsPatternProvider(heatmap.getRows(), rowsPattern);
            }

            heatmap.getRows().setVisibleIndices(sortLabels(labelProvider, rowsDirection, heatmap.getRows().getVisibleIndices(), rowsNumeric));
        }

        if (sortCols) {
            LabelProvider labelProvider = new MatrixDimensionLabelProvider(heatmap.getColumns());
            if (!colsPattern.equalsIgnoreCase("${id}")) {
                labelProvider = new AnnotationsPatternProvider(heatmap.getColumns(), colsPattern);
            }

            heatmap.getColumns().setVisibleIndices(sortLabels(labelProvider, colsDirection, heatmap.getColumns().getVisibleIndices(), colsNumeric));
        }
    }

    @NotNull
    public static int[] sortLabels(@NotNull final LabelProvider labelProvider, SortDirection direction, int[] visibleIndices, boolean numeric) {

        int count = labelProvider.getCount();
        Integer[] indices = new Integer[count];
        for (int i = 0; i < count; i++)
            indices[i] = i;

        final int dirSign = direction == SortDirection.ASCENDING ? 1 : -1;

        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer idx1, Integer idx2) {
                String label1 = labelProvider.getLabel(idx1);
                String label2 = labelProvider.getLabel(idx2);

                int res;
                if (label1 == null && label2 == null) {
                    res = 0;
                } else if (label1 == null) {
                    res = dirSign;
                } else if (label2 == null) {
                    res = -dirSign;
                } else {
                    res = label1.compareTo(label2);
                }

                return res * dirSign;
            }
        };

        Comparator<Integer> numericComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer idx1, Integer idx2) {
                Double v1;
                Double v2;

                try {
                    v1 = Double.parseDouble(labelProvider.getLabel(idx1));
                } catch (NumberFormatException e) {
                    v1 = Double.NaN;
                }

                try {
                    v2 = Double.parseDouble(labelProvider.getLabel(idx2));
                } catch (NumberFormatException e) {
                    v2 = Double.NaN;
                }

                if (v1.isNaN() || v2.isNaN()) {
                    return v1.compareTo(v2);
                } else {
                    return v1.compareTo(v2) * dirSign;
                }
            }
        };

        if (numeric) {
            Arrays.sort(indices, numericComparator);
        } else {
            Arrays.sort(indices, comparator);
        }

        int[] vIndices = new int[count];
        for (int i = 0; i < count; i++)
            vIndices[i] = visibleIndices[indices[i]];
        return vIndices;
    }

}