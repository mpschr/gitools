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

import org.gitools.biomart.BiomartService;
import org.gitools.biomart.restful.model.Query;
import org.gitools.ui.IconNames;
import org.gitools.ui.biomart.wizard.BiomartTableWizard;
import org.gitools.ui.platform.AppFrame;
import org.gitools.ui.platform.actions.BaseAction;
import org.gitools.ui.platform.progress.JobRunnable;
import org.gitools.ui.platform.progress.JobThread;
import org.gitools.ui.platform.wizard.WizardDialog;
import org.gitools.utils.progressmonitor.IProgressMonitor;

import java.awt.event.ActionEvent;
import java.io.File;

public class ImportBiomartTableAction extends BaseAction
{

    private static final long serialVersionUID = 4381993756203388654L;

    public ImportBiomartTableAction()
    {
        super("Biomart Table ...");
        setLargeIconFromResource(IconNames.biomart24);
        setSmallIconFromResource(IconNames.biomart16);
        setDefaultEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        //final IBiomartService service = BiomartServiceFactory.createDefaultservice();
        //final BiomartCentralPortalSoapService service = BiomartCentralPortalSoapService.getDefault();
        //List<BiomartSource> lBs = BiomartSourceManager.getDefault().getSources();
        //BiomartSource bsrc = lBs.get(0);
        //final BiomartRestfulService service = BiomartServiceFactory.createRestfulService(bsrc);

        final BiomartTableWizard wizard = new BiomartTableWizard();

        WizardDialog wdlg = new WizardDialog(AppFrame.get(), wizard);
        wdlg.open();
        if (wdlg.isCancelled())
        {
            return;
        }
        final File file = wizard.getSelectedFile();
        JobThread.execute(AppFrame.get(), new JobRunnable()
        {

            @Override
            public void run(IProgressMonitor monitor)
            {
                monitor.begin("Downloading data...", 1);
                Query query = wizard.getQuery();
                String format = (String) wizard.getFormat().getMime();
                BiomartService service = wizard.getService();
                try
                {
                    service.queryTable(query, file, format, wizard.isSkipRowsWithEmptyValuesEnabled(), wizard.emptyValuesReplacement(), monitor);
                } catch (Exception ex)
                {
                    monitor.exception(ex);
                }
                monitor.end();
            }
        });

    }

}
