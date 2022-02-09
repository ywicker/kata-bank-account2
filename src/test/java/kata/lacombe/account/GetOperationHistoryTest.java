package kata.lacombe.account;

import kata.lacombe.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Comparator;

import static java.time.Month.FEBRUARY;
import static kata.lacombe.OperationType.DEPOSIT;
import static kata.lacombe.OperationType.WITHDRAWAL;
import static kata.lacombe.Amount.createAmount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @DisplayName("An initialized account should make no history")
    void anInitializedAccountShouldMakeNoHistory() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThat(account.getOperationHistory()).isEmpty();
    }
    @Test
    @DisplayName("Depositing once should make a history with one operation")
    void depositingOnceShouldMakeAHistoryWithOneOperation() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        account.deposit(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    @DisplayName("Withdrawing once should make a history with one operation")
    void withdrawingOnceShouldMakeAHistoryWithOneOperation() {
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Balance(1));
        when(mockAccountParameterProvider.getMinimumAuthorizedBalance()).thenReturn(new Balance(0));
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(1);
    }
    @Test
    @DisplayName("Depositing and withdrawing once should make a history with two operations")
    void depositingAndWithdrawingOnceShouldMakeAHistoryWithTwoOperations() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        account.deposit(1);
        account.withdrawal(1);

        assertThat(account.getOperationHistory()).hasSize(2);
    }
    @Test
    @DisplayName("Depositing once should make a history with one deposit operation")
    void depositingOnceShouldMakeAHistoryWithOneDepositOperation() {
        var date = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate()).thenReturn(date);
        Account account = new Account(mockDateProvider, defaultAccountParameterProvider);

        account.deposit(1);
        var operationHistory = account.getOperationHistory();

        var expectedBalance = new Balance(1);
        var expectedOperation = new Operation(date, DEPOSIT, createAmount(1), expectedBalance);
        assertThat(operationHistory)
                .hasSize(1)
                .containsExactly(expectedOperation);
    }
    @Test
    @DisplayName("Withdrawing once should make a history with one withdrawal operation")
    void withdrawingOnceShouldMakeAHistoryWithOneWithdrawalOperation() {
        var date = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate()).thenReturn(date);
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Balance(1));
        when(mockAccountParameterProvider.getMinimumAuthorizedBalance()).thenReturn(new Balance(0));
        Account account = new Account(mockDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);
        var operationHistory = account.getOperationHistory();

        var expectedBalance = new Balance(0);
        var expectedOperation = new Operation(date, WITHDRAWAL, createAmount(1), expectedBalance);
        assertThat(operationHistory)
                .hasSize(1)
                .containsExactly(expectedOperation);
    }
    @Test
    @DisplayName("The last operation should contain the final balance")
    void theLastOperationShouldContainTheFinalBalance() {
        var date = LocalDateTime.of(2022, FEBRUARY, 2, 0, 0);
        when(mockDateProvider.getDate())
                .thenReturn(LocalDateTime.of(2022, FEBRUARY, 1, 0, 0))
                .thenReturn(LocalDateTime.of(2022, FEBRUARY, 1, 2, 0))
                .thenReturn(LocalDateTime.of(2022, FEBRUARY, 1, 4, 0))
                .thenReturn(date);
        Account account = new Account(mockDateProvider, defaultAccountParameterProvider);

        account.deposit(1);
        account.deposit(1);
        account.withdrawal(1);
        account.deposit(1);
        var operationHistory = account.getOperationHistory();
        var lastOperation = operationHistory.stream().max(Comparator.comparing(Operation::date)).get();

        assertThat(operationHistory).hasSize(4);
        var expectedBalance = new Balance(2);
        assertThat(lastOperation.balanceAfterOperation()).isEqualTo(expectedBalance);
        assertThat(account.getCurrentBalance()).isEqualTo(2);
    }
    @Test
    @DisplayName("Do an operation should return error if it is not the last")
    void doAnOperationShouldReturnErrorIfItIsNotTheLast() {
        var oneFebruaryZeroHour = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        var oneFebruaryTwoHour = LocalDateTime.of(2022, FEBRUARY, 1, 2, 0);
        when(mockDateProvider.getDate())
                .thenReturn(oneFebruaryTwoHour)
                .thenReturn(oneFebruaryZeroHour);
        Account account = new Account(mockDateProvider, defaultAccountParameterProvider);

        account.deposit(1);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    @DisplayName("Deposit and withdraw several times should make history sorted by dates")
    void depositAndWithdrawSeveralTimesShouldMakeHistorySortedByDates() {
        var oneFebruaryZeroHour = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        var oneFebruaryTwoHour = LocalDateTime.of(2022, FEBRUARY, 1, 2, 0);
        var oneFebruaryFourHour = LocalDateTime.of(2022, FEBRUARY, 1, 4, 0);
        var twoFebruaryZeroHour = LocalDateTime.of(2022, FEBRUARY, 2, 0, 0);
        when(mockDateProvider.getDate())
                .thenReturn(oneFebruaryZeroHour)
                .thenReturn(oneFebruaryTwoHour)
                .thenReturn(oneFebruaryFourHour)
                .thenReturn(twoFebruaryZeroHour);
        Account account = new Account(mockDateProvider, defaultAccountParameterProvider);

        account.deposit(1);
        account.deposit(1);
        account.withdrawal(1);
        account.deposit(1);
        var operationHistory = account.getOperationHistory();

        assertThat(operationHistory)
                .hasSize(4)
                .containsExactly(
                        new Operation(oneFebruaryZeroHour, DEPOSIT, createAmount(1), new Balance(1)),
                        new Operation(oneFebruaryTwoHour, DEPOSIT, createAmount(1), new Balance(2)),
                        new Operation(oneFebruaryFourHour, WITHDRAWAL, createAmount(1), new Balance(1)),
                        new Operation(twoFebruaryZeroHour, DEPOSIT, createAmount(1), new Balance(2))
                );
    }
    @Test
    @DisplayName("Deposit and withdraw several times at the same date should make history sorted by order of operation")
    void depositAndWithdrawTwiceShouldMakeHistorySortedByDates() {
        var oneFebruaryZeroHour = LocalDateTime.of(2022, FEBRUARY, 1, 0, 0);
        when(mockDateProvider.getDate())
                .thenReturn(oneFebruaryZeroHour);
        Account account = new Account(mockDateProvider, defaultAccountParameterProvider);

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
                        new Operation(oneFebruaryZeroHour, DEPOSIT, createAmount(1), new Balance(1)),
                        new Operation(oneFebruaryZeroHour, DEPOSIT, createAmount(1), new Balance(2)),
                        new Operation(oneFebruaryZeroHour, WITHDRAWAL, createAmount(1), new Balance(1)),
                        new Operation(oneFebruaryZeroHour, DEPOSIT, createAmount(1), new Balance(2)),
                        new Operation(oneFebruaryZeroHour, WITHDRAWAL, createAmount(1), new Balance(1)),
                        new Operation(oneFebruaryZeroHour, DEPOSIT, createAmount(1), new Balance(2)),
                        new Operation(oneFebruaryZeroHour, DEPOSIT, createAmount(1), new Balance(3))
                );
    }
}
