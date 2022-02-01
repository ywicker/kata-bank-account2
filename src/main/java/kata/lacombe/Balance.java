package kata.lacombe;

public class Balance {
    private int balanceValue;
    private int allowOverdraft;

    public Balance() {
    }
    public Balance(final int balanceValue) {
        this.balanceValue = balanceValue;
    }

    public Balance(final int balanceValue, final int allowOverdraft) {
        this.balanceValue = balanceValue;
        this.allowOverdraft = allowOverdraft;
    }

    public void add(Amount amount) {
        balanceValue += amount.value();
    }

    public void subtract(Amount amount) {
        assert Integer.signum(balanceValue + allowOverdraft - amount.value()) != -1;
        balanceValue -= amount.value();
    }

    public int getValue() {
        return balanceValue;
    }
}
