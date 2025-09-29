package com.scaler.atm.strategy;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.state.AuthenticatedState;

public class BalanceInquiryTransaction implements Transaction {
    @Override
    public String getName() { return "Balance Inquiry"; }

    @Override
    public void execute(ATMContext ctx) {
        int balance = ctx.getBankService().getBalance(ctx.getCurrentCard());
        ctx.getReceiptPrinter().print("Balance: Rs." + balance);
        ctx.print("Balance inquiry complete.\n");
        ctx.setState(new AuthenticatedState());
    }
}
