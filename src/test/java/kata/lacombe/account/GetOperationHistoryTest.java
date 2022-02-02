package kata.lacombe.account;

import kata.lacombe.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static java.time.Month.FEBRUARY;
import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;
import static kata.lacombe.PositiveAmount.createAmount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetOperationHistoryTest {
    DateProvider defaultDateProvider;
    AccountParameterProvider defaultAccountParameterProvider;
    @Mock
    DateProvider mockDateProvider;
    @Mock
    AccountParameterProvider mockAccountParameterProvider;

    @BeforeEach
    void setup() {
        defaultDateProvider = new DefaultDateProvider();
        defaultAccountParameterProvider = new DefaultAccountParameterProvider();
    }

    @Test
    void an_initialized_account_should_make_no_history() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThat(account.getOperationHistory()).isEmpty();
    }
    @Test
    void depositing_once_should_make_a_history_with_one_operation() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        account.deposit(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    void withdrawing_once_should_make_a_history_with_one_operation() {
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(1);
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    void depositing_and_withdrawing_once_should_make_a_history_with_two_operations() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        account.deposit(1);
        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(2);
    }
    @Test
    void depositing_once_should_make_a_history_with_one_deposit_operation() {
        var date = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate()).thenReturn(date);
        Account account = new Account(mockDateProvider, defaultAccountParameterProvider);

        account.deposit(1);
        var operationHistory = account.getOperationHistory();

        var expectedBalance = new Amount(1);
        var expectedOperation = new Operation(date, DEPOSIT, createAmount(1), expectedBalance);
        assertThat(operationHistory)
                .hasSize(1)
                .containsExactly(expectedOperation);
    }
    @Test
    void withdrawing_once_should_make_a_history_with_one_withdrawal_operation() {
        var date = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate()).thenReturn(date);
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(1);
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(0);
        Account account = new Account(mockDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);
        var operationHistory = account.getOperationHistory();

        var expectedBalance = new Amount(0);
        var expectedOperation = new Operation(date, WITHDRAWAL, createAmount(1), expectedBalance);
        assertThat(operationHistory)
                .hasSize(1)
                .containsExactly(expectedOperation);
    }
}
