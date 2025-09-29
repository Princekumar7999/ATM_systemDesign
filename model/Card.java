package com.scaler.atm.model;

public class Card {
    private final String number;
    private final String holder;

    public Card(String number, String holder) {
        this.number = number;
        this.holder = holder;
    }

    public String getNumber() { return number; }
    public String getHolder() { return holder; }
}
