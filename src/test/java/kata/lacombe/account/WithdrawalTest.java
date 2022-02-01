package kata.lacombe.account;

import kata.lacombe.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WithdrawalTest {
    @Test
    void withdrawal_1_with_a_balance_of_1(){
        Account account = new Account(1);

        account.withdrawal(1);

        Assertions.assertThat(account.getBalance()).isEqualTo(0);
    }
}
