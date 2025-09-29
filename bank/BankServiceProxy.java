package com.scaler.atm.bank;

import com.scaler.atm.model.Card;

public class BankServiceProxy implements BankService {
    private final BankService target = new BankServiceImpl();
    private final int maxRetries = 2;

    @Override
    public boolean validatePin(Card card, String pin) {
        return execWithRetry(() -> target.validatePin(card, pin));
    }

    @Override
    public int getBalance(Card card) {
        Integer value = execWithRetry(() -> target.getBalance(card));
        return value == null ? 0 : value;
    }

    @Override
    public boolean withdraw(Card card, int amount) {
        return execWithRetry(() -> target.withdraw(card, amount));
    }

    @Override
    public boolean deposit(Card card, int amount) {
        return execWithRetry(() -> target.deposit(card, amount));
    }

    @Override
    public boolean changePin(Card card, String newPin) {
        return execWithRetry(() -> target.changePin(card, newPin));
    }

    @Override
    public String[] getMiniStatement(Card card, int lastN) {
        return execWithRetry(() -> target.getMiniStatement(card, lastN));
    }

    private <T> T execWithRetry(SupplierWithException<T> supplier) {
        int attempts = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (RuntimeException ex) {
                attempts++;
                if (attempts > maxRetries) throw ex;
            }
        }
    }

    @FunctionalInterface
    interface SupplierWithException<T> { T get(); }
}
