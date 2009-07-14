package org.gitools.model.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.gitools.model.matrix.IMatrix;
import org.gitools.model.matrix.Matrix;
import org.gitools.model.xml.MatrixXmlElement;
import org.gitools.persistence.Extensions;
import org.gitools.persistence.PersistenceManager;

public class MatrixXmlAdapter extends XmlAdapter<MatrixXmlElement, IMatrix> {

	@Override
	public MatrixXmlElement marshal(IMatrix v) throws Exception {
		Matrix matrix = (Matrix) v;
		return new MatrixXmlElement(Extensions.getEntityExtension(v
				.getClass()), matrix.getResource());
	}

	@Override
	public IMatrix unmarshal(MatrixXmlElement v) throws Exception {
		IMatrix matrix = (IMatrix) PersistenceManager.load( v.getReference(), (v.getType()));
		return matrix;
	}
}