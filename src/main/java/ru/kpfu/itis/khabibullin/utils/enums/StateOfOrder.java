/*
 * *
 *  Copyright (c) 2023.
 *  * @author Khabibullin Alisher (Alalaq)
 *  *
 *  * All rights are reserved by ITIS institute.
 *
 */

package ru.kpfu.itis.khabibullin.utils.enums;
/**
 * @author Khabibullin Alisher
 */
public enum StateOfOrder {
    NOT_CONFIRMED("Not confirmed"),
    CONFIRMED("Confirmed"),
    DELIVERED("Delivered"),
    CANCELED("Canceled"),
    FAILED_PAYMENT("Failed payment"),
    REVIEWED("Reviewed");

    private final String value;

    StateOfOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
