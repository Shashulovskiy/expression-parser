package expression.adapters;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public interface NumberAdapter<T> {

    T add(T first, T second) throws OverflowException;
    T subtract(T first, T second) throws OverflowException;
    T multiply(T first, T second) throws OverflowException;
    T divide(T first, T second) throws OverflowException, DivisionByZeroException;
    T negate(T first) throws OverflowException;

    T parse(String number);

}
