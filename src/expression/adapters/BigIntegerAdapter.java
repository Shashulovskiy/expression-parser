package expression.adapters;

import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class BigIntegerAdapter implements NumberAdapter<BigInteger> {
    @Override
    public BigInteger add(BigInteger first, BigInteger second) {
        return first.add(second);
    }

    @Override
    public BigInteger subtract(BigInteger first, BigInteger second) {
        return first.subtract(second);
    }

    @Override
    public BigInteger multiply(BigInteger first, BigInteger second) {
        return first.multiply(second);
    }

    @Override
    public BigInteger divide(BigInteger first, BigInteger second) throws DivisionByZeroException {
        if (second.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException(String.format(
                    "Dvision by zero: %d / %d",
                    first,
                    second));
        }
        return first.divide(second);
    }

    @Override
    public BigInteger negate(BigInteger first) {
        return first.negate();
    }

    @Override
    public BigInteger parse(String number) {
        return new BigInteger(number);
    }
}
