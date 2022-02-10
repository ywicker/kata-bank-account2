package kata.lacombe;

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
    public LocalDateTime lastOperationDate() {
        return lastOperation().map(Operation::date)
                .orElse(LocalDateTime.MIN);
    }

    public void addOperation(Operation operation) {
        operationList.add(operation);
    }
}
