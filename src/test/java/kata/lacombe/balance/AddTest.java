package kata.lacombe.balance;

import kata.lacombe.PositiveAmount;
import kata.lacombe.Balance;
import org.junit.jupiter.api.Test;

import static kata.lacombe.PositiveAmount.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AddTest {
    @Test
    void add_1() {
        Balance balance = new Balance();

        PositiveAmount positiveAmount = createAmount(1);
        balance.add(positiveAmount);

        assertThat(balance.getValue()).isEqualTo(1);
    }
    @Test
    void add_1_with_balance() {
        Balance balance = new Balance(1);

        PositiveAmount positiveAmount = createAmount(1);
        balance.add(positiveAmount);

        assertThat(balance.getValue()).isEqualTo(2);
    }
    @Test
    void add_1_twice() {
        Balance balance = new Balance();

        PositiveAmount positiveAmount = createAmount(1);
        balance.add(positiveAmount);
        balance.add(positiveAmount);

        assertThat(balance.getValue()).isEqualTo(2);
    }
}
