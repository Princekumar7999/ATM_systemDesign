package com.scaler.atm.state;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.strategy.Transaction;

public class ValidatingState implements ATMState {
    @Override
    public void insertCard(ATMContext ctx, com.scaler.atm.model.Card card) {
        ctx.print("Card already inserted. Please enter PIN.");
    }

    @Override
    public void enterPin(ATMContext ctx, String pin) {
        boolean ok = ctx.getBankService().validatePin(ctx.getCurrentCard(), pin);
        if (ok) {
            ctx.print("PIN accepted. Choose transaction.");
            ctx.setState(new AuthenticatedState());
        } else {
            ctx.print("Invalid PIN. Card ejected.");
            ctx.setCurrentCard(null);
            ctx.setState(new ReadyState());
        }
    }

    @Override
    public void processTransaction(ATMContext ctx, Transaction tx) {
        ctx.print("Enter PIN before any transaction.");
    }

    @Override
    public void ejectCard(ATMContext ctx) {
        ctx.print("Card ejected.");
        ctx.setCurrentCard(null);
        ctx.setState(new ReadyState());
    }
}
