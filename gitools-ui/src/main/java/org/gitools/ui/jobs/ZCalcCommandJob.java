package org.gitools.ui.jobs;

import java.io.File;

import org.gitools.ui.platform.AppFrame;

import edu.upf.bg.progressmonitor.IProgressMonitor;
import org.gitools.analysis.htest.enrichment.ZCalcCommand;

public class ZCalcCommandJob implements Job {
	
	ZCalcCommand command;
	IProgressMonitor monitor;
	File analysisPath;
	
	public ZCalcCommandJob(
			ZCalcCommand command, 
			IProgressMonitor monitor, 
			File analysisPath) {
		
		this.command = command;
		this.monitor = monitor;
		this.analysisPath = analysisPath;
	}

	@Override
	public void run() {
		try {
	        AppFrame.instance()
	        	.setStatusText("Executing analysis...");
	        
			command.run(monitor);

			new OpenAnalysisJob(analysisPath, monitor).run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}