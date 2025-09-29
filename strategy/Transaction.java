package com.scaler.atm.strategy;

import com.scaler.atm.orchestrator.ATMContext;

public interface Transaction {
    String getName();
    void execute(ATMContext ctx);
}
