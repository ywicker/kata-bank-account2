package kata.lacombe.account;

import kata.lacombe.Account;
import org.junit.jupiter.api.Test;

import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;

public class GetOperationHistoryTest {
    @Test
    void an_initialized_account_should_make_no_history() {
        Account account = new Account();

        assertThat(account.getOperationHistory()).isEmpty();
    }
    @Test
    void depositing_once_should_make_a_history_with_one_operation() {
        Account account = new Account();

        account.deposit(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    void withdrawing_once_should_make_a_history_with_one_operation() {
        Account account = new Account(1);

        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    void depositing_and_withdrawing_once_should_make_a_history_with_two_operations() {
        Account account = new Account();

        account.deposit(1);
        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(2);
    }
    @Test
    void depositing_once_should_make_a_history_with_one_deposit_operation() {
        Account account = new Account();

        account.deposit(1);
        var operationHistory = account.getOperationHistory();
        var depositOperation = operationHistory.get(0);

        assertThat(operationHistory).hasSize(1);
        assertThat(depositOperation.type()).isEqualTo(DEPOSIT);
    }
    @Test
    void withdrawing_once_should_make_a_history_with_one_deposit_operation() {
        Account account = new Account(1);

        account.withdrawal(1);
        var operationHistory = account.getOperationHistory();
        var depositOperation = operationHistory.get(0);

        assertThat(operationHistory).hasSize(1);
        assertThat(depositOperation.type()).isEqualTo(WITHDRAWAL);
    }
}
