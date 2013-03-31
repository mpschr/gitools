/*
 *  Copyright 2010 Universitat Pompeu Fabra.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.gitools.ui.analysis.htest.wizard;

import org.gitools.utils.progressmonitor.IProgressMonitor;
import org.gitools.analysis.htest.oncozet.OncodriveAnalysis;
import org.gitools.persistence._DEPRECATED.FileFormat;
import org.gitools.persistence._DEPRECATED.FileFormats;
import org.gitools.persistence._DEPRECATED.FileSuffixes;
import org.gitools.persistence.PersistenceManager;
import org.gitools.persistence.formats.analysis.AbstractXmlFormat;
import org.gitools.ui.IconNames;
import org.gitools.ui.analysis.wizard.*;
import org.gitools.ui.examples.ExamplesManager;
import org.gitools.ui.platform.AppFrame;
import org.gitools.ui.platform.IconUtils;
import org.gitools.ui.platform.dialog.MessageStatus;
import org.gitools.ui.platform.progress.JobRunnable;
import org.gitools.ui.platform.progress.JobThread;
import org.gitools.ui.platform.wizard.AbstractWizard;
import org.gitools.ui.platform.wizard.IWizardPage;
import org.gitools.ui.settings.Settings;
import org.gitools.ui.wizard.common.SaveFilePage;

import javax.swing.*;
import java.io.File;
import java.util.Properties;

public class OncodriveAnalysisWizard extends AbstractWizard {

	private static final String EXAMPLE_ANALYSIS_FILE = "analysis." + FileSuffixes.ONCODRIVE;
	private static final String EXAMPLE_DATA_FILE = "TCGA_gbm_filtered_annot.cdm.gz";
	private static final String EXAMPLE_COLUMN_SETS_FILE = "TCGA_gbm_sample_subtypes.tcm";

	private ExamplePage examplePage;
	private DataFilePage dataPage;
	private DataFilterPage dataFilterPage;
	private ModulesPage modulesPage;
	private StatisticalTestPage statisticalTestPage;
	private SaveFilePage saveFilePage;
	private AnalysisDetailsPage analysisDetailsPage;

	public OncodriveAnalysisWizard() {
		super();
		
		setTitle("Oncodrive analysis");
		setLogo(IconUtils.getImageIconResourceScaledByHeight(IconNames.LOGO_ONCODRIVE, 96));
		setHelpContext("analysis_oncodrive");
	}
	
	@Override
	public void addPages() {
		// Example
		if (Settings.getDefault().isShowCombinationExamplePage()) {
			examplePage = new ExamplePage("an Oncodriver analysis");
			examplePage.setTitle("Oncodriver analysis");
			addPage(examplePage);
		}

		// Data
		dataPage = new DataFilePage();
		addPage(dataPage);

		// Data filtering
		dataFilterPage = new DataFilterPage();
		addPage(dataFilterPage);

		// Modules
		modulesPage = new ModulesPage();
		modulesPage.setMinSize(0);
		modulesPage.setEmptyFileAllowed(true);
		modulesPage.setTitle("Select sets of columns to be analysed independently");
		modulesPage.setMessage(MessageStatus.INFO, "If no file is selected then all data columns will be analysed as one set");
		addPage(modulesPage);

		// Statistical test
		statisticalTestPage = new StatisticalTestPage();
		addPage(statisticalTestPage);

		// Destination
		saveFilePage = new SaveFilePage();
		saveFilePage.setTitle("Select destination file");
		saveFilePage.setFolder(Settings.getDefault().getLastWorkPath());
		saveFilePage.setFormats(new FileFormat[] {
			FileFormats.ONCODRIVE });
		saveFilePage.setFormatsVisible(false);
		addPage(saveFilePage);

		// Analysis details
		analysisDetailsPage = new AnalysisDetailsPage();
		addPage(analysisDetailsPage);
	}

	@Override
	public void pageLeft(IWizardPage currentPage) {
		if (currentPage == examplePage) {
			Settings.getDefault().setShowCombinationExamplePage(
					examplePage.isShowAgain());

			if (examplePage.isExampleEnabled()) {
				JobThread.execute(AppFrame.instance(), new JobRunnable() {
					@Override public void run(IProgressMonitor monitor) {

						final File basePath = ExamplesManager.getDefault().resolvePath("oncodrive", monitor);

						if (basePath == null)
							throw new RuntimeException("Unexpected error: There are no examples available");

						File analysisFile = new File(basePath, EXAMPLE_ANALYSIS_FILE);
						Properties props = new Properties();
						props.setProperty(AbstractXmlFormat.LOAD_REFERENCES_PROP, "false");
						try {
							monitor.begin("Loading example parameters ...", 1);

							final OncodriveAnalysis a = PersistenceManager.get()
									.load(analysisFile, OncodriveAnalysis.class, props, monitor);

							SwingUtilities.invokeLater(new Runnable() {
								@Override public void run() {
									setAnalysis(a);

									dataPage.setFile(new File(basePath, EXAMPLE_DATA_FILE));
									modulesPage.setSelectedFile(new File(basePath, EXAMPLE_COLUMN_SETS_FILE));
									saveFilePage.setFileNameWithoutExtension("example");
								}
							});

							monitor.end();
						}
						catch (Exception ex) {
							monitor.exception(ex);
						}
					}
				});
			}
		}
	}

	@Override
	public boolean canFinish() {
		IWizardPage page = getCurrentPage();

		boolean canFinish = super.canFinish();
		canFinish |= page == saveFilePage && page.isComplete();

		return canFinish;
	}

	@Override
	public void performCancel() {
		super.performCancel();
	}

	@Override
	public void performFinish() {
		Settings.getDefault().setLastWorkPath(saveFilePage.getFolder());
	}

	public String getWorkdir() {
		return saveFilePage.getFolder();
	}

	public String getFileName() {
		return saveFilePage.getFileName();
	}

	public String getDataFileMime() {
		return dataPage.getFileFormat().getMime();
	}

	public File getDataFile() {
		return dataPage.getFile();
	}

    public int getSelectedValueIndex() {
        return dataPage.getSelectedValueIndex();
    }

	public File getPopulationFile() {
		return dataFilterPage.getRowsFilterFile();
	}

	public Double getPopulationDefaultValue() {
		return dataFilterPage.getPopulationDefaultValue();
	}

	public String getModulesFileMime() {
		return modulesPage.getFileMime();
	}

	public File getModulesFile() {
		return modulesPage.getSelectedFile();
	}

	public OncodriveAnalysis getAnalysis() {
		OncodriveAnalysis analysis = new OncodriveAnalysis();

		analysis.setTitle(analysisDetailsPage.getAnalysisTitle());
		analysis.setDescription(analysisDetailsPage.getAnalysisNotes());
		analysis.setAttributes(analysisDetailsPage.getAnalysisAttributes());

		analysis.setBinaryCutoffEnabled(dataFilterPage.isBinaryCutoffEnabled());
		analysis.setBinaryCutoffCmp(dataFilterPage.getBinaryCutoffCmp());
		analysis.setBinaryCutoffValue(dataFilterPage.getBinaryCutoffValue());
		analysis.setMinModuleSize(modulesPage.getMinSize());
		analysis.setMaxModuleSize(modulesPage.getMaxSize());
		analysis.setTestConfig(statisticalTestPage.getTestConfig());
		analysis.setMtc(statisticalTestPage.getMtc());

		return analysis;
	}

	private void setAnalysis(OncodriveAnalysis a) {
		analysisDetailsPage.setAnalysisTitle(a.getTitle());
		analysisDetailsPage.setAnalysisNotes(a.getDescription());
		analysisDetailsPage.setAnalysisAttributes(a.getAttributes());
		dataFilterPage.setBinaryCutoffEnabled(a.isBinaryCutoffEnabled());
		dataFilterPage.setBinaryCutoffCmp(a.getBinaryCutoffCmp());
		dataFilterPage.setBinaryCutoffValue(a.getBinaryCutoffValue());
		modulesPage.setMinSize(a.getMinModuleSize());
		modulesPage.setMaxSize(a.getMaxModuleSize());
		statisticalTestPage.setTestConfig(a.getTestConfig());
		statisticalTestPage.setMtc(a.getMtc());
	}
}
