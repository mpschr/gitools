package org.gitools.analysis.htest.oncozet;

import java.util.Date;

import org.gitools.model.Analysis;
import org.gitools.matrix.model.ObjectMatrix;
import org.gitools.matrix.model.element.BeanElementAdapter;
import org.gitools.stats.mtc.BenjaminiHochbergFdr;
import org.gitools.stats.test.Test;
import org.gitools.stats.test.factory.TestFactory;
import org.gitools.stats.test.results.CommonResult;
import org.gitools.threads.ThreadManager;
import org.gitools.threads.ThreadQueue;
import org.gitools.threads.ThreadSlot;

import cern.colt.function.DoubleProcedure;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.ObjectFactory1D;
import cern.colt.matrix.ObjectMatrix1D;
import edu.upf.bg.progressmonitor.IProgressMonitor;
import org.gitools.analysis.htest.AbstractProcessor;
import org.gitools.matrix.model.DoubleMatrix;
import org.gitools.matrix.model.IMatrix;
import org.gitools.stats.mtc.MTCFactory;
import org.gitools.stats.mtc.MTC;

public class OncozProcessor extends AbstractProcessor {

	protected static final DoubleProcedure notNaNProc = 
		new DoubleProcedure() {
			@Override
			public boolean apply(double element) {
				return !Double.isNaN(element);
			}
		};
		
	private class RunSlot extends ThreadSlot {
		public DoubleMatrix1D population;
		public Test test;
		public RunSlot(ThreadQueue threadQueue) {
			super(threadQueue);
			population = null;
			test = null;
		}
	}
	
	private OncozAnalysis analysis;
	
	public OncozProcessor(OncozAnalysis analysis) {
		
		this.analysis = analysis;
	}
	
	public void run(IProgressMonitor monitor) throws InterruptedException {
		
		Date startTime = new Date();
	
		TestFactory testFactory = 
			TestFactory.createFactory(analysis.getTestConfig());
		
		//String[] paramNames = testFactory.create().getResultNames();
		//final int numParams = paramNames.length;

		IMatrix dataMatrix = analysis.getDataMatrix();
		if (!(dataMatrix instanceof DoubleMatrix))
			throw new RuntimeException("This processor only works with DoubleMatrix data. "
					+ dataMatrix.getClass().getSimpleName() + " found instead.");

		DoubleMatrix doubleMatrix = (DoubleMatrix) dataMatrix;

		ObjectMatrix1D items = doubleMatrix.getRows().copy();
		
		DoubleMatrix2D data = doubleMatrix.getCells();
		
		ObjectMatrix1D modules = ObjectFactory1D.dense.make(
				analysis.getSetsMap().getModuleNames());
		
		int[][] moduleItemIndices = analysis.getSetsMap().getItemIndices();
		
		final int numColumns = data.columns();
		final int numItems = data.rows();
		final int numModules = modules.size();
		
		monitor.begin("Running oncoz analysis...", numItems * numModules);
	
		Test test = testFactory.create();
		
		final ObjectMatrix resultsMatrix = new ObjectMatrix();
		
		resultsMatrix.setColumns(modules);
		resultsMatrix.setRows(items);
		resultsMatrix.makeData();
		
		resultsMatrix.setCellAdapter(
				new BeanElementAdapter(test.getResultClass()));
		
		int numProcs = ThreadManager.getNumThreads();
		
		ThreadQueue threadQueue = new ThreadQueue(numProcs);
		
		for (int i = 0; i < numProcs; i++)
			try {
				threadQueue.put(new RunSlot(threadQueue));
			} catch (InterruptedException e) {
				monitor.debug("InterruptedException while initializing run queue: " + e.getLocalizedMessage());
			}
		
		/* Test analysis */
		
		for (int moduleIndex = 0; moduleIndex < numModules; moduleIndex++) {
		
			final int moduleIdx = moduleIndex;
			
			final String moduleName = modules.getQuick(moduleIndex).toString();
			
			final int[] itemIndices = moduleItemIndices[moduleIndex];
			
			final IProgressMonitor condMonitor = monitor.subtask();
			
			condMonitor.begin("Module " + moduleName + "...", numItems);
			
			final DoubleMatrix2D pop2d = data 
				.viewSelection(null, itemIndices);
			
			DoubleMatrix1D population = pop2d 
				.like1D(numColumns * numItems);
				//FIXME !!!!
				//.viewSelection(notNaNProc);
			
			int k = 0;
			for (int i = 0; i < pop2d.rows(); i++)
				for (int j = 0; j < pop2d.columns(); j++)
					population.setQuick(k++,
							pop2d.getQuick(i, j));
			
			population = population.viewSelection(notNaNProc); //FIXME
			
			for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {

				final int itemIdx = itemIndex;
				
				final String itemName = items.getQuick(itemIndex).toString();
				
				final DoubleMatrix1D itemValues = data.viewRow(itemIndex);
				
				final RunSlot slot = (RunSlot) threadQueue.take();
				
				if (slot.population != population) {
					slot.population = population;
					slot.test = testFactory.create();
					slot.test.processPopulation(moduleName, population);
				}

				slot.execute(new Runnable() {
					@Override public void run() {
						//FIXME: It will be fixed, itemValues has to be filtered 
						//for the group and item indices related to all population.
						
						CommonResult result = slot.test.processTest(
								moduleName, itemValues,
								itemName, itemIndices);
						
						resultsMatrix.setCell(itemIdx, moduleIdx, result);
					}
				});
				
				condMonitor.worked(1);
				monitor.worked(1);
			}
			
			condMonitor.end();
		}
		
		ThreadManager.shutdown(monitor);
		
		/* Multiple test correction */
		
		MTC mtc =
				MTCFactory.createFromName(analysis.getMtc());

		multipleTestCorrection(
				resultsMatrix,
				mtc,
				monitor.subtask());
		
		analysis.setStartTime(startTime);
		analysis.setElapsedTime(new Date().getTime() - startTime.getTime());
		
		analysis.setResultsMatrix(resultsMatrix);
		
		monitor.end();
	}
}
