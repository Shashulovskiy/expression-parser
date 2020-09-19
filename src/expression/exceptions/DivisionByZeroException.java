package expression.exceptions;

public class DivisionByZeroException extends EvaluationException {
    public DivisionByZeroException(String errorMsg) {
        super(errorMsg);
    }
}
