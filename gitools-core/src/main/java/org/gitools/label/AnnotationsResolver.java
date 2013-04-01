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
package org.gitools.label;

import org.gitools.matrix.model.AnnotationMatrix;
import org.gitools.utils.textpatt.TextPattern;

public class AnnotationsResolver implements TextPattern.VariableValueResolver
{

    private LabelProvider labelProvider;
    private AnnotationMatrix am;
    private int index;

    public AnnotationsResolver(LabelProvider labelProvider, AnnotationMatrix am)
    {
        this.labelProvider = labelProvider;
        this.am = am;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    @Override
    public String resolveValue(String variableName)
    {
        String label = labelProvider.getLabel(index);
        if (variableName.equalsIgnoreCase("id"))
        {
            return label;
        }

        int annRow = am != null ? am.getRowIndex(label) : -1;
        if (annRow == -1)
        {
            return "${" + variableName + "}";
        }

        int annCol = am.getColumnIndex(variableName);
        return am.getCell(annRow, annCol);
    }
}
