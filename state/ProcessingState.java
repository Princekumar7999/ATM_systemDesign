package com.scaler.atm.state;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.strategy.Transaction;

public class ProcessingState implements ATMState {
    @Override
    public void insertCard(ATMContext ctx, com.scaler.atm.model.Card card) {
        ctx.print("Currently processing. Please wait.");
    }

    @Override
    public void enterPin(ATMContext ctx, String pin) {
        ctx.print("Currently processing. Please wait.");
    }

    @Override
    public void processTransaction(ATMContext ctx, Transaction tx) {
        ctx.print("Already processing a transaction.");
    }

    @Override
    public void ejectCard(ATMContext ctx) {
        ctx.print("Cannot eject during processing.");
    }
}
