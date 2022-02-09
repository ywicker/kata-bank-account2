package kata.lacombe;

public record Balance(int value) {
    public Balance newBalance(int amountToApply) {
        return new Balance(value + amountToApply);
    }
}
