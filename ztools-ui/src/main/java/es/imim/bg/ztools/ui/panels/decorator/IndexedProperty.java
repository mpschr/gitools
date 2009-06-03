package es.imim.bg.ztools.ui.panels.decorator;

import es.imim.bg.ztools.table.element.IElementProperty;

public class IndexedProperty {
	private int index;
	private IElementProperty property;
	
	public IndexedProperty(int index, IElementProperty property) {
		this.index = index;
		this.property = property;
	}
	
	public int getIndex() {
		return index;
	}
	
	public IElementProperty getProperty() {
		return property;
	}
	
	@Override
	public String toString() {
		return property.getName();
	}
}