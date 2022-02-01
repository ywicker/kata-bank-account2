package kata.lacombe.account;

import kata.lacombe.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WithdrawalTest {
    @Test
    void withdrawal_1_with_a_balance_of_1(){
        Account account = new Account(1);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(0);
    }
    @Test
    void withdrawal_1_without_balance_should_return_error(){
        Account account = new Account();

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void withdrawal_1_without_balance_and_with_allow_overdraft_of_1(){
        Account account = new Account(0, 1);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(-1);
    }
    @Test
    void withdrawal_1_with_negative_balance_should_return_error(){
        Account account = new Account(-1);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AssertionError.class);
    }
}
