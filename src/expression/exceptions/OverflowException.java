package expression.exceptions;

public class OverflowException extends EvaluationException {
    public OverflowException(String errorMsg) {
        super(errorMsg);
    }
}
