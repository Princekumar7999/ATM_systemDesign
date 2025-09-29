package com.scaler.atm.strategy;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.state.AuthenticatedState;

public class ChangePinTransaction implements Transaction {
    private final String newPin;

    public ChangePinTransaction(String newPin) { this.newPin = newPin; }

    @Override
    public String getName() { return "Change PIN"; }

    @Override
    public void execute(ATMContext ctx) {
        boolean ok = ctx.getBankService().changePin(ctx.getCurrentCard(), newPin);
        if (!ok) {
            ctx.print("PIN change failed.\n");
            ctx.setState(new AuthenticatedState());
            return;
        }
        ctx.getReceiptPrinter().print("PIN changed successfully.");
        ctx.print("Transaction complete.\n");
        ctx.setState(new AuthenticatedState());
    }
}
