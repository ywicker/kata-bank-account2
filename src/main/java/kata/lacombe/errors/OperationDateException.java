package kata.lacombe.errors;

public class OperationDateException extends Exception {
    public OperationDateException() {
        super("The date of the new operation cannot be earlier than the date of the last operation");
    }
}
