package kata.lacombe.account;

import kata.lacombe.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static kata.lacombe.NonNegativeAmount.createNonNegativeAmount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WithdrawalTest {
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
    @DisplayName("Withdrawal 1 with a balance of 1")
    void withdrawal1WithABalanceOf1(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Amount(1));
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(createNonNegativeAmount(0));
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);

        assertThat(account.getCurrentBalance()).isEqualTo(0);
    }
    @Test
    @DisplayName("Withdrawal 1 without balance should return error")
    void withdrawal1WithoutBalanceShouldReturnError(){
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    @DisplayName("Withdrawal 1 without balance and with allow overdraft of 1")
    void withdrawal1WithoutBalanceAndWithAllowOverdraftOf1(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Amount(0));
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(createNonNegativeAmount(1));
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);

        assertThat(account.getCurrentBalance()).isEqualTo(-1);
    }
    @Test
    @DisplayName("Withdrawal 1 with negative balance should return error")
    void withdrawal1WithNegativeBalanceShouldReturnError(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Amount(-1));
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(createNonNegativeAmount(0));
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    @DisplayName("Withdrawal 1 with negative balance and with allow overdraft of 2")
    void withdrawal1WithNegativeBalanceAndWithAllowOverdraftOf2(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Amount(-1));
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(createNonNegativeAmount(2));
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);

        assertThat(account.getCurrentBalance()).isEqualTo(-2);
    }
    @Test
    @DisplayName("Withdrawal 1 twice with a balance of 2")
    void withdrawal1TwiceWithABalanceOf2(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(new Amount(2));
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(createNonNegativeAmount(0));
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);
        account.withdrawal(1);

        assertThat(account.getCurrentBalance()).isEqualTo(0);
    }
    @Test
    @DisplayName("Withdrawal 0 should return error")
    void withdrawal0ShouldReturnError(){
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.withdrawal(0))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    @DisplayName("Withdrawal minus 1 should return error")
    void withdrawalMinus1ShouldReturnError(){
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.withdrawal(-1))
                .isInstanceOf(AssertionError.class);
    }
}
