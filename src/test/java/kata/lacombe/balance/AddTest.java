package kata.lacombe.balance;

import kata.lacombe.Amount;
import kata.lacombe.Balance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static kata.lacombe.Amount.*;

public class AddTest {
    @Test
    void add_1() {
        Balance balance = new Balance();

        Amount amount = createAmount(1);
        balance.add(amount);

        Assertions.assertThat(balance.getValue()).isEqualTo(1);
    }
    @Test
    void add_1_with_balance() {
        Balance balance = new Balance(1);

        Amount amount = createAmount(1);
        balance.add(amount);

        Assertions.assertThat(balance.getValue()).isEqualTo(2);
    }
    @Test
    void add_1_twice() {
        Balance balance = new Balance();

        Amount amount = createAmount(1);
        balance.add(amount);
        balance.add(amount);

        Assertions.assertThat(balance.getValue()).isEqualTo(2);
    }
}
