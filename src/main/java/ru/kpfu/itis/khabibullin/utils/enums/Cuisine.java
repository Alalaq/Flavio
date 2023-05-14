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
public enum Cuisine {
    AMERICAN("AMERICAN"),
    CHINESE("CHINESE"),
    FRENCH("FRENCH"),
    INDIAN("INDIAN"),
    ITALIAN("ITALIAN"),
    JAPANESE("JAPANESE"),
    KOREAN("KOREAN"),
    MEXICAN("MEXICAN"),
    GEORGIAN("GEORGIAN"),
    RUSSIAN("RUSSIAN");

    private final String name;

    Cuisine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
