package kata.lacombe;

import kata.lacombe.errors.AmountValueException;
import kata.lacombe.errors.AuthorizedOverdraftExceededException;
import kata.lacombe.errors.OperationDateException;
import kata.lacombe.providers.DateProvider;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

import static kata.lacombe.AccountParameters.createAccountParameters;
import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;

public class Account {
    private final Operations operations;
    private final DateProvider dateProvider;
    private final AccountParameters accountParameters;

    public Account(final DateProvider dateProvider, final AccountParameters accountParameters) {
        this.operations = new Operations();
        this.dateProvider = dateProvider;
        this.accountParameters = accountParameters;
    }
    public Account(final DateProvider dateProvider) {
        this.operations = new Operations();
        this.dateProvider = dateProvider;
        this.accountParameters = createAccountParameters(0, 0);
    }

    public void deposit(final int value) throws Exception {
        applyOperation(DEPOSIT, new Amount(value));
    }

    public void withdrawal(final int value) throws Exception {
        applyOperation(WITHDRAWAL, new Amount(value));
    }

    public int getCurrentBalance() {
        return currentBalance().value();
    }

    public List<Operation> getOperationHistory() {
        return operations.getOperationList();
    }

    private void applyOperation(@NotNull OperationType type, Amount amount)
            throws AuthorizedOverdraftExceededException, OperationDateException, AmountValueException {

        amountValueCannotBeNullOrNegative(amount);

        var operationDate = dateProvider.getDate();
        operationDateCannotBeOlderThanLastOperationDate(operationDate);

        var balanceAfterOperation = currentBalance().add(type.amountToApply(amount));
        balanceCannotBeLowerThanMinimumAuthorizedBalance(balanceAfterOperation);

        var operation = new Operation(operationDate,
                type,
                amount,
                balanceAfterOperation);
        operations.addOperation(operation);
    }

    private Amount currentBalance() {
        return operations.lastOperation().map(Operation::balanceAfterOperation)
                .orElseGet(accountParameters::initialBalance);
    }

    private void operationDateCannotBeOlderThanLastOperationDate(@NotNull LocalDateTime operationDate) throws OperationDateException {
        var lastOperationDate = operations.lastOperationDate();
        if (operationDate.isBefore(lastOperationDate)) {
            throw new OperationDateException();
        }
    }
    private void balanceCannotBeLowerThanMinimumAuthorizedBalance(@NotNull Amount newBalance) throws AuthorizedOverdraftExceededException {
        var minimumAuthorizedBalance = accountParameters.minimumAuthorizedBalance();
        if (newBalance.value() < minimumAuthorizedBalance.value()) {
            throw new AuthorizedOverdraftExceededException();
        }
    }
    private void amountValueCannotBeNullOrNegative(@NotNull Amount amount) throws AmountValueException {
        if(Integer.signum(amount.value()) != 1) {
            throw new AmountValueException();
        }
    }
}
