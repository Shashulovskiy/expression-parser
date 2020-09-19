package expression.parser;

import expression.exceptions.ParserException;

public class BaseParser {
    private final Source source;
    protected char ch;

    protected BaseParser(final Source source) {
        this.source = source;
    }

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : '\0';
    }

    protected boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void expect(final char c) throws ParserException {
        if (ch != c) {
            throw error("Expected '" + c + "', found '" + ch + "'");
        }
        nextChar();
    }

    protected void expect(final String value) throws ParserException {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected ParserException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    protected String testNumber() {
        if (between('0', '9')) {
            StringBuilder result = new StringBuilder();
            result.append(ch);
            nextChar();
            while (between('0', '9')) {
                result.append(ch);
                nextChar();
            }
            skipWhitespace();
            return result.toString();
        }
        return null;
    }
}
