/*
 *  Copyright 2010 chris.
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

package org.gitools.ui.analysis.correlation.wizard;

import java.io.File;
import org.gitools.analysis.correlation.CorrelationAnalysis;
import org.gitools.persistence.FileFormat;
import org.gitools.persistence.FileSuffixes;
import org.gitools.ui.IconNames;
import org.gitools.ui.analysis.wizard.AnalysisDetailsPage;
import org.gitools.ui.analysis.wizard.DataPage;
import org.gitools.ui.platform.IconUtils;
import org.gitools.ui.platform.wizard.AbstractWizard;
import org.gitools.ui.platform.wizard.IWizardPage;
import org.gitools.ui.settings.Settings;
import org.gitools.ui.common.wizard.SaveFilePage;

public class CorrelationAnalysisWizard extends AbstractWizard {

	private DataPage dataPage;
	protected CorrelationPage2 corrPage;
	private SaveFilePage saveFilePage;
	protected AnalysisDetailsPage analysisDetailsPage;

	public CorrelationAnalysisWizard() {
		super();

		setTitle("Correlation analysis");
		setLogo(IconUtils.getImageIconResourceScaledByHeight(IconNames.LOGO_CORRELATION, 96));
	}

	@Override
	public void addPages() {
		// Data
		dataPage = new DataPage();
		dataPage.setPopulationFileVisible(false);
		dataPage.setDiscardNonMappedRowsVisible(false);
		addPage(dataPage);

		// Correlation method
		corrPage = new CorrelationPage2();
		addPage(corrPage);

		// Destination
		saveFilePage = new SaveFilePage();
		saveFilePage.setTitle("Select destination file");
		saveFilePage.setFolder(Settings.getDefault().getLastWorkPath());
		saveFilePage.setFormats(new FileFormat[] {
			new FileFormat("Correlation analysis (*."
					+ FileSuffixes.CORRELATIONS + ")",
					FileSuffixes.CORRELATIONS) });
		saveFilePage.setFormatsVisible(false);
		addPage(saveFilePage);

		// Analysis details
		analysisDetailsPage = new AnalysisDetailsPage();
		addPage(analysisDetailsPage);
	}

	@Override
	public boolean canFinish() {
		boolean canFinish = super.canFinish();

		IWizardPage page = getCurrentPage();

		canFinish |= page.isComplete() && (page == saveFilePage);

		return canFinish;
	}

	@Override
	public void performFinish() {
		Settings.getDefault().setLastWorkPath(saveFilePage.getFolder());
	}

	public String getWorkdir() {
		return saveFilePage.getFolder();
	}

	public String getFileName() {
		return saveFilePage.getFilePath();
	}

	public String getDataFileMime() {
		return dataPage.getFileFormat().getMime();
	}

	public File getDataFile() {
		return dataPage.getDataFile();
	}

	public File getPopulationFile() {
		return dataPage.getPopulationFile();
	}

	public CorrelationAnalysis getAnalysis() {
		CorrelationAnalysis a = new CorrelationAnalysis();

		a.setTitle(analysisDetailsPage.getAnalysisTitle());
		a.setDescription(analysisDetailsPage.getAnalysisNotes());
		a.setAttributes(analysisDetailsPage.getAnalysisAttributes());

		//a.setAttributeIndex(corrPage.getAttributeIndex());
		a.setReplaceNanValue(corrPage.isReplaceNanValuesEnabled() ?
				corrPage.getReplaceNanValue() : null);
		a.setTransposeData(corrPage.isTransposeEnabled());
		
		return a;
	}
}
