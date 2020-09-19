package expression.parser;

import expression.AbstractExpression;
import expression.exceptions.OverflowException;
import expression.adapters.NumberAdapter;
import expression.exceptions.ParserException;

public interface Parser<T> {
    AbstractExpression<T> parse(String expression) throws OverflowException, ParserException;
}
