package org.gitools.cli.htest;

import org.kohsuke.args4j.Option;

import edu.upf.bg.progressmonitor.NullProgressMonitor;
import edu.upf.bg.progressmonitor.IProgressMonitor;
import edu.upf.bg.progressmonitor.StreamProgressMonitor;
import edu.upf.bg.tools.exception.ToolException;
import edu.upf.bg.tools.exception.ToolValidationException;
import org.gitools.analysis.htest.enrichment.EnrichmentAnalysis;
import org.gitools.analysis.htest.enrichment.ZCalcCommand;
import org.gitools.model.ToolConfig;
import org.gitools.stats.test.factory.TestFactory;
import org.gitools.threads.ThreadManager;

public class ZcalcCliTool extends HtestCliTool {

	public static class ZcalcArguments extends HtestArguments {
		@Option(name = "-c", aliases = {"-class", "-m", "-modules"}, metaVar = "<file>",
				usage = "File with mappings between items and modules.")
		public String modulesFile;

		@Option(name = "-min", aliases = "-min-mod-size", metaVar = "<min>",
				usage = "Discard all modules that have\nless items than <min> (default: 20)")
		private int minModuleSize = 20;

		@Option(name = "-max", aliases = "-max-mod-size", metaVar = "<max>",
				usage = "Discard all modules that have\nmore items than <max> (default: no limit)")
		private int maxModuleSize = Integer.MAX_VALUE;

		@Option(name = "-omi", aliases = "-only-mapped-items",
				usage = "Consider only items having a mapping for the background.\n" +
						"(default: all items in data file will be considered)")
		private boolean discardNonMappedRows = false;
	}
	
	@Override
	public void validate(Object argsObject) throws ToolException {
		
		super.validate(argsObject);
		
		ZcalcArguments args = (ZcalcArguments) argsObject;

		testConfig = TestFactory.createToolConfig(
				ToolConfig.ZETCALC, args.testName);

		if (testConfig == null)
			throw new ToolValidationException("Unknown test: " + args.testName);

		if (args.modulesFile == null)
        	throw new ToolValidationException("Groups file has to be specified.");
        
        if (args.minModuleSize < 1)
        	args.minModuleSize = 1;
        if (args.maxModuleSize < args.minModuleSize)
        	args.maxModuleSize = args.minModuleSize;
	}
	
	@Override
	public void run(Object argsObject) throws ToolException {
		
		ZcalcArguments args = (ZcalcArguments) argsObject;

		EnrichmentAnalysis analysis = new EnrichmentAnalysis();
		prepareAnalysis(analysis, args);

		analysis.setMinModuleSize(args.minModuleSize);
		analysis.setMaxModuleSize(args.maxModuleSize);

		analysis.setDiscardNonMappedRows(args.discardNonMappedRows);
        
		ZCalcCommand cmd = new ZCalcCommand(
        		analysis, args.dataFile, args.modulesFile,
				args.workdir, args.analysisName,
				args.outputFormat, true);
        
        IProgressMonitor monitor = !args.quiet ? 
			new StreamProgressMonitor(System.out, args.verbose, args.debug)
			: new NullProgressMonitor();

		ThreadManager.setNumThreads(args.maxProcs);

        try {
			cmd.run(monitor);
		} catch (Exception e) {
			throw new ToolException(e);
		}
		finally {
			ThreadManager.shutdown(monitor);
		}
	}
}