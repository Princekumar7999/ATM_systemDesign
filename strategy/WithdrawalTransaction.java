package com.scaler.atm.strategy;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.state.AuthenticatedState;
import com.scaler.atm.state.DispensingState;

public class WithdrawalTransaction implements Transaction {
    private final int amount;

    public WithdrawalTransaction(int amount) { this.amount = amount; }

    @Override
    public String getName() { return "Withdrawal"; }

    @Override
    public void execute(ATMContext ctx) {
        boolean ok = ctx.getBankService().withdraw(ctx.getCurrentCard(), amount);
        if (!ok) {
            ctx.print("Withdrawal failed (insufficient funds or error).\n");
            ctx.setState(new AuthenticatedState());
            return;
        }
        ctx.print("Withdrawing: " + amount);
        ctx.setState(new DispensingState());
        ctx.getCashDispenser().dispense(amount);
        ctx.getReceiptPrinter().print("Withdrawal of Rs." + amount + " successful.");
        ctx.print("Transaction complete.\n");
        ctx.setState(new AuthenticatedState());
    }
}
