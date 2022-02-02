package kata.lacombe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static kata.lacombe.PositiveAmount.createPositiveAmount;

public class Operations {
    private final List<Operation> operationList;

    private final DateProvider dateProvider;
    private final AccountParameterProvider accountParameterProvider;

    public Operations(DateProvider dateProvider, AccountParameterProvider accountParameterProvider) {
        this.dateProvider = dateProvider;
        this.accountParameterProvider = accountParameterProvider;
        this.operationList = new ArrayList<>();
    }

    public List<Operation> getOperationList() {
        return List.copyOf(operationList);
    }

    public void addOperation(OperationType type, int value) {
        var positiveAmount = createPositiveAmount(value);
        var balance = currentBalance();
        var balanceAfterOperation = balance.add(type.amountToApply(positiveAmount));
        var allowOverdraft = accountParameterProvider.getAllowOverdraft();
        assert balanceAfterOperation.isNotLessThan(allowOverdraft.opposite());
        var operation = new Operation(dateProvider.getDate(), type, positiveAmount, balanceAfterOperation);
        operationList.add(operation);
    }

    public Amount currentBalance() {
        var initialBalance = accountParameterProvider.getInitialBalance();
        var operationsBalance = operationList.stream()
                .map(operation -> operation.type().amountToApply(operation.operationAmount()))
                .reduce(new Amount(0), Amount::add);
        return initialBalance.add(operationsBalance);
    }
}
