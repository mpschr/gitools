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
package org.gitools.ui.actions.file;

import org.gitools.ui.IconNames;
import org.gitools.ui.dialog.UnimplementedDialog;
import org.gitools.ui.platform.AppFrame;
import org.gitools.ui.platform.actions.BaseAction;

import java.awt.event.ActionEvent;

public class ImportBiomartMatrixAction extends BaseAction
{

    private static final long serialVersionUID = 4381993756203388654L;

    public ImportBiomartMatrixAction()
    {
        super("Biomart Matrix ...");
        setLargeIconFromResource(IconNames.biomart24);
        setSmallIconFromResource(IconNames.biomart16);
        setDefaultEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        UnimplementedDialog.show(AppFrame.get());
    }

}
