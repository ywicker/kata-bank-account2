package kata.lacombe;

import java.util.List;

import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;

public class Account {
    private final Operations operations;

    public Account(final DateProvider dateProvider, final AccountParameterProvider accountParameterProvider) {
        operations = new Operations(dateProvider, accountParameterProvider);
    }

    public void deposit(final int value) {
        operations.addOperation(DEPOSIT, value);
    }

    public void withdrawal(final int value) {
        operations.addOperation(WITHDRAWAL, value);
    }

    public int getCurrentBalance() {
        return operations.currentBalance().value();
    }

    public List<Operation> getOperationHistory() {
        return operations.getOperationList();
    }
}
