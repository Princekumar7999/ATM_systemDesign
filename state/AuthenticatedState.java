package com.scaler.atm.state;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.strategy.Transaction;

public class AuthenticatedState implements ATMState {
    @Override
    public void insertCard(ATMContext ctx, com.scaler.atm.model.Card card) {
        ctx.print("Card already inserted.");
    }

    @Override
    public void enterPin(ATMContext ctx, String pin) {
        ctx.print("PIN already verified.");
    }

    @Override
    public void processTransaction(ATMContext ctx, Transaction tx) {
        ctx.print("Processing transaction: " + tx.getName());
        ctx.setState(new ProcessingState());
        tx.execute(ctx);
    }

    @Override
    public void ejectCard(ATMContext ctx) {
        ctx.print("Card ejected. Thank you.");
        ctx.setCurrentCard(null);
        ctx.setState(new ReadyState());
    }
}
