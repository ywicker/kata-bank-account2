package kata.lacombe.account;

import kata.lacombe.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void withdrawal_1_with_a_balance_of_1(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(1);
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(0);
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(0);
    }
    @Test
    void withdrawal_1_without_balance_should_return_error(){
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void withdrawal_1_without_balance_and_with_allow_overdraft_of_1(){
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(1);
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(-1);
    }
    @Test
    void withdrawal_1_with_negative_balance_should_return_error(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(-1);
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void withdrawal_1_with_negative_balance_and_with_allow_overdraft_of_2(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(-1);
        when(mockAccountParameterProvider.getAllowOverdraft()).thenReturn(2);
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(-2);
    }
    @Test
    void withdrawal_1_twice_with_a_balance_of_2(){
        when(mockAccountParameterProvider.getInitialBalance()).thenReturn(2);
        Account account = new Account(defaultDateProvider, mockAccountParameterProvider);

        account.withdrawal(1);
        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(0);
    }
    @Test
    void withdrawal_0_should_return_error(){
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.withdrawal(0))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void withdrawal_minus_1_should_return_error(){
        Account account = new Account(defaultDateProvider, defaultAccountParameterProvider);

        assertThatThrownBy(() -> account.withdrawal(-1))
                .isInstanceOf(AssertionError.class);
    }
}
