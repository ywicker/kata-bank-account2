package kata.lacombe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static kata.lacombe.PositiveAmount.createPositiveAmount;

public class Operations {
    private final List<Operation> operationList;

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
        var balanceAfterOperation = currentBalance.add(type.amountToApply(positiveAmount));

        var operationDate = dateProvider.getDate();
        var allowOverdraft = accountParameterProvider.getAllowOverdraft();

        assert isAfterOrEqualsLastOperationDate(operationDate);
        assert balanceAfterOperation.isNotLessThan(allowOverdraft.opposite());

        var operation = new Operation(operationDate, type, positiveAmount, balanceAfterOperation);
        operationList.add(operation);
    }

    public Amount currentBalance() {
        var initialBalance = accountParameterProvider.getInitialBalance();
        var operationsBalance = operationList.stream()
                .map(operation -> operation.type().amountToApply(operation.operationAmount()))
                .reduce(new Amount(0), Amount::add);
        return initialBalance.add(operationsBalance);
    }

    private boolean isAfterOrEqualsLastOperationDate(LocalDateTime newOperationDate) {
        var lastOperationDate = operationList.stream()
                .map(Operation::date)
                .max(LocalDateTime::compareTo);
        return lastOperationDate
                .map(localDateTime -> !newOperationDate.isBefore(localDateTime))
                .orElse(true);
    }
}
