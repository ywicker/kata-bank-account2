package kata.lacombe;

import static kata.lacombe.NonNegativeAmount.createNonNegativeAmount;

public class DefaultAccountParameterProvider implements AccountParameterProvider {
    @Override
    public Amount getInitialBalance() {
        return new Amount(0);
    }

    @Override
    public NonNegativeAmount getAllowOverdraft() {
        return createNonNegativeAmount(0);
    }
}
