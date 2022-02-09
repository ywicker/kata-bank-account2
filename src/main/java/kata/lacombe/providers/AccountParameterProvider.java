package kata.lacombe.providers;

import kata.lacombe.Balance;

public interface AccountParameterProvider {
    default Balance getInitialBalance(){
        return new Balance(0);
    }
    default Balance getMinimumAuthorizedBalance() {
        return new Balance(0);
    }
}
