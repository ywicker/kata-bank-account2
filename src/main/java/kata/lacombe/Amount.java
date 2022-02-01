package kata.lacombe;

public record Amount(int value) {

    public static Amount createAmount(final int value) {
        assert Integer.signum(value) == 1;
        return new Amount(value);
    }

    public int getValue() {
        return value;
    }
}
