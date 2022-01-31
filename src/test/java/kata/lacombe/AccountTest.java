package kata.lacombe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountTest {
    @Test
    void deposit_1_without_balance() {
        Account account = new Account();

        account.deposit(1);

        assertThat(account.getBalance()).isEqualTo(1);
    }
    @Test
    void deposit_1_with_balance() {
        Account account = new Account(1);

        account.deposit(1);

        assertThat(account.getBalance()).isEqualTo(2);
    }
    @Test
    void deposit_1_twice() {
        Account account = new Account();

        account.deposit(1);
        account.deposit(1);

        assertThat(account.getBalance()).isEqualTo(2);
    }
    @Test
    void deposit_0() {
        Account account = new Account();

        assertThatThrownBy(() -> account.deposit(0))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void deposit_minus_1() {
        Account account = new Account();

        assertThatThrownBy(() -> account.deposit(-1))
                .isInstanceOf(AssertionError.class);
    }
}
