package com.scaler.atm.state;

import com.scaler.atm.model.Card;
import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.strategy.Transaction;

public class ReadyState implements ATMState {
    @Override
    public void insertCard(ATMContext ctx, Card card) {
        if (ctx.getCardReader().validateCard(card)) {
            ctx.setCurrentCard(card);
            ctx.print("Card accepted. Please enter PIN.");
            ctx.setState(new ValidatingState());
        } else {
            ctx.print("Invalid card. Please try another card.");
        }
    }

    @Override
    public void enterPin(ATMContext ctx, String pin) {
        ctx.print("No card inserted.");
    }

    @Override
    public void processTransaction(ATMContext ctx, Transaction tx) {
        ctx.print("Insert card first.");
    }

    @Override
    public void ejectCard(ATMContext ctx) {
        ctx.print("No card to eject.");
    }
}
