package ru.kpfu.itis.khabibullin.utils;

public enum Cuisine {
    AMERICAN("American"),
    CHINESE("Chinese"),
    FRENCH("French"),
    INDIAN("Indian"),
    ITALIAN("Italian"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    MEXICAN("Mexican"),
    GEORGIAN("Georgian"),
    RUSSIAN("Russian");

    private final String name;

    Cuisine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
