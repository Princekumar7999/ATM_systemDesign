package com.scaler.atm.hardware;

public class SimpleCashDispenser implements CashDispenser {
    @Override
    public void dispense(int amount) {
        System.out.println("[CashDispenser] Dispensing Rs." + amount);
    }
}
