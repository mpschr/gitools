package es.imim.bg.ztools.ui.utils;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import es.imim.bg.ztools.ui.IconNames;

public class IconUtils {

	public static Icon getIconResource(String name) {
		URL url = IconUtils.class.getResource(name);
		if (url == null)
			url = IconUtils.class.getResource(IconNames.nullResource);
		
		return new ImageIcon(url);
	}
}
