package kata.lacombe;

public class NonNegativeAmount extends Amount {
    private NonNegativeAmount(int value) {
        super(value);
    }
    public static NonNegativeAmount createNonNegativeAmount(final int value) {
        assert Integer.signum(value) != -1;
        return new NonNegativeAmount(value);
    }
}
