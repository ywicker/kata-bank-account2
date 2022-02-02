package kata.lacombe;

import java.util.ArrayList;
import java.util.List;

import static kata.lacombe.PositiveAmount.createAmount;

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

    public void add(OperationType type, int value) {
        var positiveAmount = createAmount(value);
        int balance = actualBalance();
        int futureBalance = balance + type.amountToApply(positiveAmount);
        var allowOverdraft = accountParameterProvider.getAllowOverdraft();
        assert balance + type.amountToApply(positiveAmount) >= -allowOverdraft;
        var operation = new Operation(dateProvider.getDate(), type, positiveAmount, new Balance(futureBalance));
        operationList.add(operation);
    }

    public int actualBalance() {
        var initialBalance = accountParameterProvider.getInitialBalance();
        var operationsBalance = operationList.stream()
                .map(operation -> operation.type().amountToApply(operation.amount()))
                .reduce(0, Integer::sum);
        return initialBalance + operationsBalance;
    }
}
