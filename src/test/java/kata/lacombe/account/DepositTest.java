package kata.lacombe.account;

import kata.lacombe.Account;
import kata.lacombe.DateProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DepositTest {
    DateProvider defaultDateProvider;

    @BeforeEach
    void setup() {
        defaultDateProvider = new DefaultDateProvider();
    }

    @Test
    void deposit_1_without_balance() {
        Account account = new Account(defaultDateProvider);

        account.deposit(1);

        assertThat(account.getBalance()).isEqualTo(1);
    }
    @Test
    void deposit_1_with_balance() {
        Account account = new Account(defaultDateProvider,1);

        account.deposit(1);

        assertThat(account.getBalance()).isEqualTo(2);
    }
    @Test
    void deposit_1_twice() {
        Account account = new Account(defaultDateProvider);

        account.deposit(1);
        account.deposit(1);

        assertThat(account.getBalance()).isEqualTo(2);
    }
    @Test
    void deposit_0_should_return_error() {
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.deposit(0))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void deposit_minus_1_should_return_error() {
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.deposit(-1))
                .isInstanceOf(AssertionError.class);
    }
}
