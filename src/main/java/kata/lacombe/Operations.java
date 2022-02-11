package kata.lacombe;

import kata.lacombe.errors.OperationDateException;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class Operations {
    private final LinkedList<Operation> operationList;

    public Operations() {
        this.operationList = new LinkedList<>();
    }

    public List<Operation> getOperationList() {
        return List.copyOf(operationList);
    }

    public Optional<Operation> lastOperation() {
        if(operationList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(operationList.peekLast().copy());
    }
    private LocalDateTime lastOperationDate() {
        return lastOperation().map(Operation::date)
                .orElse(LocalDateTime.MIN);
    }

    public void addOperation(Operation operation) throws OperationDateException {
        operationDateCannotBeOlderThanLastOperationDate(operation.date());

        operationList.add(operation);
    }

    private void operationDateCannotBeOlderThanLastOperationDate(@NotNull LocalDateTime operationDate) throws OperationDateException {
        var lastOperationDate = lastOperationDate();
        if (operationDate.isBefore(lastOperationDate)) {
            throw new OperationDateException();
        }
    }
}
