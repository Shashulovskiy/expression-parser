package expression.adapters;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public interface PerformBinaryOperation<T> {
    T perform(T first, T second) throws OverflowException, DivisionByZeroException;
}
