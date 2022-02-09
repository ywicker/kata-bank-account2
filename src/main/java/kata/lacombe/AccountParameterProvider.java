package kata.lacombe;

import static kata.lacombe.NonNegativeAmount.createNonNegativeAmount;

public interface AccountParameterProvider {
    default Balance getInitialBalance(){
        return new Balance(0);
    }
    default Balance getMinimumAuthorizedBalance() {
        return new Balance(0);
    }
}
