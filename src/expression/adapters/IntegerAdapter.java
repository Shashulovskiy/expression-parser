package expression.adapters;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class IntegerAdapter implements NumberAdapter<Integer> {
    @Override
    public Integer add(Integer first, Integer second) throws OverflowException {
        int sum = first + second;
        if (first > 0 && second > 0 && sum <= 0 ||
                first < 0 && second < 0 && sum >= 0) {
            throw new OverflowException(String.format(
                    "Integer overflow: %d + %d",
                    first,
                    second));

        }
        return sum;
    }

    @Override
    public Integer subtract(Integer first, Integer second) throws OverflowException {
        int sub = first - second;
        if (((first ^ second) & (first ^ sub)) < 0) {
            throw new OverflowException(String.format(
                    "Integer overflow: %d - %d",
                    first,
                    second));

        }
        return sub;
    }

    @Override
    public Integer multiply(Integer first, Integer second) throws OverflowException {
        int mul = first * second;
        int absoluteFirstExpressionResult =
                first > 0 ? first : -first;
        int absoluteSecondExpressionResult =
                second > 0 ? second : -second;

        if (((absoluteFirstExpressionResult | absoluteSecondExpressionResult) >>> 15 != 0)) {
            if (((second != 0) && (mul / second != first)) ||
                    (first == Integer.MIN_VALUE && second == -1)) {
                throw new OverflowException(String.format(
                        "Integer overflow: %d * %d",
                        first,
                        second));
            }
        }
        return mul;
    }

    @Override
    public Integer divide(Integer first, Integer second) throws OverflowException, DivisionByZeroException {
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new OverflowException(String.format(
                    "Integer overflow: %d / %d",
                    first,
                    second));
        }

        if (second == 0) {
            throw new DivisionByZeroException(String.format(
                    "Dvision by zero: %d / %d",
                    first,
                    second));
        }
        return first / second;
    }

    @Override
    public Integer negate(Integer first) throws OverflowException {
        if (first == Integer.MIN_VALUE) {
            throw new OverflowException(String.format(
                    "Integer overflow: -%d",
                    first));
        }
        return -first;
    }

    @Override
    public Integer parse(String number) {
        return Integer.parseInt(number);
    }
}
