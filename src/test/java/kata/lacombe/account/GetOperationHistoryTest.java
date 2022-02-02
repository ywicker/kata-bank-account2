package kata.lacombe.account;

import kata.lacombe.Account;
import kata.lacombe.Amount;
import kata.lacombe.DateProvider;
import kata.lacombe.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static java.time.Month.FEBRUARY;
import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetOperationHistoryTest {
    DateProvider defaultDateProvider;
    @Mock
    DateProvider mockDateProvider;

    @BeforeEach
    void setup() {
        defaultDateProvider = new DefaultDateProvider();
    }

    @Test
    void an_initialized_account_should_make_no_history() {
        Account account = new Account(defaultDateProvider);

        assertThat(account.getOperationHistory()).isEmpty();
    }
    @Test
    void depositing_once_should_make_a_history_with_one_operation() {
        Account account = new Account(defaultDateProvider);

        account.deposit(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    void withdrawing_once_should_make_a_history_with_one_operation() {
        Account account = new Account(defaultDateProvider, 1);

        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    void depositing_and_withdrawing_once_should_make_a_history_with_two_operations() {
        Account account = new Account(defaultDateProvider);

        account.deposit(1);
        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(2);
    }
    @Test
    void depositing_once_should_make_a_history_with_one_deposit_operation() {
        var date = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate()).thenReturn(date);
        Account account = new Account(mockDateProvider);

        account.deposit(1);
        var operationHistory = account.getOperationHistory();

        var expectedOperation = new Operation(date, DEPOSIT, Amount.createAmount(1));
        assertThat(operationHistory)
                .hasSize(1)
                .containsExactly(expectedOperation);
    }
    @Test
    void withdrawing_once_should_make_a_history_with_one_withdrawal_operation() {
        var date = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate()).thenReturn(date);
        Account account = new Account(mockDateProvider, 1);

        account.withdrawal(1);
        var operationHistory = account.getOperationHistory();

        var expectedOperation = new Operation(date, WITHDRAWAL, Amount.createAmount(1));
        assertThat(operationHistory)
                .hasSize(1)
                .containsExactly(expectedOperation);
    }
}
