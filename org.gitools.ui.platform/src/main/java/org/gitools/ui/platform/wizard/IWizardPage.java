/*
 * #%L
 * gitools-ui-platform
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
package org.gitools.ui.platform.wizard;

import org.gitools.ui.platform.dialog.MessageStatus;
import org.gitools.ui.platform.help.HelpContext;

import javax.swing.*;

public interface IWizardPage {

    String getId();

    void setId(String id);

    void setTitle(String title);

    void setMessage(String message);

    void setMessage(MessageStatus status, String message);

    IWizard getWizard();

    void setWizard(IWizard wizard);

    boolean isComplete();

    JComponent createControls();

    void updateControls();

    public void updateModel();

    String getTitle();

    Icon getLogo();

    MessageStatus getStatus();

    String getMessage();

    HelpContext getHelpContext();

    void setHelpContext(HelpContext context);

    void addPageUpdateListener(IWizardPageUpdateListener listener);

    void removePageUpdateListener(IWizardPageUpdateListener listener);
}