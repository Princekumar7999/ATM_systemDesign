package com.scaler.atm.state;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.model.Card;
import com.scaler.atm.strategy.Transaction;

public interface ATMState {
    void insertCard(ATMContext ctx, Card card);
    void enterPin(ATMContext ctx, String pin);
    void processTransaction(ATMContext ctx, Transaction tx);
    void ejectCard(ATMContext ctx);
}
