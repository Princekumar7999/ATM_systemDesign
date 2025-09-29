package com.scaler.atm.state;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.strategy.Transaction;

public class DispensingState implements ATMState {
    @Override
    public void insertCard(ATMContext ctx, com.scaler.atm.model.Card card) {
        ctx.print("Dispensing cash. Please wait.");
    }

    @Override
    public void enterPin(ATMContext ctx, String pin) {
        ctx.print("Dispensing cash. Please wait.");
    }

    @Override
    public void processTransaction(ATMContext ctx, Transaction tx) {
        ctx.print("Dispensing cash. Please wait.");
    }

    @Override
    public void ejectCard(ATMContext ctx) {
        ctx.print("Dispensing cash. Please wait.");
    }
}
