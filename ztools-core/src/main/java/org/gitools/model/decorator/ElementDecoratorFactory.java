package org.gitools.model.decorator;

import java.util.ArrayList;
import java.util.List;

import org.gitools.model.decorator.impl.BinaryElementDecorator;
import org.gitools.model.decorator.impl.Log2RatioElementDecorator;
import org.gitools.model.decorator.impl.PValueElementDecorator;
import org.gitools.model.decorator.impl.ZScoreElementDecorator;
import org.gitools.model.matrix.element.IElementAdapter;


public class ElementDecoratorFactory {

	private static List<ElementDecoratorDescriptor> descriptors
			= new ArrayList<ElementDecoratorDescriptor>();
	
	static {
		descriptors.add(new ElementDecoratorDescriptor(
				ElementDecoratorNames.PVALUE, PValueElementDecorator.class));
		
		descriptors.add(new ElementDecoratorDescriptor(
				ElementDecoratorNames.ZSCORE, ZScoreElementDecorator.class));
		
		descriptors.add(new ElementDecoratorDescriptor(
				ElementDecoratorNames.BINARY, BinaryElementDecorator.class));
		
		descriptors.add(new ElementDecoratorDescriptor(
				ElementDecoratorNames.LOG2RATIO, Log2RatioElementDecorator.class));
	}
	
	public static ElementDecorator create(
			String name, 
			IElementAdapter adapter) {
		
		for (ElementDecoratorDescriptor descriptor : descriptors)
			if (descriptor.getName().equals(name))
				return create(descriptor, adapter);
		
		return null;
	}
	
	public static ElementDecorator create(
			ElementDecoratorDescriptor descriptor, 
			IElementAdapter adapter) {
		
		final Class<? extends ElementDecorator> cls = descriptor.getDecoratorClass();
		
		ElementDecorator decorator = null;
		try {
			decorator = cls.getConstructor(IElementAdapter.class)
								.newInstance(new Object[] { adapter });
		}
		catch (Exception e) {
			return null;
		}
		
		return decorator;
	}

	public static ElementDecoratorDescriptor getDescriptor(
			Class<? extends ElementDecorator> decoratorClass) {
		
		for (ElementDecoratorDescriptor desc : descriptors)
			if (desc.getDecoratorClass().equals(decoratorClass))
				return desc;
		
		return null;
	}
	
	public static List<ElementDecoratorDescriptor> getDescriptors() {
		return descriptors;
	}
}