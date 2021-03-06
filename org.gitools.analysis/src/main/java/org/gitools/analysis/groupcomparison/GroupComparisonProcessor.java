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
package org.gitools.analysis.groupcomparison;

import org.gitools.analysis.AnalysisProcessor;
import org.gitools.analysis.groupcomparison.dimensiongroups.DimensionGroup;
import org.gitools.analysis.groupcomparison.format.math33Preview.CombinatoricsUtils;
import org.gitools.analysis.stats.mtc.MTCFactory;
import org.gitools.analysis.stats.test.EnrichmentTest;
import org.gitools.analysis.stats.test.MannWhitneyWilcoxonTest;
import org.gitools.analysis.stats.test.OneWayAnovaTest;
import org.gitools.analysis.stats.test.results.GroupComparisonResult;
import org.gitools.analysis.stats.test.results.OneWayAnovaResult;
import org.gitools.api.analysis.IProgressMonitor;
import org.gitools.api.matrix.*;
import org.gitools.api.resource.ResourceReference;
import org.gitools.heatmap.Heatmap;
import org.gitools.heatmap.HeatmapLayer;
import org.gitools.heatmap.decorator.impl.PValueDecorator;
import org.gitools.heatmap.decorator.impl.PValueLogSumDecorator;
import org.gitools.heatmap.header.ColoredLabel;
import org.gitools.heatmap.header.HeatmapColoredLabelsHeader;
import org.gitools.heatmap.header.HeatmapHeader;
import org.gitools.matrix.model.hashmatrix.HashMatrix;
import org.gitools.matrix.model.hashmatrix.HashMatrixDimension;
import org.gitools.matrix.model.matrix.AnnotationMatrix;
import org.gitools.matrix.model.matrix.element.LayerAdapter;
import org.gitools.matrix.model.matrix.element.MapLayerAdapter;
import org.gitools.utils.CloneUtils;
import org.gitools.utils.aggregation.SumAbsAggregator;
import org.gitools.utils.color.ColorGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static org.gitools.api.matrix.MatrixDimensionKey.COLUMNS;
import static org.gitools.api.matrix.MatrixDimensionKey.ROWS;

public class GroupComparisonProcessor implements AnalysisProcessor {

    private final GroupComparisonAnalysis analysis;

    public GroupComparisonProcessor(GroupComparisonAnalysis analysis) {
        this.analysis = analysis;
    }

    @Override
    public void run(IProgressMonitor monitor) {
        Date startTime = new Date();

        // Prepare input data matrix
        IMatrix dataMatrix = analysis.getData().get();
        IMatrixLayer<Double> layer = dataMatrix.getLayers().get(analysis.getLayerName());

        // Prepare dimensions to compare
        IMatrixDimension sourceRows = (analysis.isTransposeData() ? dataMatrix.getDimension(COLUMNS) : dataMatrix.getDimension(ROWS));
        IMatrixDimension sourceColumns = (analysis.isTransposeData() ? dataMatrix.getDimension(ROWS) : dataMatrix.getDimension(COLUMNS));

        EnrichmentTest test = analysis.getTest();
        Heatmap resultHeatmap;
        // Prepare group predicates
        NullConversion nullConversionFunction = new NullConversion(analysis.getNullConversion());

        if (test.getClass() == MannWhitneyWilcoxonTest.class) {
            resultHeatmap = mannWhitneyWilcoxon(monitor, dataMatrix, layer, sourceRows, sourceColumns, (MannWhitneyWilcoxonTest) test, nullConversionFunction);
        } else {
            resultHeatmap = oneWayAnova(monitor, dataMatrix, layer, sourceRows, sourceColumns, (OneWayAnovaTest) test, nullConversionFunction);
        }


        // Finish
        analysis.setStartTime(startTime);
        analysis.setElapsedTime(System.currentTimeMillis() - startTime.getTime());
        analysis.setResults(new ResourceReference<>("results", resultHeatmap));

    }


    private Heatmap oneWayAnova(IProgressMonitor monitor, IMatrix dataMatrix, IMatrixLayer<Double> layer, IMatrixDimension sourceRows, IMatrixDimension sourceColumns, OneWayAnovaTest test, NullConversion nullConversionFunction) {

        LayerAdapter<OneWayAnovaResult> adapter = new LayerAdapter<>(OneWayAnovaResult.class);
        DimensionGroup[] groups = analysis.getGroups().toArray(new DimensionGroup[analysis.getGroups().size()]);

        ArrayList<String> resultColnames = new ArrayList<>();
        resultColnames.add("oneWayAnova");

        HashMatrixDimension resultColumns = new HashMatrixDimension(COLUMNS, resultColnames);
        HashMatrixDimension resultsRows = new HashMatrixDimension(ROWS, sourceRows);

        Heatmap resultHeatmap = new Heatmap(
                new HashMatrix(
                        adapter.getMatrixLayers(),
                        resultsRows,
                        resultColumns
                )
        );

        // Run comparison
        dataMatrix.newPosition()
                .iterate(sourceRows)
                .monitor(monitor, "Running group comparison analysis")
                .transform(
                        new OneWayAnovaFunction(
                                test,
                                sourceColumns,
                                layer,
                                nullConversionFunction,
                                groups)
                )
                .store(resultHeatmap, new MapLayerAdapter<>(resultColumns, adapter));

        // Run multiple test correction
        IMatrixPosition position = resultHeatmap.newPosition();
        IMatrixFunction<Double, Double> mtcFunction = MTCFactory.createFunction(analysis.getMtc());

        for (String condition : position.iterate(resultColumns)) {
            // Two-tail p-Value
            position.iterate(adapter.getLayer(Double.class, "two-tail-p-value"), resultsRows)
                    .transform(mtcFunction)
                    .store(resultHeatmap, adapter.getLayer(Double.class, "corrected-two-tail-p-value"));
        }


        // Results formatting

        for (HeatmapLayer resultLayer : resultHeatmap.getLayers()) {
            if (resultLayer.getId().contains("p-value")) {
                resultLayer.setDecorator(new PValueDecorator());
            }
        }

        resultHeatmap.setTitle(analysis.getTitle() + " (results)");

        if (analysis.isCopyAnnotation() && dataMatrix instanceof Heatmap &&
                ((Heatmap) dataMatrix).getRows().getAnnotations() != null) {

            resultHeatmap.getRows().addAnnotations(
                    ((Heatmap) dataMatrix).getRows().getAnnotations()
            );

            for (HeatmapHeader header : ((Heatmap) dataMatrix).getRows().getHeaders()) {

                HeatmapHeader headerClone = null;
                try {
                    headerClone = CloneUtils.clone(header);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                resultHeatmap.getRows().addHeader(headerClone);
                headerClone.init(resultHeatmap.getRows());
            }
        }


        return resultHeatmap;
    }

    private Heatmap mannWhitneyWilcoxon(IProgressMonitor monitor, IMatrix dataMatrix, IMatrixLayer<Double> layer, IMatrixDimension sourceRows, IMatrixDimension sourceColumns, MannWhitneyWilcoxonTest test, NullConversion nullConversionFunction) {
        // Prepare results data matrix
        LayerAdapter<GroupComparisonResult> adapter = new LayerAdapter<>(GroupComparisonResult.class);

        DimensionGroup[] groups = analysis.getGroups().toArray(new DimensionGroup[analysis.getGroups().size()]);
        ArrayList<String> resultColnames = new ArrayList<>();
        AnnotationMatrix resultColumnAnnotations = new AnnotationMatrix();

        Iterator<int[]> combIterator = CombinatoricsUtils.combinationsIterator(groups.length, 2);
        while (combIterator.hasNext()) {
            int[] groupIndices = combIterator.next();

            DimensionGroup dimensionGroup1 = groups[groupIndices[0]];
            DimensionGroup dimensionGroup2 = groups[groupIndices[1]];

            String combName = dimensionGroup1.getName() + " VS " + dimensionGroup2.getName();
            resultColnames.add(combName);
            resultColumnAnnotations.setAnnotation(combName, "Group 1", dimensionGroup1.getName());
            resultColumnAnnotations.setAnnotation(combName, "Group 2", dimensionGroup2.getName());
        }

        HashMatrixDimension resultColumns = new HashMatrixDimension(COLUMNS, resultColnames);
        HashMatrixDimension resultsRows = new HashMatrixDimension(ROWS, sourceRows);

        Heatmap resultHeatmap = new Heatmap(
                new HashMatrix(
                        adapter.getMatrixLayers(),
                        resultsRows,
                        resultColumns
                )
        );
        resultHeatmap.getColumns().addAnnotations(resultColumnAnnotations);


        // Run comparison
        dataMatrix.newPosition()
                .iterate(sourceRows)
                .monitor(monitor, "Running group comparison analysis")
                .transform(
                        new GroupComparisonFunction(
                                test,
                                sourceColumns,
                                layer,
                                nullConversionFunction,
                                groups)
                )
                .store(resultHeatmap, new MapLayerAdapter<>(resultColumns, adapter));

        // Run multiple test correction
        IMatrixPosition position = resultHeatmap.newPosition();
        IMatrixFunction<Double, Double> mtcFunction = MTCFactory.createFunction(analysis.getMtc());

        for (String condition : position.iterate(resultColumns)) {
            // Left p-Value
            position.iterate(adapter.getLayer(Double.class, "left-p-value"), resultsRows)
                    .transform(mtcFunction)
                    .store(resultHeatmap, adapter.getLayer(Double.class, "corrected-left-p-value"));

            // Right p-Value
            position.iterate(adapter.getLayer(Double.class, "right-p-value"), resultsRows)
                    .transform(mtcFunction)
                    .store(resultHeatmap, adapter.getLayer(Double.class, "corrected-right-p-value"));

            // Two-tail p-Value
            position.iterate(adapter.getLayer(Double.class, "two-tail-p-value"), resultsRows)
                    .transform(mtcFunction)
                    .store(resultHeatmap, adapter.getLayer(Double.class, "corrected-two-tail-p-value"));

        }

        // Results formatting

        for (HeatmapLayer resultLayer : resultHeatmap.getLayers()) {
            if (resultLayer.getId().contains("p-value")) {
                resultLayer.setDecorator(new PValueDecorator());
            }
            resultHeatmap.getLayers().get("p-value-log-sum").setDecorator(
                    new PValueLogSumDecorator()
            );

        }

        resultHeatmap.getLayers().setTopLayerById("p-value-log-sum");
        resultHeatmap.setTitle(analysis.getTitle() + " (results)");

        if (analysis.isCopyAnnotation() && dataMatrix instanceof Heatmap &&
                ((Heatmap) dataMatrix).getRows().getAnnotations() != null) {

            resultHeatmap.getRows().addAnnotations(
                    ((Heatmap) dataMatrix).getRows().getAnnotations()
            );

            for (HeatmapHeader header : ((Heatmap) dataMatrix).getRows().getHeaders()) {

                HeatmapHeader headerClone = null;
                try {
                    headerClone = CloneUtils.clone(header);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                resultHeatmap.getRows().addHeader(headerClone);
                headerClone.init(resultHeatmap.getRows());
            }
        }


        // generate colors for annation
        ColorGenerator cg1 = new ColorGenerator();
        ColorGenerator cg2 = new ColorGenerator();
        ArrayList<ColoredLabel> group1List = new ArrayList<ColoredLabel>();
        ArrayList<ColoredLabel> group2List = new ArrayList<ColoredLabel>();
        for (DimensionGroup g : analysis.getGroups()) {
            group1List.add(new ColoredLabel(g.getName(), cg1.next(g.getName())));
            group2List.add(new ColoredLabel(g.getName(), cg2.next(g.getName())));
        }

        resultHeatmap.getColumns().setCellSize(40);
        resultHeatmap.getLayers().get("p-value-log-sum").setSortDirection(SortDirection.DESCENDING);
        resultHeatmap.getLayers().get("p-value-log-sum").setAggregator(SumAbsAggregator.INSTANCE);

        HeatmapColoredLabelsHeader group1Header = new HeatmapColoredLabelsHeader(resultHeatmap.getColumns());
        group1Header.setClusters(group1List);
        group1Header.setAnnotationPattern("${Group 1}");
        group1Header.setTitle("Group 1");
        HeatmapColoredLabelsHeader group2Header = new HeatmapColoredLabelsHeader(resultHeatmap.getColumns());
        group2Header.setClusters(group2List);
        group2Header.setAnnotationPattern("${Group 2}");
        group2Header.setTitle("Group 2");
        resultHeatmap.getColumns().addHeader(group1Header);
        resultHeatmap.getColumns().addHeader(group2Header);
        return resultHeatmap;
    }


}
