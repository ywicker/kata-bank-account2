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
        operations.add(DEPOSIT, value);
    }

    public void withdrawal(final int value) {
        operations.add(WITHDRAWAL, value);
    }

    public int getBalance() {
        return operations.actualBalance();
    }

    public List<Operation> getOperationHistory() {
        return operations.getOperationList();
    }
}
