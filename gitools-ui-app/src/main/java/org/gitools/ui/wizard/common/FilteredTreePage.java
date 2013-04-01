/*
 * #%L
 * gitools-ui-app
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
package org.gitools.ui.wizard.common;

import org.gitools.ui.platform.wizard.AbstractWizardPage;

import javax.swing.*;
import javax.swing.tree.TreeModel;

public abstract class FilteredTreePage extends AbstractWizardPage
{

    protected FilteredTreePanel panel;

    @Override
    public JComponent createControls()
    {
        panel = new FilteredTreePanel()
        {
            @Override
            protected TreeModel updateModel(String filterText)
            {
                return pageCreateModel(filterText);
            }
        };

        return panel;
    }

    protected abstract TreeModel createModel(String filterText);

    protected TreeModel pageCreateModel(String filterText)
    {
        return createModel(filterText);
    }

    public FilteredTreePanel getPanel()
    {
        return panel;
    }
}
