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
package org.gitools.ui.app.actions.file;

import org.gitools.ui.core.Application;
import org.gitools.ui.core.actions.AbstractAction;
import org.gitools.ui.core.components.editor.AbstractEditor;
import org.gitools.ui.platform.settings.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ExitAction extends AbstractAction {

    private static final long serialVersionUID = -2861462318817904958L;

    public ExitAction() {
        super("Exit");

        setDesc("Close aplication");
        setMnemonic(KeyEvent.VK_X);
        setDefaultEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean dirty = false;
        for (AbstractEditor editor : Application.get().getEditorsPanel().getEditors()) {
            dirty = dirty || editor.isDirty();
        }

        if (dirty) {
            int dialogResult = JOptionPane.showConfirmDialog(Application.get(), "Do you want to close Gitools without saving changes?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
            if (dialogResult != JOptionPane.YES_OPTION) {
                return;
            }
        }

        Settings.get().save();
        System.exit(0);
    }

}
