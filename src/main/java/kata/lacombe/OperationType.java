package kata.lacombe;

public enum OperationType {
    DEPOSIT {
        public Amount amountToApply(final PositiveAmount amount) {
            return amount;
        }
    },
    WITHDRAWAL {
        public Amount amountToApply(final PositiveAmount amount) {
            return new Amount(-amount.value());
        }
    };

    public Amount amountToApply(final PositiveAmount amount) {
        return amount;
    }
}
