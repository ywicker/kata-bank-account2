package kata.lacombe;

import jdk.dynalink.Operation;

import java.util.List;

public class Account {
    private final Balance balance;

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

    public List<Operation> getOperationHistory() {
        return List.of();
    }
}
