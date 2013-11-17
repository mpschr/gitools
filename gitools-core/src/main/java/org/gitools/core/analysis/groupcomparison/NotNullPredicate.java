package org.gitools.core.analysis.groupcomparison;

import org.gitools.core.matrix.model.IMatrixPosition;
import org.gitools.core.matrix.model.IMatrixPredicate;

public class NotNullPredicate<T> implements IMatrixPredicate<T> {

    @Override
    public boolean apply(T value, IMatrixPosition position) {
        return value != null;
    }
}
