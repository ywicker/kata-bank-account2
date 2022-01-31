package kata.lacombe;

public class Account {
    private int balance;

    public Account() {
    }

    public Account(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }
}
