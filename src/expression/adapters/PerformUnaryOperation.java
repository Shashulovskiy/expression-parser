package expression.adapters;

import expression.exceptions.OverflowException;

public interface PerformUnaryOperation<T> {
    T perform(T first) throws OverflowException;
}
