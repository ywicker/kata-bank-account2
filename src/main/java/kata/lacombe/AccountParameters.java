package kata.lacombe;

public record AccountParameters(Amount initialBalance, Amount minimumAuthorizedBalance) {
    public static AccountParameters createAccountParameters(int initialBalanceValue, int minimumAuthorizedBalanceValue) {
        var initialBalance = new Amount(initialBalanceValue);
        var minimumAuthorizedBalance = new Amount(minimumAuthorizedBalanceValue);
        return new AccountParameters(initialBalance, minimumAuthorizedBalance);
    }
}
