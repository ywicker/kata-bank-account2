package kata.lacombe;

public class Account {
    private int balance;

    public Account() {
    }

    public Account(final int balance) {
        this.balance = balance;
    }

    public void deposit(final int amount) {
        assert Integer.signum(amount) == 1;
        balance += amount;
    }

    public void withdrawal(final int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }
}
