package kata.lacombe;

import java.time.LocalDateTime;

public record Operation(LocalDateTime date, OperationType type, PositiveAmount operationAmount, Amount balanceAfterOperation) {
}
