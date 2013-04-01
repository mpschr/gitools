/*
 * #%L
 * gitools-biomart
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
package org.gitools.biomart.queryhandler;

import org.gitools.model.ModuleMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModuleMapQueryHandler implements BiomartQueryHandler
{

    private ModuleMap mmap;

    private Map<String, Set<String>> map;

    @Override
    public void begin() throws Exception
    {
        map = new HashMap<String, Set<String>>();
    }

    @Override
    public void line(String[] fields) throws Exception
    {
        String srcf = fields[0];
        String dstf = fields[1];
        Set<String> items = map.get(srcf);
        if (items == null)
        {
            items = new HashSet<String>();
            map.put(srcf, items);
        }
        items.add(dstf);
    }

    @Override
    public void end()
    {
        mmap = new ModuleMap(map);
        map.clear();
        map = null;
    }

    public ModuleMap getModuleMap()
    {
        return mmap;
    }
}
