package kata.lacombe.account;

import kata.lacombe.Account;
import kata.lacombe.DateProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WithdrawalTest {
    DateProvider defaultDateProvider;

    @BeforeEach
    void setup() {
        defaultDateProvider = new DefaultDateProvider();
    }

    @Test
    void withdrawal_1_with_a_balance_of_1(){
        Account account = new Account(defaultDateProvider, 1);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(0);
    }
    @Test
    void withdrawal_1_without_balance_should_return_error(){
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void withdrawal_1_without_balance_and_with_allow_overdraft_of_1(){
        Account account = new Account(defaultDateProvider, 0, 1);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(-1);
    }
    @Test
    void withdrawal_1_with_negative_balance_should_return_error(){
        Account account = new Account(defaultDateProvider, -1);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void withdrawal_1_with_negative_balance_and_with_allow_overdraft_of_2(){
        Account account = new Account(defaultDateProvider, -1, 2);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(-2);
    }
    @Test
    void withdrawal_1_twice_with_a_balance_of_2(){
        Account account = new Account(defaultDateProvider, 2);

        account.withdrawal(1);
        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(0);
    }
    @Test
    void withdrawal_0_should_return_error(){
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.withdrawal(0))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void withdrawal_minus_1_should_return_error(){
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.withdrawal(-1))
                .isInstanceOf(AssertionError.class);
    }
}
