package kata.lacombe;

public class Account {
    private Balance balance;

    public Account() {
        balance = new Balance();
    }

    public Account(final int balanceValue) {
        this.balance = new Balance(balanceValue);
    }

    public Account(final int balance,final int allowOverdraft) {
        this.balance = new Balance(balance, allowOverdraft);
    }

    public void deposit(final int value) {
        Amount amount = Amount.createAmount(value);
        balance.add(amount);
    }

    public void withdrawal(final int value) {
        Amount amount = Amount.createAmount(value);
        balance.subtract(amount);
    }

    public int getBalance() {
        return balance.getValue();
    }
}
