package kata.lacombe.account;

import kata.lacombe.*;
import kata.lacombe.errors.AmountValueException;
import kata.lacombe.providers.AccountParameterProvider;
import kata.lacombe.providers.DateProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepositTest {
    DateProvider defaultDateProvider;
    AccountParameterProvider defaultAccountParameterProvider;
    @Mock
    AccountParameterProvider mockAccountParameterProvider;

    @BeforeEach
    void setup() {
        defaultDateProvider = new DefaultDateProvider();
        defaultAccountParameterProvider = new DefaultAccountParameterProvider();
    }

    @Test
    @DisplayName("Deposit 1 without balance")
    void deposit1WithoutBalance() throws Exception {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(1);
    }
    @Test
    @DisplayName("Deposit 1 with balance")
    void deposit1WithBalance() throws Exception {
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Balance(1));
        when(mockAccountParameterProvider.getMinimumAuthorizedBalance()).thenReturn(new Balance(0));
        Account account = new Account(defaultDateProvider,mockAccountParameterProvider);

        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(2);
    }
    @Test
    @DisplayName("Deposit 1 twice")
    void deposit1Twice() throws Exception {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        account.deposit(1);
        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(2);
    }
    @Test
    @DisplayName("Deposit 0 should return error")
    void deposit0ShouldReturnError() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.deposit(0))
                .isInstanceOf(AmountValueException.class);
    }
    @Test
    @DisplayName("Deposit minus 1 should return error")
    void depositMinus1ShouldReturnError() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.deposit(-1))
                .isInstanceOf(AmountValueException.class);
    }
}
