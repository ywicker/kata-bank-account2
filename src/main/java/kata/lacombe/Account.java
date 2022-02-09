package kata.lacombe;

import kata.lacombe.providers.AccountParameterProvider;
import kata.lacombe.providers.DateProvider;

import java.util.List;

import static kata.lacombe.Amount.createAmount;
import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;

public class Account {
    private final Operations operations;

    public Account(final DateProvider dateProvider, final AccountParameterProvider accountParameterProvider) {
        operations = new Operations(dateProvider, accountParameterProvider);
    }

    public void deposit(final int value) throws Exception {
        operations.addOperation(DEPOSIT, createAmount(value));
    }

    public void withdrawal(final int value) throws Exception {
        operations.addOperation(WITHDRAWAL, createAmount(value));
    }

    public int getCurrentBalance() {
        return operations.currentBalance().value();
    }

    public List<Operation> getOperationHistory() {
        return operations.getOperationList();
    }
}
