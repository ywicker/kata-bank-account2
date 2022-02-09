package kata.lacombe;

import kata.lacombe.errors.AmountValueException;

import java.util.Objects;

public final class Amount {
    private final int value;

    private Amount(int value) {
        this.value = value;
    }

    public static Amount createAmount(int value) throws AmountValueException {
        if(Integer.signum(value) != 1) {
            throw new AmountValueException();
        }
        return new Amount(value);
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Amount) obj;
        return this.value == that.value;
    }
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    @Override
    public String toString() {
        return "Amount[" +
                "value=" + value + ']';
    }
}