package kata.lacombe.account;

import kata.lacombe.*;
import kata.lacombe.errors.OperationDateException;
import kata.lacombe.providers.DateProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Comparator;

import static java.time.Month.FEBRUARY;
import static kata.lacombe.AccountParameters.createAccountParameters;
import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @DisplayName("An initialized account should make no history")
    void anInitializedAccountShouldMakeNoHistory() {
        Account account = new Account(defaultDateProvider);

        assertThat(account.getOperationHistory()).isEmpty();
    }
    @Test
    @DisplayName("Depositing once should make a history with one operation")
    void depositingOnceShouldMakeAHistoryWithOneOperation() throws Exception {
        Account account = new Account(defaultDateProvider);

        account.deposit(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    @DisplayName("Withdrawing once should make a history with one operation")
    void withdrawingOnceShouldMakeAHistoryWithOneOperation() throws Exception {
        var accountParameters = createAccountParameters(1, 0);
        Account account = new Account(defaultDateProvider, accountParameters);

        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    @DisplayName("Depositing and withdrawing once should make a history with two operations")
    void depositingAndWithdrawingOnceShouldMakeAHistoryWithTwoOperations() throws Exception {
        Account account = new Account(defaultDateProvider);

        account.deposit(1);
        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(2);
    }
    @Test
    @DisplayName("Depositing once should make a history with one deposit operation")
    void depositingOnceShouldMakeAHistoryWithOneDepositOperation() throws Exception {
        var date = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate()).thenReturn(date);
        Account account = new Account(mockDateProvider);

        account.deposit(1);
        var operationHistory = account.getOperationHistory();

        var expectedBalance = new Amount(1);
        var expectedOperation = new Operation(date, DEPOSIT, new Amount(1), expectedBalance);
        assertThat(operationHistory)
                .hasSize(1)
                .containsExactly(expectedOperation);
    }
    @Test
    @DisplayName("Withdrawing once should make a history with one withdrawal operation")
    void withdrawingOnceShouldMakeAHistoryWithOneWithdrawalOperation() throws Exception {
        var date = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate()).thenReturn(date);
        var accountParameters = createAccountParameters(1, 0);
        Account account = new Account(mockDateProvider, accountParameters);

        account.withdrawal(1);
        var operationHistory = account.getOperationHistory();

        var expectedBalance = new Amount(0);
        var expectedOperation = new Operation(date, WITHDRAWAL, new Amount(1), expectedBalance);
        assertThat(operationHistory)
                .hasSize(1)
                .containsExactly(expectedOperation);
    }
    @Test
    @DisplayName("The last operation should contain the final balance")
    void theLastOperationShouldContainTheFinalBalance() throws Exception {
        var date = LocalDateTime.of(2022, FEBRUARY, 2, 0, 0);
        when(mockDateProvider.getDate())
                .thenReturn(LocalDateTime.of(2022, FEBRUARY, 1, 0, 0))
                .thenReturn(LocalDateTime.of(2022, FEBRUARY, 1, 2, 0))
                .thenReturn(LocalDateTime.of(2022, FEBRUARY, 1, 4, 0))
                .thenReturn(date);
        Account account = new Account(mockDateProvider);

        account.deposit(1);
        account.deposit(1);
        account.withdrawal(1);
        account.deposit(1);
        var operationHistory = account.getOperationHistory();
        var lastOperation = operationHistory.stream().max(Comparator.comparing(Operation::date)).get();

        assertThat(operationHistory).hasSize(4);
        var expectedBalance = new Amount(2);
        assertThat(lastOperation.balanceAfterOperation()).isEqualTo(expectedBalance);
        assertThat(account.getCurrentBalance()).isEqualTo(2);
    }
    @Test
    @DisplayName("Do an operation should return error if it is not the last")
    void doAnOperationShouldReturnErrorIfItIsNotTheLast() throws Exception {
        var oneFebruaryZeroHour = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        var oneFebruaryTwoHour = LocalDateTime.of(2022, FEBRUARY, 1, 2, 0);
        when(mockDateProvider.getDate())
                .thenReturn(oneFebruaryTwoHour)
                .thenReturn(oneFebruaryZeroHour);
        Account account = new Account(mockDateProvider);

        account.deposit(1);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(OperationDateException.class);
    }
    @Test
    @DisplayName("Deposit and withdraw several times should make history sorted by dates")
    void depositAndWithdrawSeveralTimesShouldMakeHistorySortedByDates() throws Exception {
        var oneFebruaryZeroHour = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        var oneFebruaryTwoHour = LocalDateTime.of(2022, FEBRUARY, 1, 2, 0);
        var oneFebruaryFourHour = LocalDateTime.of(2022, FEBRUARY, 1, 4, 0);
        var twoFebruaryZeroHour = LocalDateTime.of(2022, FEBRUARY, 2, 0, 0);
        when(mockDateProvider.getDate())
                .thenReturn(oneFebruaryZeroHour)
                .thenReturn(oneFebruaryTwoHour)
                .thenReturn(oneFebruaryFourHour)
                .thenReturn(twoFebruaryZeroHour);
        Account account = new Account(mockDateProvider);

        account.deposit(1);
        account.deposit(1);
        account.withdrawal(1);
        account.deposit(1);
        var operationHistory = account.getOperationHistory();

        assertThat(operationHistory)
                .hasSize(4)
                .containsExactly(
                        new Operation(oneFebruaryZeroHour, DEPOSIT, new Amount(1), new Amount(1)),
                        new Operation(oneFebruaryTwoHour, DEPOSIT, new Amount(1), new Amount(2)),
                        new Operation(oneFebruaryFourHour, WITHDRAWAL, new Amount(1), new Amount(1)),
                        new Operation(twoFebruaryZeroHour, DEPOSIT, new Amount(1), new Amount(2))
                );
    }
    @Test
    @DisplayName("Deposit and withdraw several times at the same date should make history sorted by order of operation")
    void depositAndWithdrawTwiceShouldMakeHistorySortedByDates() throws Exception {
        var oneFebruaryZeroHour = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate())
                .thenReturn(oneFebruaryZeroHour);
        Account account = new Account(mockDateProvider);

        account.deposit(1);
        account.deposit(1);
        account.withdrawal(1);
        account.deposit(1);
        account.withdrawal(1);
        account.deposit(1);
        account.deposit(1);
        var operationHistory = account.getOperationHistory();

        assertThat(operationHistory)
                .hasSize(7)
                .containsExactly(
                        new Operation(oneFebruaryZeroHour, DEPOSIT, new Amount(1), new Amount(1)),
                        new Operation(oneFebruaryZeroHour, DEPOSIT, new Amount(1), new Amount(2)),
                        new Operation(oneFebruaryZeroHour, WITHDRAWAL, new Amount(1), new Amount(1)),
                        new Operation(oneFebruaryZeroHour, DEPOSIT, new Amount(1), new Amount(2)),
                        new Operation(oneFebruaryZeroHour, WITHDRAWAL, new Amount(1), new Amount(1)),
                        new Operation(oneFebruaryZeroHour, DEPOSIT, new Amount(1), new Amount(2)),
                        new Operation(oneFebruaryZeroHour, DEPOSIT, new Amount(1), new Amount(3))
                );
    }
}
