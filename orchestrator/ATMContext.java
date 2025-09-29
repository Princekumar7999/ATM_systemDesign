package com.scaler.atm.orchestrator;

import com.scaler.atm.bank.BankService;
import com.scaler.atm.hardware.*;
import com.scaler.atm.model.Card;
import com.scaler.atm.state.ATMState;

public interface ATMContext {
    CardReader getCardReader();
    CashDispenser getCashDispenser();
    InputDevice getInputDevice();
    ReceiptPrinter getReceiptPrinter();
    BankService getBankService();

    Card getCurrentCard();
    void setCurrentCard(Card card);

    void setState(ATMState newState);
    void print(String message);
}
