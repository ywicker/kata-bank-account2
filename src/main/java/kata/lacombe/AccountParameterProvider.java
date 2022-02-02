package kata.lacombe;

public interface AccountParameterProvider {
    Amount getInitialBalance();
    NonNegativeAmount getAllowOverdraft();
}
