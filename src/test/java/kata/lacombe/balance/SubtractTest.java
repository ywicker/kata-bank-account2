package kata.lacombe.balance;

import kata.lacombe.PositiveAmount;
import kata.lacombe.Balance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static kata.lacombe.PositiveAmount.createAmount;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SubtractTest {
    @Test
    void subtract_1_with_existing_balance_of_1() {
        Balance balance = new Balance(1);

        PositiveAmount positiveAmount = createAmount(1);
        balance.subtract(positiveAmount);

        Assertions.assertThat(balance.getValue()).isEqualTo(0);
    }
    @Test
    void subtract_1_without_balance_should_return_error() {
        Balance balance = new Balance();

        PositiveAmount positiveAmount = createAmount(1);
        assertThatThrownBy(() -> balance.subtract(positiveAmount))
                .isInstanceOf(AssertionError.class);
    }
    @Test
    void subtract_1_without_balance_and_with_allow_overdraft_of_1() {
        Balance balance = new Balance(0,1);

        PositiveAmount positiveAmount = createAmount(1);
        balance.subtract(positiveAmount);

        Assertions.assertThat(balance.getValue()).isEqualTo(-1);
    }
}
