package kata.lacombe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;

public class Account {
    private final Balance balance;
    private final List<Operation> operationList;
    private final DateProvider dateProvider;

    public Account(final DateProvider dateProvider) {
        this.dateProvider = dateProvider;
        balance = new Balance();
        operationList = new ArrayList<>();
    }

    public Account(final DateProvider dateProvider, final int balanceValue) {
        this.dateProvider = dateProvider;
        this.balance = new Balance(balanceValue);
        operationList = new ArrayList<>();
    }

    public Account(final DateProvider dateProvider, final int balance,final int allowOverdraft) {
        this.dateProvider = dateProvider;
        this.balance = new Balance(balance, allowOverdraft);
        operationList = new ArrayList<>();
    }

    public void deposit(final int value) {
        var amount = Amount.createAmount(value);
        operationList.add(new Operation(dateProvider.getDate(), DEPOSIT, amount));
        balance.add(amount);
    }

    public void withdrawal(final int value) {
        var amount = Amount.createAmount(value);
        operationList.add(new Operation(dateProvider.getDate(), WITHDRAWAL, amount));
        balance.subtract(amount);
    }

    public int getBalance() {
        return balance.getValue();
    }

    public List<Operation> getOperationHistory() {
        return operationList;
    }
}
