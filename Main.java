package com.scaler.atm;

import com.scaler.atm.bank.BankService;
import com.scaler.atm.bank.BankServiceProxy;
import com.scaler.atm.hardware.*;
import com.scaler.atm.model.Card;
import com.scaler.atm.orchestrator.ATM;
import com.scaler.atm.strategy.*;

public class Main {
    public static void main(String[] args) {
        // Hardware
        CardReader cardReader = new SimpleCardReader();
        CashDispenser cashDispenser = new SimpleCashDispenser();
        InputDevice inputDevice = new ScreenAndKeypad();
        ReceiptPrinter receiptPrinter = new SimpleReceiptPrinter();

        // Bank (wrapped by proxy for retries)
        BankService bankService = new BankServiceProxy();

        // ATM orchestrator
        ATM atm = new ATM(cardReader, cashDispenser, inputDevice, receiptPrinter, bankService);

        // Simulate a session
        Card card = new Card("1234-5678-9012-3456", "JOHN DOE");
        atm.insertCard(card);
        atm.enterPin("1234");

        // Transactions via Strategy
        Transaction deposit = new DepositTransaction(200);
        atm.processTransaction(deposit);

        Transaction withdrawal = new WithdrawalTransaction(150);
        atm.processTransaction(withdrawal);

        Transaction balanceInquiry = new BalanceInquiryTransaction();
        atm.processTransaction(balanceInquiry);

        Transaction miniStmt = new MiniStatementTransaction(5);
        atm.processTransaction(miniStmt);

        Transaction changePin = new ChangePinTransaction("4321");
        atm.processTransaction(changePin);

        // Eject card to finish
        atm.ejectCard();

        System.out.println("\n--- ATM demo complete ---");
    }
}
