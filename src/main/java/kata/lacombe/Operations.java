package kata.lacombe;

import java.util.ArrayList;
import java.util.List;

public class Operations {
    private final List<Operation> operationList;

    public Operations() {
        this.operationList = new ArrayList<>();
    }

    public void add(Operation operation) {
        operationList.add(operation);
    }

    public List<Operation> getOperationList() {
        return List.copyOf(operationList);
    }
}
