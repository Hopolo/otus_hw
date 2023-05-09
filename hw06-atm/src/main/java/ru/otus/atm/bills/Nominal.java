package ru.otus.atm.bills;

public enum Nominal {
    FIVE_THOUSANDTH(5000), TWO_THOUSANDTH(2000), ONE_THOUSANDTH(1000), FIVE_HUNDREDTH(500), ONE_HUNDREDTH(100), FIFTYTH(50);
    private final int val;

    Nominal(int val) {
        this.val = val;
    }

    public int getValue() {
        return val;
    }
}
