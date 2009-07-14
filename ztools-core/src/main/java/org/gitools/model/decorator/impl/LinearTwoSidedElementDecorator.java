package org.gitools.model.decorator.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.gitools.matrix.MatrixUtils;
import org.gitools.model.decorator.ElementDecoration;
import org.gitools.model.decorator.ElementDecorator;
import org.gitools.model.matrix.element.IElementAdapter;

import edu.upf.bg.GenericFormatter;
import edu.upf.bg.colorscale.LinearTwoSidedColorScale;
import edu.upf.bg.colorscale.util.ColorConstants;

@XmlAccessorType(XmlAccessType.FIELD)
public class LinearTwoSidedElementDecorator extends ElementDecorator {

	private static final long serialVersionUID = -181427286948958314L;

	private int valueIndex;
	@XmlTransient
	private LinearTwoSidedColorScale scale;
	@XmlTransient
	private GenericFormatter fmt = new GenericFormatter("<");
	
	
	public LinearTwoSidedElementDecorator(){
		
		valueIndex = getPropertyIndex(new String[] {
				"value", "log2ratio" });
		
		scale = new LinearTwoSidedColorScale();

	}
	
	
	public LinearTwoSidedElementDecorator(IElementAdapter adapter) {
		super(adapter);
		
		valueIndex = getPropertyIndex(new String[] {
				"value", "log2ratio" });
		
		scale = new LinearTwoSidedColorScale();
	}

	public final int getValueIndex() {
		return valueIndex;
	}

	public final void setValueIndex(int valueIndex) {
		this.valueIndex = valueIndex;
		firePropertyChange(PROPERTY_CHANGED);
	}

	public final double getMinValue() {
		return scale.getMinPoint();
	}

	public final void setMinValue(double minValue) {
		scale.setMinPoint(minValue);
		firePropertyChange(PROPERTY_CHANGED);
	}

	public final double getMidValue() {
		return scale.getMidPoint();
	}

	public final void setMidValue(double midValue) {
		scale.setMidPoint(midValue);
		firePropertyChange(PROPERTY_CHANGED);
	}

	public final double getMaxValue() {
		return scale.getMaxPoint();
	}

	public final void setMaxValue(double maxValue) {
		scale.setMaxPoint(maxValue);
		firePropertyChange(PROPERTY_CHANGED);
	}

	public final Color getMinColor() {
		return scale.getMinColor();
	}

	public final void setMinColor(Color minColor) {
		scale.setMinColor(minColor);
		firePropertyChange(PROPERTY_CHANGED);
	}

	public final Color getMidColor() {
		return scale.getMidColor();
	}

	public final void setMidColor(Color midColor) {
		scale.setMidColor(midColor);
		firePropertyChange(PROPERTY_CHANGED);
	}

	public final Color getMaxColor() {
		return scale.getMaxColor();
	}

	public final void setMaxColor(Color maxColor) {
		scale.setMaxColor(maxColor);
		firePropertyChange(PROPERTY_CHANGED);
	}

	@Override
	public void decorate(ElementDecoration decoration, Object element) {
		decoration.reset();
		
		if (element == null) {
			decoration.setBgColor(ColorConstants.emptyColor);
			decoration.setToolTip("Empty cell");
			return;
		}
		
		Object value = adapter.getValue(element, valueIndex);
		
		double v = MatrixUtils.doubleValue(value);

		final Color color = scale.valueColor(v);
		
		decoration.setBgColor(color);
		decoration.setToolTip(fmt.pvalue(v));
	}
	
	@Override
	public Map<String, String> getConfiguration() {
		
		Map<String, String> configuration = new HashMap <String, String>();
		configuration.put("valueIndex;", Integer.toString(valueIndex));
		return configuration;
	}

	@Override
	public void setConfiguration(Map<String, String> configuration) {
		this.valueIndex = Integer.parseInt((String) configuration.get("valueIndex"));	
	}
}