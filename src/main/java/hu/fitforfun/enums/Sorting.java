package hu.fitforfun.enums;

public enum Sorting {
    NEWEST("newest"),
    BY_RATE("byRate"),
    PRICE_FORWARD("priceForward"),
    PRICE_BACKWARD("priceBackward");

    private String name;

    Sorting(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
