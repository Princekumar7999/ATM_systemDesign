package com.scaler.atm.orchestrator;

import com.scaler.atm.bank.BankService;
import com.scaler.atm.hardware.*;
import com.scaler.atm.model.Card;
import com.scaler.atm.state.*;
import com.scaler.atm.strategy.Transaction;

public class ATM implements ATMContext {
    private final CardReader cardReader;
    private final CashDispenser cashDispenser;
    private final InputDevice inputDevice;
    private final ReceiptPrinter receiptPrinter;
    private final BankService bankService;

    private ATMState state;
    private Card currentCard;

    public ATM(CardReader cardReader,
               CashDispenser cashDispenser,
               InputDevice inputDevice,
               ReceiptPrinter receiptPrinter,
               BankService bankService) {
        this.cardReader = cardReader;
        this.cashDispenser = cashDispenser;
        this.inputDevice = inputDevice;
        this.receiptPrinter = receiptPrinter;
        this.bankService = bankService;
        this.state = new ReadyState();
    }

    // Public API orchestrating the session
    public void insertCard(Card card) {
        state.insertCard(this, card);
    }

    public void enterPin(String pin) {
        state.enterPin(this, pin);
    }

    public void processTransaction(Transaction transaction) {
        state.processTransaction(this, transaction);
    }

    public void ejectCard() {
        state.ejectCard(this);
    }

    // Context interface methods
    @Override
    public CardReader getCardReader() { return cardReader; }

    @Override
    public CashDispenser getCashDispenser() { return cashDispenser; }

    @Override
    public InputDevice getInputDevice() { return inputDevice; }

    @Override
    public ReceiptPrinter getReceiptPrinter() { return receiptPrinter; }

    @Override
    public BankService getBankService() { return bankService; }

    @Override
    public Card getCurrentCard() { return currentCard; }

    @Override
    public void setCurrentCard(Card card) { this.currentCard = card; }

    @Override
    public void setState(ATMState newState) { this.state = newState; }

    @Override
    public void print(String message) { System.out.println(message); }
}
