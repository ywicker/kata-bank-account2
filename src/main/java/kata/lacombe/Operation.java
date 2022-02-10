package kata.lacombe;

import java.time.LocalDateTime;

public record Operation(LocalDateTime date, OperationType type, Amount operationAmount, Amount balanceAfterOperation) {
    public Operation copy() {
        return new Operation(date, type, operationAmount, balanceAfterOperation);
    }
}
