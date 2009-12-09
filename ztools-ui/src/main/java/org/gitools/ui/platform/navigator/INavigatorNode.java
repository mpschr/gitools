package org.gitools.ui.platform.navigator;

import javax.swing.Icon;
import javax.swing.tree.ExpandVetoException;

public interface INavigatorNode {

	void refresh();
	
	void expand() throws ExpandVetoException;
	
	void collapse() throws ExpandVetoException;
	
	Icon getIcon();
	
	String getLabel();

}