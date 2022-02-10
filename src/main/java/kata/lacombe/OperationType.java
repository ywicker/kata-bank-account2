package kata.lacombe;

public enum OperationType {
    DEPOSIT {
        public Amount amountToApply(final Amount amount) {
            return amount;
        }
    },
    WITHDRAWAL {
        public Amount amountToApply(final Amount amount) {
            return amount.opposite();
        }
    };

    public Amount amountToApply(final Amount amount) {
        return amount;
    }
}
