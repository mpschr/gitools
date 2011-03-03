/*
 *  Copyright 2011 chris.
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

package org.gitools.heatmap.drawer;

import org.gitools.heatmap.model.Heatmap;
import org.gitools.heatmap.model.HeatmapHeader;
import org.gitools.matrix.model.IMatrixView;

public abstract class AbstractHeatmapHeaderDrawer<HT extends HeatmapHeader> extends AbstractHeatmapDrawer {

	protected static interface LabelProvider {
		String getLabel(int index);
	}

	protected static abstract class MatrixLabelProvider implements LabelProvider {
		protected IMatrixView mv;

		public MatrixLabelProvider(IMatrixView mv) {
			this.mv = mv;
		}
	}

	protected HT header;
	protected boolean horizontal;

	public AbstractHeatmapHeaderDrawer(Heatmap heatmap, HT header, boolean horizontal) {
		super(heatmap);

		this.header = header;
		this.horizontal = horizontal;
	}

	public HT getHeader() {
		return header;
	}

}
