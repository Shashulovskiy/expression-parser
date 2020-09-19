package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public interface AbstractExpression<T> {
    T evaluate(T x, T y, T z) throws OverflowException, DivisionByZeroException;
}
