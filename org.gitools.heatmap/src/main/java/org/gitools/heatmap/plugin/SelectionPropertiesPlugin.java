/*
 * #%L
 * org.gitools.heatmap
 * %%
 * Copyright (C) 2013 - 2014 Universitat Pompeu Fabra - Biomedical Genomics group
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
package org.gitools.heatmap.plugin;

import javax.enterprise.context.Dependent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Dependent
public class SelectionPropertiesPlugin extends AbstractPlugin implements IBoxPlugin {

    public static String NAME = "SelectionProperties";

    public SelectionPropertiesPlugin() {
        super(NAME);
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getOldestCompatibleVersion() {
        return "1.0.0";
    }

    @Override
    public PluginAccess getPluginAccess() {
        return IBoxPlugin.ACCESSES;
    }


}
