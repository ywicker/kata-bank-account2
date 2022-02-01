package kata.lacombe.balance;

import kata.lacombe.Amount;
import kata.lacombe.Balance;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;

public class SubstractTest {
    @Test
    void withdrawal_1_with_existing_balance_of_1() {
        Balance balance = new Balance(1);

        Amount amount = new Amount(1);
        balance.substract(amount);

        Assertions.assertThat(balance.getValue()).isEqualTo(0);
    }
}
