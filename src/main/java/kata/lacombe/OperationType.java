package kata.lacombe;

public enum OperationType {
    DEPOSIT {
        public int amountToApply(final Amount amount) {
            return amount.value();
        }
    },
    WITHDRAWAL {
        public int amountToApply(final Amount amount) {
            return -amount.value();
        }
    };

    public int amountToApply(final Amount amount) {
        return amount.value();
    }
}
