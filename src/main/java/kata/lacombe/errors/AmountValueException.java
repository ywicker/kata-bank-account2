package kata.lacombe.errors;

public class AmountValueException extends Exception {
    public AmountValueException() {
        super("The amount value cannot be null or negative");
    }
}
