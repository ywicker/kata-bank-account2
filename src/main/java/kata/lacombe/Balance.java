package kata.lacombe;

public class Balance {
    private int balanceValue;

    public Balance(final int balanceValue) {
        this.balanceValue = balanceValue;
    }

    public Balance(final int balance, final int allowOverdraft) {
    }

    public void add(Amount amount) {
        balanceValue += amount.value();
    }

    public void substract(Amount amount) {
    }

    public int getValue() {
        return balanceValue;
    }
}
