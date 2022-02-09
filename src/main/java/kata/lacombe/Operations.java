package kata.lacombe;

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

    public void addOperation(OperationType type, Amount amount) {
        var currentBalance = currentBalance();
        var balanceAfterOperation = currentBalance.newBalance(type.amountToApply(amount));

        var operationDate = dateProvider.getDate();
        var minimumAuthorizedBalance = accountParameterProvider.getMinimumAuthorizedBalance();

        assert isAfterOrEqualsLastOperationDate(operationDate);
        assert balanceAfterOperation.value() >= minimumAuthorizedBalance.value();

        var operation = new Operation(operationDate, type, amount, balanceAfterOperation);
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
