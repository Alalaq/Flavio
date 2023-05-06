package ru.kpfu.itis.khabibullin.utils.enums;

public enum StateOfOrder {
    NOT_CONFIRMED("NOT_CONFIRMED"),
    CONFIRMED("CONFIRMED"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED"),
    FAILED_PAYMENT("FAILED_PAYMENT");

    private final String value;

    StateOfOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
