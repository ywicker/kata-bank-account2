package kata.lacombe;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final Balance balance;
    private final List<Operation> operationList;

    public Account() {
        balance = new Balance();
        operationList = new ArrayList<>();
    }

    public Account(final int balanceValue) {
        this.balance = new Balance(balanceValue);
        operationList = new ArrayList<>();
    }

    public Account(final int balance,final int allowOverdraft) {
        this.balance = new Balance(balance, allowOverdraft);
        operationList = new ArrayList<>();
    }

    public void deposit(final int value) {
        var amount = Amount.createAmount(value);
        operationList.add(new Operation());
        balance.add(amount);
    }

    public void withdrawal(final int value) {
        var amount = Amount.createAmount(value);
        operationList.add(new Operation());
        balance.subtract(amount);
    }

    public int getBalance() {
        return balance.getValue();
    }

    public List<Operation> getOperationHistory() {
        return operationList;
    }
}
