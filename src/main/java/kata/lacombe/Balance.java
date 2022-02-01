package kata.lacombe;

public class Balance {
    private int balanceValue;

    public Balance() {
    }
    public Balance(final int balanceValue) {
        this.balanceValue = balanceValue;
    }

    public Balance(final int balance, final int allowOverdraft) {
    }

    public void add(Amount amount) {
        balanceValue += amount.value();
    }

    public void substract(Amount amount) {
        assert Integer.signum(balanceValue - amount.getValue()) != -1;
        balanceValue -= amount.value();
    }

    public int getValue() {
        return balanceValue;
    }
}
