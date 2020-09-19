package expression.parser;

import expression.exceptions.ParserException;

public interface Source {
    boolean hasNext();
    char next();
    ParserException error(final String message);
}
