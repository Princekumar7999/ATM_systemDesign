package com.scaler.atm.strategy;

import com.scaler.atm.orchestrator.ATMContext;
import com.scaler.atm.state.AuthenticatedState;

public class MiniStatementTransaction implements Transaction {
    private final int lastN;

    public MiniStatementTransaction(int lastN) { this.lastN = lastN; }

    @Override
    public String getName() { return "Mini Statement"; }

    @Override
    public void execute(ATMContext ctx) {
        String[] lines = ctx.getBankService().getMiniStatement(ctx.getCurrentCard(), lastN);
        StringBuilder sb = new StringBuilder();
        sb.append("Mini Statement (last ").append(lastN).append(")\n");
        for (String l : lines) sb.append(l).append("\n");
        ctx.getReceiptPrinter().print(sb.toString());
        ctx.print("Transaction complete.\n");
        ctx.setState(new AuthenticatedState());
    }
}
