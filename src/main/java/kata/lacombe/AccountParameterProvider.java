package kata.lacombe;

import static kata.lacombe.NonNegativeAmount.createNonNegativeAmount;

public interface AccountParameterProvider {
    default Amount getInitialBalance(){
        return new Amount(0);
    }
    default NonNegativeAmount getAllowOverdraft() {
        return createNonNegativeAmount(0);
    }
}
