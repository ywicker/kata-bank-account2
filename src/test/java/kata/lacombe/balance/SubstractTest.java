package kata.lacombe.balance;

import kata.lacombe.Amount;
import kata.lacombe.Balance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SubstractTest {
    @Test
    void substract_1_with_existing_balance_of_1() {
        Balance balance = new Balance(1);

        Amount amount = new Amount(1);
        balance.substract(amount);

        Assertions.assertThat(balance.getValue()).isEqualTo(0);
    }
    @Test
    void substract_1_without_balance_should_return_error() {
        Balance balance = new Balance();

        Amount amount = new Amount(1);
        assertThatThrownBy(() -> balance.substract(amount))
                .isInstanceOf(AssertionError.class);
    }
}
