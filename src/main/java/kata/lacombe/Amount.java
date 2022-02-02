package kata.lacombe;


import java.util.Objects;

public class Amount {
    private final int value;

    public Amount(int value) {
        this.value = value;
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

    public Amount add(Amount amount) {
        return new Amount(value + amount.value);
    }
    public boolean isNotLessThan(Amount amount) {
        return !(this.value < amount.value);
    }
    public Amount opposite() {
        return new Amount(-this.value());
    }
}