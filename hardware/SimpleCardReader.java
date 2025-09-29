package com.scaler.atm.hardware;

import com.scaler.atm.model.Card;

public class SimpleCardReader implements CardReader {
    @Override
    public boolean validateCard(Card card) {
        return card != null && card.getNumber() != null && card.getNumber().length() >= 8;
    }
}
