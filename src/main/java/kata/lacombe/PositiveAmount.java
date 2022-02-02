package kata.lacombe;

public class PositiveAmount extends Amount {

    private PositiveAmount(int value) {
        super(value);
    }

    public static PositiveAmount createPositiveAmount(final int value) {
        assert Integer.signum(value) == 1;
        return new PositiveAmount(value);
    }
}
