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

package org.gitools.ui.analysis.overlapping;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JSplitPane;
import org.gitools.analysis.overlapping.OverlappingAnalysis;
import org.gitools.heatmap.model.Heatmap;
import org.gitools.matrix.DiagonalMatrixView;
import org.gitools.matrix.model.IMatrixView;
import org.gitools.matrix.model.element.IElementAdapter;
import org.gitools.model.decorator.impl.LinearTwoSidedElementDecorator;
import org.gitools.ui.analysis.editor.AbstractTablesPanel;
import org.gitools.ui.heatmap.editor.HeatmapEditor;
import org.gitools.ui.platform.actions.BaseAction;


public class OverlappingResultsEditor extends HeatmapEditor {

	//private OverlappingAnalysis analysis;

	protected AbstractTablesPanel tablesPanel;

	protected static Heatmap createHeatmap(OverlappingAnalysis analysis) {
		IMatrixView results = new DiagonalMatrixView(analysis.getCellResults());
		Heatmap heatmap = new Heatmap(results);
		heatmap.setTitle(analysis.getTitle() + " (results)");
		IElementAdapter cellAdapter = results.getCellAdapter();
		LinearTwoSidedElementDecorator dec = new LinearTwoSidedElementDecorator(cellAdapter);
		int valueIndex = cellAdapter.getPropertyIndex("jaccard-index");
		Color minColor = new Color(0x63, 0xdc, 0xfe);
		Color maxColor = new Color(0xff, 0x00, 0x5f);
		dec.setValueIndex(valueIndex != -1 ? valueIndex : 0);
		dec.setMinValue(0.0);
		dec.setMinColor(minColor);
		dec.setMidValue(0.0);
		dec.setMidColor(minColor);
		dec.setMaxValue(1.0);
		dec.setMaxColor(maxColor);
		dec.setEmptyColor(Color.WHITE);
		heatmap.setCellDecorator(dec);

		heatmap.setTitle(analysis.getTitle());

		return heatmap;
	}

	protected static List<BaseAction> createToolBar(OverlappingAnalysis analysis) {
		return null;
	}

	public OverlappingResultsEditor(OverlappingAnalysis analysis) {
		super(createHeatmap(analysis), createToolBar(analysis), true);

		tablesPanel = new OverlappingTablesPanel(analysis, heatmap);
		tablesPanel.setMinimumSize(new Dimension(140, 140));

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(1);
		splitPane.setOneTouchExpandable(true);
		splitPane.setTopComponent(embeddedContainer);
		splitPane.setBottomComponent(tablesPanel);

		setLayout(new BorderLayout());
		add(splitPane);
	}


}
