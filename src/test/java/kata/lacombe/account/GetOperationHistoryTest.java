package kata.lacombe.account;

import kata.lacombe.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetOperationHistoryTest {
    @Test
    void an_initialized_account_should_make_no_history() {
        Account account = new Account();

        Assertions.assertThat(account.getOperationHistory()).isEmpty();
    }
}
