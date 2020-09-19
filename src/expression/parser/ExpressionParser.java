package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.adapters.*;

import java.util.List;

public class ExpressionParser<T> implements Parser<T> {

    protected NumberAdapter<T> type;

    public static class BinaryOperation<T> implements AbstractExpression<T> {

        private final AbstractExpression<T> firstOperand;
        private final AbstractExpression<T> secondOperand;
        protected PerformBinaryOperation<T> operation;


        protected BinaryOperation(AbstractExpression<T> first,
                                  AbstractExpression<T> second,
                                  PerformBinaryOperation<T> operation) {
            firstOperand = first;
            secondOperand = second;
            this.operation = operation;
        }

        public T evaluate(T x, T y, T z) throws OverflowException, DivisionByZeroException {
            return operation.perform(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
        }
    }

    public static class UnaryOperation<T> implements AbstractExpression<T> {
        private final AbstractExpression<T> firstOperand;
        protected PerformUnaryOperation<T> operation;


        public UnaryOperation(AbstractExpression<T> first,
                              PerformUnaryOperation<T> operation) {
            firstOperand = first;
            this.operation = operation;
        }

        public T evaluate(T x, T y, T z) throws OverflowException, DivisionByZeroException {
            return operation.perform(firstOperand.evaluate(x, y, z));
        }
    }

    public static class Const<T> implements AbstractExpression<T> {
        private final T constantValue;

        public Const(T constantValue) {
            this.constantValue = constantValue;
        }

        @Override
        public T evaluate(Object x, Object y, Object z) {
            return constantValue;
        }
    }

    public static class Variable<T> implements AbstractExpression<T> {
        private final String variable;

        public Variable(String variable) {
            this.variable = variable;
        }

        @Override
        public T evaluate(T x, T y, T z) {
            return variable.equals("x") ? x : (variable.equals("y") ? y : z);
        }
    }

    private class BinaryOperationCreator {

        String operationSign;
        PerformBinaryOperation<T> op;

        public BinaryOperationCreator(String operationSign, PerformBinaryOperation<T> op) {
            this.operationSign = operationSign;
            this.op = op;
        }

        public BinaryOperation<T> buildBinaryOperation(AbstractExpression<T> first, AbstractExpression<T> second) {
            return new BinaryOperation<>(first, second, op);
        }

        public char getFirstChar() {
            return operationSign.charAt(0);
        }

        public String getRest() {
            return operationSign.substring(1);
        }
    }

    public AbstractExpression<T> parse(String source) throws ParserException {
        return new InnerParser(new StringSource(source)).parse(0);
    }

    private final static List<String> variableNames = List.of(
            "x", "y", "z"
    );

    public ExpressionParser(NumberAdapter<T> type) {
        this.type = type;
    }

    private class InnerParser extends BaseParser {

        private final List<List<BinaryOperationCreator>> binaryOperations = List.of(
                List.of(new BinaryOperationCreator("+", type::add), new BinaryOperationCreator("-", type::subtract)),
                List.of(new BinaryOperationCreator("*",  type::multiply), new BinaryOperationCreator("/", type::divide))
        );


        private InnerParser(Source source) {
            super(source);
            nextChar();
        }

        private AbstractExpression<T> parseWrapper(int level) throws ParserException {
            if (level == binaryOperations.size() - 1) {
                return parsePrimal();
            }
            return parse(level + 1);
        }

        private AbstractExpression<T> parse(int level) throws ParserException {
            AbstractExpression<T> prefix = parseWrapper(level);
            boolean found;
            while (true) {
                skipWhitespace();
                found = false;
                for (BinaryOperationCreator operand : binaryOperations.get(level)) {
                    if (test(operand.getFirstChar())) {
                        expect(operand.getRest());
                        prefix = operand.buildBinaryOperation(prefix, parseWrapper(level));
                        found = true;
                    }
                }
                skipWhitespace();
                if (!found) {
                    return prefix;
                }
            }
        }

        private AbstractExpression<T> parsePrimal() throws ParserException {
            skipWhitespace();
            String number;
            if (test('-')) {
                if ((number = testNumber()) != null) {
                    return new Const<>(type.parse("-" + number));
                } else {
                    return new UnaryOperation<>(parsePrimal(), type::negate);
                }
            } else if ((number = testNumber()) != null) {
                return new Const<>(type.parse(number));
            } else if (test('(')) {
                AbstractExpression<T> result = parse(0);
                expect(')');
                return result;
            } else {
                for (String name : variableNames) {
                    if (test(name.charAt(0))) {
                        expect(name.substring(1));
                        return new Variable<>(name);
                    }
                }
            }
            return null;
        }
    }
}
