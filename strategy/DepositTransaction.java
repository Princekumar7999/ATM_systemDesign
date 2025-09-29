package com.scaler.atm.strategy;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.state.AuthenticatedState;

public class DepositTransaction implements Transaction {
    private final int amount;

    public DepositTransaction(int amount) { this.amount = amount; }

    @Override
    public String getName() { return "Deposit"; }

    @Override
    public void execute(ATMContext ctx) {
        boolean ok = ctx.getBankService().deposit(ctx.getCurrentCard(), amount);
        if (!ok) {
            ctx.print("Deposit failed.\n");
            ctx.setState(new AuthenticatedState());
            return;
        }
        ctx.getReceiptPrinter().print("Deposit of Rs." + amount + " successful.");
        ctx.print("Transaction complete.\n");
        ctx.setState(new AuthenticatedState());
    }
}
