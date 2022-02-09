package kata.lacombe;

import kata.lacombe.errors.AuthorizedOverdraftExceededException;
import kata.lacombe.errors.OperationDateException;
import kata.lacombe.providers.AccountParameterProvider;
import kata.lacombe.providers.DateProvider;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


public class Operations {
    private final LinkedList<Operation> operationList;

    private final DateProvider dateProvider;
    private final AccountParameterProvider accountParameterProvider;

    public Operations(DateProvider dateProvider, AccountParameterProvider accountParameterProvider) {
        this.dateProvider = dateProvider;
        this.accountParameterProvider = accountParameterProvider;
        this.operationList = new LinkedList<>();
    }

    public List<Operation> getOperationList() {
        return List.copyOf(operationList);
    }

    public void addOperation(OperationType type, Amount amount) throws AuthorizedOverdraftExceededException, OperationDateException {
        var operationDate = dateProvider.getDate();
        var balanceAfterOperation = currentBalance().newBalance(type.amountToApply(amount));

        operationDateCannotBeOlderThanLastOperationDate(operationDate);
        balanceCannotBeLowerThanMinimumAuthorizedBalance(balanceAfterOperation);

        var operation = new Operation(operationDate, type, amount, balanceAfterOperation);
        operationList.add(operation);
    }

    public Balance currentBalance() {
        if(operationList.isEmpty()) {
            return accountParameterProvider.getInitialBalance();
        }
        return operationList.peekLast().balanceAfterOperation();
    }

    private void operationDateCannotBeOlderThanLastOperationDate(LocalDateTime operationDate) throws OperationDateException {
        var lastOperationDate = lastOperationDate();
        if (operationDate.isBefore(lastOperationDate)) {
            throw new OperationDateException();
        }
    }

    private LocalDateTime lastOperationDate() {
        if(operationList.isEmpty()) {
            return LocalDateTime.MIN;
        }
        return operationList.peekLast().date();
    }

    private void balanceCannotBeLowerThanMinimumAuthorizedBalance(Balance newBalance) throws AuthorizedOverdraftExceededException {
        var minimumAuthorizedBalance = accountParameterProvider.getMinimumAuthorizedBalance();
        if (newBalance.value() < minimumAuthorizedBalance.value()) {
            throw new AuthorizedOverdraftExceededException();
        }
    }
}
