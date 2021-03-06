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
package org.gitools.ui.app.heatmap.drawer;

import org.gitools.heatmap.Heatmap;
import org.gitools.heatmap.HeatmapDimension;
import org.gitools.heatmap.decorator.Decoration;
import org.gitools.heatmap.header.HeatmapHeader;
import org.gitools.ui.core.HeatmapPosition;

import java.awt.*;

public abstract class AbstractHeatmapHeaderDrawer<HT extends HeatmapHeader> extends AbstractHeatmapDrawer {

    private final HT header;
    private HeatmapDimension heatmapDimension;

    protected AbstractHeatmapHeaderDrawer(Heatmap heatmap, HeatmapDimension heatmapDimension, HT header) {
        super(heatmap);

        this.header = header;
        this.heatmapDimension = heatmapDimension;
    }

    public HT getHeader() {
        return header;
    }

    @Override
    public Point getPoint(HeatmapPosition p) {
        int index = (isHorizontal() ? p.row : p.column);
        int point = getHeaderPoint(index);
        return (isHorizontal() ? new Point(point, 0) : new Point(0, point));
    }

    protected int getHeaderPoint(int index) {

        HeatmapDimension hdim = getHeatmapDimension();
        int cellSize = hdim.getFullCellSize();
        int totalSize = cellSize * hdim.size();

        int point = index >= 0 ? index * cellSize : 0;
        if (point > totalSize) {
            point = totalSize;
        }

        return point;
    }

    protected String getLabel(int index) {
        return getHeader().getIdentifierTransform().apply(getHeatmapDimension().getLabel(index));
    }

    protected boolean isHighlightedIndex(int i) {
        return getHeatmapDimension().isHighlighted(getHeatmapDimension().getLabel(i));
    }

    @Override
    public HeatmapPosition getPosition(Point p) {
        int point = (isHorizontal() ? p.x : p.y);
        int index = getHeaderPosition(point);
        return (isHorizontal() ? new HeatmapPosition(getHeatmap(), -1, index) : new HeatmapPosition(getHeatmap(), index, -1));
    }

    protected int getHeaderPosition(int point) {
        HeatmapDimension hdim = getHeatmapDimension();
        int index = -1;
        int cellSize = hdim.getFullCellSize();
        int totalSize = cellSize * hdim.size();
        if (point >= 0 && point < totalSize) {
            index = point / cellSize;
        }
        return index;
    }

    @Override
    public Dimension getSize() {
        HeatmapDimension hdim = getHeatmapDimension();
        int total = (hdim.getFullCellSize()) * hdim.size();
        return (isHorizontal() ? new Dimension(total, getHeader().getSize()) : new Dimension(getHeader().getSize(), total));
    }

    @Deprecated
    public boolean isHorizontal() {
        return getHeatmap().getColumns() == heatmapDimension;
    }

    public void drawHeaderLegend(Graphics2D g, Rectangle headerIntersection, HeatmapHeader heatmapHeader) {
    }

    public HeatmapDimension getHeatmapDimension() {
        return heatmapDimension;
    }

    protected int fullCellSize() {
        return heatmapDimension.getFullCellSize();
    }

    protected void prepareDraw(Graphics2D g, Rectangle box) {
        paintBackground(header.getBackgroundColor(), g, box);
        calculateFontSize(g, header.getHeatmapDimension().getCellSize(), 8);
    }

    protected void paintCell(Decoration decoration, int index, int offset, int width, Graphics2D g, Rectangle box) {

        int gridSize = (heatmapDimension.showGrid() ? heatmapDimension.getGridSize() : 0);

        paintCell(
                decoration,
                header.getBackgroundColor(),
                gridSize,
                offset, index * (heatmapDimension.getFullCellSize()),
                width,
                heatmapDimension.getCellSize(),
                g,
                box
        );

    }

    protected int firstVisibleIndex(Rectangle box, Rectangle clip) {
        int size = fullCellSize();
        int clipStart = clip.y - box.y;
        int index = ((clipStart - size) / size);
        index = (index < 0 ? 0 : index);
        return index;
    }

    protected int lastVisibleIndex(Rectangle box, Rectangle clip) {
        int size = fullCellSize();
        int clipStart = clip.y - box.y;
        int clipEnd = clipStart + clip.height;
        int index = ((clipEnd + size - 1) / size);
        index = ((index + 1) > heatmapDimension.size() ? heatmapDimension.size() - 1 : index);
        return index;
    }


    /**
     * Configure the header and drawer if necessary according to where
     * the user has clicked in the heatmap header
     * @param p
     * @param x
     * @param y
     */
    public void configure(Point p, int x, int y) {
        // override if neccessary
    }

}
