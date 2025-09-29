package com.scaler.atm.hardware;

import com.scaler.atm.model.Card;

public interface CardReader {
    boolean validateCard(Card card);
}
