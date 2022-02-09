package kata.lacombe.errors;

public class AuthorizedOverdraftExceededException extends Exception {
    public AuthorizedOverdraftExceededException() {
        super("The balance cannot be less than the minimum balance allowed");
    }
}
