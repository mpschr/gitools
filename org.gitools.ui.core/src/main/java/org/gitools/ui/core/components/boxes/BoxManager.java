/*
 * #%L
 * org.gitools.ui.app
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

package org.gitools.ui.core.components.boxes;

import org.gitools.ui.core.Application;
import org.gitools.ui.core.components.editor.AbstractEditor;

import java.util.Collection;
import java.util.HashSet;

public class BoxManager {

    private static HashSet<String> protectedIds = new HashSet<>();
    private static Object boxesById;

    /**
     * Uncollapses all boxes whose ids match boxIds, collapses all others,
     * except those that match the ids in protectedIds
     *
     * @param boxIds
     */
    public static void openOnly(String... boxIds) {
        Collection<Box> boxes = getBoxes();
        Collection<String> focusedIds = new HashSet<>();
        for (String id : boxIds) {
            focusedIds.add(id);
        }

        for (Box b : boxes) {
            boolean focused = focusedIds.contains(b.getId());
            if (b.isVisible() && !protectedIds.contains(b.getId()) && focused) {
                open(b);
            } else if (b.isVisible() && !protectedIds.contains(b.getId()) && !focused) {
                close(b);
            }
        }
    }

    public static Collection<Box> getBoxes() {
        AbstractEditor editor = Application.get().getEditorsPanel().getSelectedEditor();
        if (editor.getBoxes() != null) {
            return editor.getBoxes();
        }
        return new HashSet<Box>();
    }


    public static void close(Box... boxes) {
        for (Box b : boxes) {
            b.setCollapsed(true);
        }
    }

    public static void open(Box... boxes) {
        for (Box b : boxes) {
            b.setCollapsed(false);
        }
    }

    public static void protect(String... ids) {
        protectedIds = new HashSet<>();
        for (String id : ids) {
            protectedIds.add(id);
        }
    }

    public static void reset() {
        protectedIds.clear();
    }

    public static Box getBoxById(String id) {
        for (Box b : getBoxes()) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }
}
