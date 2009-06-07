package org.gitools.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.DataFormatException;

import javax.xml.bind.JAXBException;

import org.gitools.datafilters.DoubleFilter;
import org.gitools.datafilters.ValueFilter;
import org.gitools.model.Analysis;
import org.gitools.model.Project;
import org.gitools.model.ToolConfig;
import org.gitools.resources.ProjectResource;
import org.gitools.resources.analysis.AnalysisResource;
import org.gitools.resources.analysis.CsvAnalysisResource;
import org.gitools.resources.analysis.REXmlAnalysisResource;
import org.gitools.stats.test.factory.TestFactory;

import es.imim.bg.progressmonitor.ProgressMonitor;

public abstract class AnalysisCommand implements Command {

	protected static final char defaultSep = '\t';
	protected static final char defaultQuote = '"';
	
	protected String analysisName;
	
	protected String testName;

	protected int samplingNumSamples;
	
	protected String dataFile;
	
	protected ValueFilter valueFilter;
	
	protected String modulesFile;
	
	protected int minModuleSize;
	protected int maxModuleSize;
	
	protected boolean includeNonMappedItems;
	
	protected String workdir;
	
	protected String outputFormat;
	protected boolean resultsByCond;

	public AnalysisCommand(
			String analysisName, String testName, int samplingNumSamples, 
			String dataFile, ValueFilter valueFilter, 
			String groupsFile, int minGroupSize, int maxGroupSize, 
			boolean includeNonMappedItems, 
			String workdir, String outputFormat, boolean resultsByCond) {
		
		this.analysisName = analysisName;
		this.testName = testName;
		this.samplingNumSamples = samplingNumSamples;
		this.dataFile = dataFile;
		this.valueFilter = valueFilter != null ? valueFilter : new DoubleFilter();
		this.modulesFile = groupsFile;
		this.minModuleSize = minGroupSize;
		this.maxModuleSize = maxGroupSize;
		this.includeNonMappedItems = includeNonMappedItems;
		this.workdir = workdir;
		this.outputFormat = outputFormat;
		this.resultsByCond = resultsByCond;
	}
	
	protected TestFactory createTestFactory(String toolName, String configName) {		
		ToolConfig toolConfig =
			TestFactory.createToolConfig(toolName, configName);
		
		TestFactory testFactory = 
			TestFactory.createFactory(toolConfig);
		
		return testFactory;
	}
	
	protected void save(Analysis analysis, ProgressMonitor monitor) 
			throws IOException, DataFormatException {

		final String basePath = workdir + File.separator + analysisName;
		
		monitor.begin("Saving project ...", 1);
		monitor.info("Location: " + basePath);
		
		Set<String> formats = new HashSet<String>();
		for (String format : outputFormat.split(","))
			formats.add(format);
		formats.add("csv"); // to mantain compatibility
		
		//saveProject(basePath, analysis, monitor);
		
		for (String format : formats) {
			AnalysisResource ar = null;
			
			if ("csv".equalsIgnoreCase(format))
				ar = new CsvAnalysisResource(basePath, resultsByCond);
			else if ("rexml".equalsIgnoreCase(format))
				ar = new REXmlAnalysisResource(basePath, minModuleSize, maxModuleSize);
			
			ar.save(analysis, monitor.subtask());
		}
		
		monitor.end();
	}

	private void saveProject(
			String basePath, Analysis analysis, ProgressMonitor monitor) 
			throws FileNotFoundException, IOException {
		
		File path = new File(basePath);
		if (!path.exists())
			path.mkdirs();
		
		Project proj = new Project();
		//inv.setSummary("inv summary");
		//inv.setNotes("inv notes");
		proj.getAnalysis().add(analysis);
		proj.getDataTables().add(analysis.getDataTable());
		proj.getModuleMaps().add(analysis.getModuleMap());
		ProjectResource res = new ProjectResource(path, "project.xml");
		try {
			res.save(proj, monitor.subtask());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}