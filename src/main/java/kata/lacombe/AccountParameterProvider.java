package kata.lacombe;

public interface AccountParameterProvider {
    default Balance getInitialBalance(){
        return new Balance(0);
    }
    default Balance getMinimumAuthorizedBalance() {
        return new Balance(0);
    }
}
