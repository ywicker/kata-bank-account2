package kata.lacombe.account;

import kata.lacombe.Account;
import kata.lacombe.DefaultDateProvider;
import kata.lacombe.errors.AmountValueException;
import kata.lacombe.providers.DateProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static kata.lacombe.AccountParameters.createAccountParameters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class DepositTest {
    DateProvider defaultDateProvider;

    @BeforeEach
    void setup() {
        defaultDateProvider = new DefaultDateProvider();
    }

    @Test
    @DisplayName("Deposit 1 without balance")
    void deposit1WithoutBalance() throws Exception {
        Account account = new Account(defaultDateProvider);

        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(1);
    }
    @Test
    @DisplayName("Deposit 1 with balance")
    void deposit1WithBalance() throws Exception {
        var accountParameters = createAccountParameters(1, 0);
        Account account = new Account(defaultDateProvider,accountParameters);

        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(2);
    }
    @Test
    @DisplayName("Deposit 1 twice")
    void deposit1Twice() throws Exception {
        Account account = new Account(defaultDateProvider);

        account.deposit(1);
        account.deposit(1);

        assertThat(account.getCurrentBalance()).isEqualTo(2);
    }
    @Test
    @DisplayName("Deposit 0 should return error")
    void deposit0ShouldReturnError() {
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.deposit(0))
                .isInstanceOf(AmountValueException.class);
    }
    @Test
    @DisplayName("Deposit minus 1 should return error")
    void depositMinus1ShouldReturnError() {
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.deposit(-1))
                .isInstanceOf(AmountValueException.class);
    }
}
