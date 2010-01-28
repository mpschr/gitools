package org.gitools.analysis.htest.oncozet;

import java.io.File;

import org.gitools.datafilters.ValueParser;
import org.gitools.model.ModuleMap;
import org.gitools.matrix.model.DoubleMatrix;
import org.gitools.persistence.PersistenceException;
import org.gitools.persistence.text.DoubleMatrixTextPersistence;
import org.gitools.persistence.text.ModuleMapTextSimplePersistence;
import org.gitools.stats.test.factory.TestFactory;

import edu.upf.bg.progressmonitor.IProgressMonitor;
import org.gitools.analysis.htest.HtestCommand;

public class OncozCommand extends HtestCommand {

	protected String setsFile;

	public OncozCommand(
			OncozAnalysis analysis,
			String dataFile,
			String setsFile,
			String workdir,
			String fileName,
			String outputFormat,
			boolean resultsByCond) {
		
		super(analysis, dataFile,
				workdir, fileName,
				outputFormat, resultsByCond);

		this.setsFile = setsFile;
	}

	public String getSetsFile() {
		return setsFile;
	}

	public void setSetsFile(String setsFile) {
		this.setsFile = setsFile;
	}

	@Override
	public void run(IProgressMonitor monitor) 
			throws PersistenceException, InterruptedException {
		
		final OncozAnalysis oncozAnalysis = (OncozAnalysis) analysis;
		
		// Load data and modules
		
		monitor.begin("Loading ...", 1);
		monitor.info("Data: " + dataPath);
		monitor.info("Sets: " + setsFile);
		
		DoubleMatrix doubleMatrix = new DoubleMatrix();
		ModuleMap setsMap = new ModuleMap();

		loadDataAndModules(
				doubleMatrix, setsMap,
				dataPath, createValueParser(analysis),
				setsFile,
				oncozAnalysis.getMinSetSize(),
				oncozAnalysis.getMaxSetSize(),
				true, monitor.subtask());

		oncozAnalysis.setDataMatrix(doubleMatrix);
		oncozAnalysis.setSetsMap(setsMap);

		monitor.end();
		
		OncozProcessor processor = new OncozProcessor(oncozAnalysis);
		
		processor.run(monitor);
		
		// Save analysis
		
		save(oncozAnalysis, monitor);
	}

	private void loadDataAndModules(
			DoubleMatrix doubleMatrix, ModuleMap moduleMap,
			String dataFileName, ValueParser valueParser, 
			String setsFileName, int minSetsSize, int maxSetsSize,
			boolean includeNonMappedItems,
			IProgressMonitor monitor)
			throws PersistenceException {
		
		// Load metadata
		
		File resource = new File(dataFileName);
		
		DoubleMatrixTextPersistence dmPersistence = new DoubleMatrixTextPersistence();
		dmPersistence.readMetadata(resource, doubleMatrix, valueParser, monitor);
		
		// Load sets
		
		if (setsFileName != null) {
			File file = new File(setsFileName);
			moduleMap.setTitle(file.getName());
			
			ModuleMapTextSimplePersistence moduleMapTextSimplePersistence = new ModuleMapTextSimplePersistence(file);
			moduleMapTextSimplePersistence.load(
				moduleMap,
				minSetsSize,
				maxSetsSize,
				doubleMatrix.getColumnStrings(),
				includeNonMappedItems,
				monitor);
		}
		else {
			String[] names = doubleMatrix.getColumnStrings();
			moduleMap.setItemNames(names);
			moduleMap.setModuleNames(new String[] {"all"});
			int num = names.length;
			int[][] indices = new int[1][num];
			for (int i = 0; i < num; i++)
				indices[0][i] = i;
			moduleMap.setItemIndices(indices);
		}
		
		// Load data
		
		dmPersistence.readData(
				resource,
				doubleMatrix,
				valueParser,
				moduleMap.getItemsOrder(),
				null,
				monitor);		
	}
}
