package com.scaler.atm.bank;

import com.scaler.atm.model.Card;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class BankServiceImpl implements BankService {
    private final Map<String, Integer> balances = new HashMap<>();
    private final Map<String, String> pins = new HashMap<>();
    private final Map<String, List<String>> statements = new HashMap<>();

    public BankServiceImpl() {
        String cardNo = "1234-5678-9012-3456";
        balances.put(cardNo, 1000);
        pins.put(cardNo, "1234");
        statements.put(cardNo, new ArrayList<>());
        statements.get(cardNo).add("OPEN: Balance Rs.1000");
    }

    @Override
    public boolean validatePin(Card card, String pin) {
        if (card == null || pin == null) return false;
        String actual = pins.get(card.getNumber());
        return pin.equals(actual);
    }

    @Override
    public int getBalance(Card card) {
        return balances.getOrDefault(card.getNumber(), 0);
    }

    @Override
    public boolean withdraw(Card card, int amount) {
        String key = card.getNumber();
        int bal = balances.getOrDefault(key, 0);
        if (amount <= 0 || amount > bal) return false;
        balances.put(key, bal - amount);
        statements.computeIfAbsent(key, k -> new ArrayList<>())
                .add("WITHDRAW: Rs." + amount + ", Bal: Rs." + (bal - amount));
        return true;
    }

    @Override
    public boolean deposit(Card card, int amount) {
        String key = card.getNumber();
        int bal = balances.getOrDefault(key, 0);
        if (amount <= 0) return false;
        balances.put(key, bal + amount);
        statements.computeIfAbsent(key, k -> new ArrayList<>())
                .add("DEPOSIT: Rs." + amount + ", Bal: Rs." + (bal + amount));
        return true;
    }

    @Override
    public boolean changePin(Card card, String newPin) {
        if (card == null || newPin == null || newPin.isEmpty()) return false;
        pins.put(card.getNumber(), newPin);
        statements.computeIfAbsent(card.getNumber(), k -> new ArrayList<>())
                .add("PIN CHANGE");
        return true;
    }

    @Override
    public String[] getMiniStatement(Card card, int lastN) {
        List<String> list = statements.getOrDefault(card.getNumber(), new ArrayList<>());
        int size = list.size();
        int from = Math.max(0, size - Math.max(0, lastN));
        List<String> sub = list.subList(from, size);
        return sub.toArray(new String[0]);
    }
}
