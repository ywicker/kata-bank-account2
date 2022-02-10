package kata.lacombe;

import kata.lacombe.providers.DateProvider;

import java.util.List;

import static kata.lacombe.AccountParameters.createAccountParameters;
import static kata.lacombe.Amount.createAmount;
import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;

public class Account {
    private final Operations operations;

    public Account(final DateProvider dateProvider, final AccountParameters accountParameters) {
        operations = new Operations(dateProvider, accountParameters);
    }
    public Account(final DateProvider dateProvider) {
        var defaultAccountParameter = createAccountParameters(0, 0);
        operations = new Operations(dateProvider, defaultAccountParameter);
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
