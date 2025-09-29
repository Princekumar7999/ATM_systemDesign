package com.scaler.atm.bank;

import com.scaler.atm.model.Card;

public interface BankService {
    boolean validatePin(Card card, String pin);
    int getBalance(Card card);
    boolean withdraw(Card card, int amount);
    boolean deposit(Card card, int amount);
    boolean changePin(Card card, String newPin);
    String[] getMiniStatement(Card card, int lastN);
}
