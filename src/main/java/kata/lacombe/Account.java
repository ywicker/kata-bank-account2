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

    public void deposit(final int amount) {
        assert Integer.signum(amount) == 1;
        balance += amount;
    }

    public void withdrawal(final int amount) {
        assert amount > 0;
        assert Integer.signum(balance + allowOverdraft - amount) >= 0;
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }
}
