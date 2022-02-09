package kata.lacombe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static kata.lacombe.PositiveAmount.createPositiveAmount;

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

    public void addOperation(OperationType type, int value) {
        var positiveAmount = createPositiveAmount(value);
        var currentBalance = currentBalance();
        var balanceAfterOperation = currentBalance.newBalance(type.amountToApply(positiveAmount));

        var operationDate = dateProvider.getDate();
        var minimumAuthorizedBalance = accountParameterProvider.getMinimumAuthorizedBalance();

        assert isAfterOrEqualsLastOperationDate(operationDate);
        assert balanceAfterOperation.value() >= minimumAuthorizedBalance.value();

        var operation = new Operation(operationDate, type, positiveAmount, balanceAfterOperation);
        operationList.add(operation);
    }

    public Balance currentBalance() {
        if(operationList.isEmpty()) {
            return accountParameterProvider.getInitialBalance();
        }
        return operationList.peekLast().balanceAfterOperation();
    }

    private boolean isAfterOrEqualsLastOperationDate(LocalDateTime newOperationDate) {
        if(operationList.isEmpty()) {
            return true;
        }
        return !newOperationDate.isBefore(operationList.peekLast().date());
    }
}
