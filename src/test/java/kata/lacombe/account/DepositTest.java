package kata.lacombe.account;

import kata.lacombe.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static kata.lacombe.NonNegativeAmount.createNonNegativeAmount;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void deposit_1_without_balance() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(1);
    }
    @Test
    void deposit_1_with_balance() {
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Amount(1));
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(createNonNegativeAmount(0));
        Account account = new Account(defaultDateProvider,mockAccountParameterProvider);

        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(2);
    }
    @Test
    void deposit_1_twice() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        account.deposit(1);
        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(2);
    }
    @Test
    void deposit_0_should_return_error() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.deposit(0))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void deposit_minus_1_should_return_error() {
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.deposit(-1))
                .isInstanceOf(AssertionError.class);
    }
}
