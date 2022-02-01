package kata.lacombe;

public class Amount {
    private int value;

    private Amount(final int value) {
        this.value = value;
    }

    public static Amount createAmount(final int value) {
        assert Integer.signum(value) == 1;
        return new Amount(value);
    }

    public int getValue() {
        return value;
    }
}
