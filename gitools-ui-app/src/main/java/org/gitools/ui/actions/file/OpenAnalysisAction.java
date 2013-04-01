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

import org.gitools.analysis.combination.CombinationAnalysis;
import org.gitools.analysis.correlation.CorrelationAnalysis;
import org.gitools.analysis.groupcomparison.GroupComparisonAnalysis;
import org.gitools.analysis.htest.enrichment.EnrichmentAnalysis;
import org.gitools.analysis.htest.oncozet.OncodriveAnalysis;
import org.gitools.analysis.overlapping.OverlappingAnalysis;
import org.gitools.model.Analysis;
import org.gitools.persistence.PersistenceManager;
import org.gitools.persistence._DEPRECATED.FileFormat;
import org.gitools.persistence._DEPRECATED.FileFormats;
import org.gitools.persistence._DEPRECATED.MimeTypes;
import org.gitools.ui.IconNames;
import org.gitools.ui.analysis.combination.editor.CombinationAnalysisEditor;
import org.gitools.ui.analysis.correlation.editor.CorrelationAnalysisEditor;
import org.gitools.ui.analysis.groupcomparison.editor.GroupComparisonAnalysisEditor;
import org.gitools.ui.analysis.htest.editor.EnrichmentAnalysisEditor;
import org.gitools.ui.analysis.htest.editor.OncodriveAnalysisEditor;
import org.gitools.ui.analysis.overlapping.OverlappingAnalysisEditor;
import org.gitools.ui.platform.AppFrame;
import org.gitools.ui.platform.actions.BaseAction;
import org.gitools.ui.platform.editor.AbstractEditor;
import org.gitools.ui.platform.progress.JobRunnable;
import org.gitools.ui.platform.progress.JobThread;
import org.gitools.ui.settings.Settings;
import org.gitools.ui.utils.FileChooserUtils;
import org.gitools.ui.utils.FileFormatFilter;
import org.gitools.utils.progressmonitor.IProgressMonitor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class OpenAnalysisAction extends BaseAction
{

    private static final long serialVersionUID = -6528634034161710370L;

    public OpenAnalysisAction()
    {
        super("Analysis ...");
        setDesc("Open an analysis from the file system");
        setSmallIconFromResource(IconNames.openAnalysis16);
        setLargeIconFromResource(IconNames.openAnalysis24);
        setMnemonic(KeyEvent.VK_A);
        setDefaultEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        FileFilter[] filters = new FileFilter[]{
                new FileFormatFilter("Any analysis", null, new FileFormat[]{
                        FileFormats.ENRICHMENT,
                        FileFormats.ONCODRIVE,
                        FileFormats.CORRELATIONS,
                        FileFormats.COMBINATION,
                        FileFormats.OVERLAPPING,
                        FileFormats.GROUP_COMPARISON
                }),
                new FileFormatFilter(FileFormats.ENRICHMENT),
                new FileFormatFilter(FileFormats.ONCODRIVE),
                new FileFormatFilter(FileFormats.CORRELATIONS),
                new FileFormatFilter(FileFormats.COMBINATION),
                new FileFormatFilter(FileFormats.OVERLAPPING),
                new FileFormatFilter(FileFormats.GROUP_COMPARISON)
        };

        FileChooserUtils.FileAndFilter ret = FileChooserUtils.selectFile(
                "Select the analysis file",
                Settings.getDefault().getLastPath(),
                FileChooserUtils.MODE_OPEN,
                filters);

        if (ret == null)
        {
            return;
        }

        final File file = ret.getFile();
        final FileFormatFilter filter = (FileFormatFilter) ret.getFilter();

        if (file != null)
        {
            Settings.getDefault().setLastPath(file.getParent());
            Settings.getDefault().save();

            JobThread.execute(AppFrame.get(), new JobRunnable()
            {
                @Override
                public void run(IProgressMonitor monitor)
                {
                    try
                    {
                        AbstractEditor editor = null;

                        String mime = filter.getMime();
                        if (mime == null)
                        {
                            mime = PersistenceManager.get().getMimeFromFile(file.getName());
                        }

                        Analysis analysis = (Analysis) PersistenceManager.get()
                                .load(file, mime, monitor);

                        if (monitor.isCancelled())
                        {
                            return;
                        }

                        if (mime.equals(MimeTypes.ENRICHMENT_ANALYSIS))
                        {
                            editor = new EnrichmentAnalysisEditor((EnrichmentAnalysis) analysis);
                        }
                        else if (mime.equals(MimeTypes.ONCODRIVE_ANALYSIS))
                        {
                            editor = new OncodriveAnalysisEditor((OncodriveAnalysis) analysis);
                        }
                        else if (mime.equals(MimeTypes.CORRELATIONS_ANALYSIS))
                        {
                            editor = new CorrelationAnalysisEditor((CorrelationAnalysis) analysis);
                        }
                        else if (mime.equals(MimeTypes.COMBINATION_ANALYSIS))
                        {
                            editor = new CombinationAnalysisEditor((CombinationAnalysis) analysis);
                        }
                        else if (mime.equals(MimeTypes.OVERLAPPING_ANALYSIS))
                        {
                            editor = new OverlappingAnalysisEditor((OverlappingAnalysis) analysis);
                        }
                        else if (mime.equals(MimeTypes.GROUPCOMPARISON_ANALYSIS))
                        {
                            editor = new GroupComparisonAnalysisEditor((GroupComparisonAnalysis) analysis);
                        }

                        editor.setName(file.getName());

                        final AbstractEditor newEditor = editor;
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                AppFrame.get().getEditorsPanel().addEditor(newEditor);
                                AppFrame.get().refresh();
                            }
                        });
                    } catch (Exception ex)
                    {
                        monitor.exception(ex);
                    }
                }
            });
        }
    }
}
