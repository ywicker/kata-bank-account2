package kata.lacombe;

public class Account {
    private int balance;
    private int allowOverdraft;

    public Account() {
    }

    public Account(final int balance) {
        this.balance = balance;
    }

    public Account(final int balance,final int allowOverdraft) {
        this.balance = balance;
        this.allowOverdraft = allowOverdraft;
    }

    public void deposit(final int value) {
        Amount amount = Amount.createAmount(value);
        balance += amount.getValue();
    }

    public void withdrawal(final int value) {
        Amount amount = Amount.createAmount(value);
        assert Integer.signum(balance + allowOverdraft - amount.getValue()) >= 0;
        balance -= amount.getValue();
    }

    public int getBalance() {
        return balance;
    }
}
