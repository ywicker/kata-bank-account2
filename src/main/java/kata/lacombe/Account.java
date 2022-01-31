package kata.lacombe;

public class Account {
    private int balance;

    public Account() {
    }

    public Account(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        assert Integer.signum(amount) ==1;
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }
}
