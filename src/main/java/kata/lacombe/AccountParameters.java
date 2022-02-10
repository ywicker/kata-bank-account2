package kata.lacombe;

public record AccountParameters(Balance initialBalance, Balance minimumAuthorizedBalance) {
    public static AccountParameters createAccountParameters(int initialBalanceValue, int minimumAuthorizedBalanceValue) {
        var initialBalance = new Balance(initialBalanceValue);
        var minimumAuthorizedBalance = new Balance(minimumAuthorizedBalanceValue);
        return new AccountParameters(initialBalance, minimumAuthorizedBalance);
    }
}
