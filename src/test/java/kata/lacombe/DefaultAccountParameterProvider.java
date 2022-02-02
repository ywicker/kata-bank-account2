package kata.lacombe;

public class DefaultAccountParameterProvider implements AccountParameterProvider {
    @Override
    public int getInitialBalance() {
        return 0;
    }

    @Override
    public int getAllowOverdraft() {
        return 0;
    }
}
