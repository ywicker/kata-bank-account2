package kata.lacombe;

public enum OperationType {
    DEPOSIT {
        public int amountToApply(final PositiveAmount amount) {
            return amount.value();
        }
    },
    WITHDRAWAL {
        public int amountToApply(final PositiveAmount amount) {
            return -amount.value();
        }
    };

    public int amountToApply(final PositiveAmount amount) {
        return amount.value();
    }
}
