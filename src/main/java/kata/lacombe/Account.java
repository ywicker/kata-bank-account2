package kata.lacombe;

import java.util.List;

import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;
import static kata.lacombe.PositiveAmount.createAmount;

public class Account {
    private final Balance balance;
    private final Operations operations;
    private final DateProvider dateProvider;

    public Account(final DateProvider dateProvider) {
        this.dateProvider = dateProvider;
        balance = new Balance();
        operations = new Operations();
    }

    public Account(final DateProvider dateProvider, final int balanceValue) {
        this.dateProvider = dateProvider;
        this.balance = new Balance(balanceValue);
        operations = new Operations();
    }

    public Account(final DateProvider dateProvider, final int balance,final int allowOverdraft) {
        this.dateProvider = dateProvider;
        this.balance = new Balance(balance, allowOverdraft);
        operations = new Operations();
    }

    public void deposit(final int value) {
        var amount = createAmount(value);
        balance.add(amount);
        operations.add(new Operation(dateProvider.getDate(), DEPOSIT, amount, balance));
    }

    public void withdrawal(final int value) {
        var amount = createAmount(value);
        balance.subtract(amount);
        operations.add(new Operation(dateProvider.getDate(), WITHDRAWAL, amount, balance));
    }

    public int getBalance() {
        return balance.getValue();
    }

    public List<Operation> getOperationHistory() {
        return operations.getOperationList();
    }
}
