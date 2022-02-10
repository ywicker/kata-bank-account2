package kata.lacombe.account;

import kata.lacombe.Account;
import kata.lacombe.DefaultDateProvider;
import kata.lacombe.errors.AmountValueException;
import kata.lacombe.errors.AuthorizedOverdraftExceededException;
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
public class WithdrawalTest {
    DateProvider defaultDateProvider;

    @BeforeEach
    void setup() {
        defaultDateProvider = new DefaultDateProvider();
    }

    @Test
    @DisplayName("Withdrawal 1 with a balance of 1")
    void withdrawal1WithABalanceOf1() throws Exception {
        var accountParameters = createAccountParameters(1, 0);
        Account account = new Account(defaultDateProvider, accountParameters);

        account.withdrawal(1);

        assertThat(account.getCurrentBalance()).isEqualTo(0);
    }
    @Test
    @DisplayName("Withdrawal 1 without balance should return error")
    void withdrawal1WithoutBalanceShouldReturnError() {
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AuthorizedOverdraftExceededException.class);
    }
    @Test
    @DisplayName("Withdrawal 1 without balance and with allow overdraft of 1")
    void withdrawal1WithoutBalanceAndWithAllowOverdraftOf1() throws Exception{
        var accountParameters = createAccountParameters(0, -1);
        Account account = new Account(defaultDateProvider, accountParameters);

        account.withdrawal(1);

        assertThat(account.getCurrentBalance()).isEqualTo(-1);
    }
    @Test
    @DisplayName("Withdrawal 1 with negative balance should return error")
    void withdrawal1WithNegativeBalanceShouldReturnError(){
        var accountParameters = createAccountParameters(-1, 0);
        Account account = new Account(defaultDateProvider, accountParameters);

        assertThatThrownBy(() -> account.withdrawal(1))
                .isInstanceOf(AuthorizedOverdraftExceededException.class);
    }
    @Test
    @DisplayName("Withdrawal 1 with negative balance and with allow overdraft of 2")
    void withdrawal1WithNegativeBalanceAndWithAllowOverdraftOf2() throws Exception{
        var accountParameters = createAccountParameters(-1, -2);
        Account account = new Account(defaultDateProvider, accountParameters);

        account.withdrawal(1);

        assertThat(account.getCurrentBalance()).isEqualTo(-2);
    }
    @Test
    @DisplayName("Withdrawal 1 twice with a balance of 2")
    void withdrawal1TwiceWithABalanceOf2() throws Exception{
        var accountParameters = createAccountParameters(2, 0);
        Account account = new Account(defaultDateProvider, accountParameters);

        account.withdrawal(1);
        account.withdrawal(1);

        assertThat(account.getCurrentBalance()).isEqualTo(0);
    }
    @Test
    @DisplayName("Withdrawal 0 should return error")
    void withdrawal0ShouldReturnError(){
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.withdrawal(0))
                .isInstanceOf(AmountValueException.class);
    }
    @Test
    @DisplayName("Withdrawal minus 1 should return error")
    void withdrawalMinus1ShouldReturnError(){
        Account account = new Account(defaultDateProvider);

        assertThatThrownBy(() -> account.withdrawal(-1))
                .isInstanceOf(AmountValueException.class);
    }
}
