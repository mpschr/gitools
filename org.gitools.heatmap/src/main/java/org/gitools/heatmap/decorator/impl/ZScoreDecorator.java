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
package org.gitools.heatmap.decorator.impl;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.gitools.api.matrix.IMatrix;
import org.gitools.api.matrix.IMatrixLayer;
import org.gitools.api.matrix.IMatrixPosition;
import org.gitools.heatmap.decorator.Decoration;
import org.gitools.heatmap.decorator.Decorator;
import org.gitools.matrix.MatrixUtils;
import org.gitools.utils.colorscale.ColorConstants;
import org.gitools.utils.colorscale.impl.ZScoreColorScale;
import org.gitools.utils.formatter.ITextFormatter;
import org.gitools.utils.xml.adapter.ColorXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class ZScoreDecorator extends Decorator<ZScoreColorScale> {
    public static final String PROPERTY_SIGNIFICANCE = "significanceLevel";
    public static final String PROPERTY_CORRECTED_VALUE = "correctedValueIndex";
    public static final String PROPERTY_USE_CORRECTION = "useCorrection";
    public static final String PROPERTY_SIG_HALF_AMPLITUD = "sigHalfAmplitude";
    public static final String PROPERTY_HALF_AMPLITUD = "halfAmplitude";
    public static final String PROPERTY_LEFT_MIN_COLOR = "leftMinColor";
    public static final String PROPERTY_LEFT_MAX_COLOR = "leftMaxColor";
    public static final String PROPERTY_RIGHT_MIN_COLOR = "rightMinColor";
    public static final String PROPERTY_RIGHT_MAX_COLOR = "rightMaxColor";
    public static final String PROPERTY_NON_SIGNIFICANT_COLOR = "nonSignificantColor";
    public static final String PROPERTY_EMPTY_COLOR = "emptyColor";

    private int correctedValueIndex;
    private boolean useCorrection;
    private double significanceLevel;


    private ZScoreColorScale scale;

    @XmlTransient
    private NonEventToNullFunction<ZScoreColorScale> significantEvents;

    public ZScoreDecorator() {
        super();

        scale = new ZScoreColorScale();
        correctedValueIndex = -1;
        useCorrection = false;
        significanceLevel = 0.05;
    }

    public ZScoreDecorator(double significanceLevel, double limits) {
        super();

        scale = new ZScoreColorScale();
        correctedValueIndex = -1;
        useCorrection = false;
        this.significanceLevel = significanceLevel;
        scale.setHalfAmplitude(limits);
        setSigHalfAmplitude(significanceLevel);
    }

    public ZScoreColorScale getScale() {
        return scale;
    }

    public void setScale(ZScoreColorScale scale) {
        this.scale = scale;
    }

    @XmlElement(name = "filter-layer-index")
    public int getCorrectedValueIndex() {
        return correctedValueIndex;
    }

    public void setCorrectedValueIndex(int correctionValueIndex) {
        int old = this.correctedValueIndex;
        this.correctedValueIndex = correctionValueIndex;
        firePropertyChange(PROPERTY_CORRECTED_VALUE, old, correctionValueIndex);
    }

    @XmlElement(name = "use-filter")
    public final boolean getUseCorrection() {
        return useCorrection;
    }

    public final void setUseCorrection(boolean useCorrection) {
        boolean old = this.useCorrection;
        this.useCorrection = useCorrection;
        firePropertyChange(PROPERTY_USE_CORRECTION, old, useCorrection);
    }

    @XmlElement(name = "significance")
    public double getSignificanceLevel() {
        return significanceLevel;
    }

    public void setSignificanceLevel(double sigLevel) {
        double old = this.significanceLevel;
        this.significanceLevel = sigLevel;
        setSigHalfAmplitude(calculateSigHalfAmplitudeFromSigLevel(sigLevel));
        firePropertyChange(PROPERTY_SIGNIFICANCE, old, sigLevel);
    }

    private static NormalDistribution NORMAL = new NormalDistribution();

    private double calculateSigHalfAmplitudeFromSigLevel(double sigLevel) {
        double v = NORMAL.inverseCumulativeProbability(sigLevel / 2);
        return Math.abs(v);
    }

    public final double getSigHalfAmplitude() {
        return getScale().getSigHalfAmplitude();
    }

    public final void setSigHalfAmplitude(double sigHalfAmplitude) {
        double old = getScale().getSigHalfAmplitude();
        getScale().setSigHalfAmplitude(sigHalfAmplitude);
        firePropertyChange(PROPERTY_SIG_HALF_AMPLITUD, old, sigHalfAmplitude);
    }

    @XmlElement(name = "limit")
    public final double getHalfAmplitude() {
        return getScale().getHalfAmplitude();
    }

    public final void setHalfAmplitude(double halfAmplitude) {
        double old = getScale().getHalfAmplitude();
        getScale().setHalfAmplitude(halfAmplitude);
        firePropertyChange(PROPERTY_HALF_AMPLITUD, old, halfAmplitude);
    }

    @XmlElement(name = "left-min-color")
    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public Color getLeftMinColor() {
        return getScale().getLeftMinColor();
    }

    public void setLeftMinColor(Color color) {
        Color old = getScale().getLeftMinColor();
        getScale().setLeftMinColor(color);
        firePropertyChange(PROPERTY_LEFT_MIN_COLOR, old, color);
    }

    @XmlElement(name = "left-max-color")
    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public Color getLeftMaxColor() {
        return getScale().getLeftMaxColor();
    }

    public void setLeftMaxColor(Color color) {
        Color old = getScale().getLeftMaxColor();
        getScale().setLeftMaxColor(color);
        firePropertyChange(PROPERTY_LEFT_MAX_COLOR, old, color);
    }

    @XmlElement(name = "right-min-color")
    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public Color getRightMinColor() {
        return getScale().getRightMinColor();
    }

    public void setRightMinColor(Color color) {
        Color old = getScale().getRightMinColor();
        getScale().setRightMinColor(color);
        firePropertyChange(PROPERTY_RIGHT_MIN_COLOR, old, color);
    }

    @XmlElement(name = "right-max-color")
    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public Color getRightMaxColor() {
        return getScale().getRightMaxColor();
    }

    public void setRightMaxColor(Color color) {
        Color old = getScale().getRightMaxColor();
        getScale().setRightMaxColor(color);
        firePropertyChange(PROPERTY_RIGHT_MAX_COLOR, old, color);
    }

    @XmlElement(name = "non-significant-color")
    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public Color getNonSignificantColor() {
        return getScale().getNonSignificantColor();
    }

    public void setNonSignificantColor(Color color) {
        Color old = getScale().getNonSignificantColor();
        getScale().setNonSignificantColor(color);
        firePropertyChange(PROPERTY_NON_SIGNIFICANT_COLOR, old, color);
    }

    @XmlElement(name = "empty-color")
    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public Color getEmptyColor() {
        return getScale().getEmptyColor();
    }

    public void setEmptyColor(Color color) {
        Color old = getScale().getEmptyColor();
        getScale().setEmptyColor(color);
        firePropertyChange(PROPERTY_EMPTY_COLOR, old, color);
    }

    @Override
    public void decorate(Decoration decoration, ITextFormatter textFormatter, IMatrix matrix, IMatrixLayer layer, String... identifiers) {

        Object value = matrix.get(layer, identifiers);
        double v = toDouble(value);

        if (Double.isNaN(v)) {
            decoration.setBgColor(getScale().getEmptyColor());
            return;
        }

        boolean useScale = true;

        if (useCorrection) {
            Object corrValue = correctedValueIndex >= 0 ? matrix.get(matrix.getLayers().get(correctedValueIndex), identifiers) : 0.0;

            double cv = MatrixUtils.doubleValue(corrValue);

            useScale = cv <= significanceLevel;
        }

        final Color color = useScale ? getScale().valueColor(v) : ColorConstants.nonSignificantColor;

        decoration.setBgColor(color);
        if (isShowValue()) {
            decoration.setValue(textFormatter.format(value));
        }
    }

    @Override
    public NonEventToNullFunction getDefaultEventFunction() {

        if (significantEvents == null) {
            initEvents();
        }

        return significantEvents;
    }

    private void initEvents() {
        significantEvents = new NonEventToNullFunction<ZScoreColorScale>(scale, "Significant Events") {

            @Override
            public Double apply(Double value, IMatrixPosition position) {
                this.position = position;
                return (value == null || Math.abs(value) <= getColorScale().getSigHalfAmplitude()) ?
                        null : Math.abs(value);
            }

            @Override
            public String getDescription() {
                double limit = getColorScale().getSigHalfAmplitude();
                return "All values above and below significance threshold (" +
                        limit + ", " + -limit + ") are events";
            }
        };
    }

    @Override
    public List<NonEventToNullFunction> getEventFunctionAlternatives() {

        List<NonEventToNullFunction> list = new ArrayList<>();
        list.add(significantEvents);
        list.addAll(super.getEventFunctionAlternatives());

        return list;
    }
}
