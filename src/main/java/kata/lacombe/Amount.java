package kata.lacombe;

public record Amount(int value) {
    public Amount add(Amount amount) {
        return new Amount(value + amount.value);
    }
    public Amount opposite() {
        return new Amount(-value);
    }
}