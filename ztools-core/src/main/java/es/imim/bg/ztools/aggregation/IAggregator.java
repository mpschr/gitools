package es.imim.bg.ztools.aggregation;

import cern.colt.matrix.DoubleMatrix1D;


public interface IAggregator {
	
	double aggregate(DoubleMatrix1D data);
}
