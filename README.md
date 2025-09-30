## Overview
This project contains ONLY the ATM system design. It demonstrates a crisp Java design using SOLID principles along with State, Strategy, and Proxy patterns.  
Supported transactions: Withdrawal, Balance Inquiry, Deposit, Change PIN, Mini Statement.

## How to Open in VS Code
Install the "Extension Pack for Java" for best experience.

## How to Build and Run
- Using VS Code tasks (recommended):
  - Terminal > Run Task... > `Build (javac)`
  - Terminal > Run Task... > `Run (java)`
 
 Files & Responsibilities

atm.Main — Wires dependencies and demonstrates a session by calling ATM.insertCard(), enterPin(), processTransaction() with various strategies, and ejectCard().

atm.orchestrator.ATM — Orchestrator that holds references to ports (CardReader, CashDispenser, InputDevice, ReceiptPrinter, BankService) and current ATMState.

Methods: insertCard(), enterPin(), processTransaction(), ejectCard() delegate to the current ATMState.

atm.state. — State pattern.

ReadyState — Accepts card and moves to ValidatingState when card is valid.

ValidatingState — Checks PIN through BankService.validatePin(); on success -> AuthenticatedState.

AuthenticatedState — Accepts Transaction strategies and triggers processing (ProcessingState then back).

ProcessingState/DispensingState — Intermediate states during execution/dispense.

atm.strategy. — Strategy pattern for transactions.

WithdrawalTransaction.execute() — Calls BankService.withdraw(), dispenses, prints receipt.

BalanceInquiryTransaction.execute() — Prints current balance from BankService.getBalance().

DepositTransaction.execute() — Calls BankService.deposit() and prints receipt.

ChangePinTransaction.execute() — Calls BankService.changePin() and prints receipt.

MiniStatementTransaction.execute() — Uses BankService.getMiniStatement() and prints lines.

atm.bank. — Bank abstraction, in-memory impl, proxy.

BankService — Interface following DIP; easily swappable.

BankServiceImpl — In-memory balances, pins, and statements to keep example simple.

BankServiceProxy — Adds retry policy (Proxy pattern) around the service.

atm.hardware. — Ports and minimal adapters: CardReader, CashDispenser, InputDevice, ReceiptPrinter and simple console implementations.

atm.model.Card — Card DTO.

SOLID Principles (Where Applied)

Single Responsibility — Each class has one reason to change (e.g., ATM orchestrates, transactions encapsulate business rules, bank/hardware are separate concerns).

Open/Closed — Add new transactions by implementing Transaction without modifying orchestrator or states.

Liskov Substitution — Any ATMState or Transaction implementation can be swapped via their interfaces.

Interface Segregation — Small, focused hardware interfaces avoid fat contracts.

Dependency Inversion — ATM depends on abstractions (BankService, hardware ports), allowing easy substitution.

Design Patterns (Why)

State — Removes complex conditionals in ATM by delegating behavior to ATMState implementations.

Strategy — Each transaction is a strategy; adding a new one is isolated and does not affect others.

Proxy — BankServiceProxy centralizes cross-cutting concerns (retry, potential logging) around the bank calls.

Quick Function Guide

ATM.insertCard(Card) — Validates card through CardReader, sets current card, transitions to PIN validation.

ATM.enterPin(String) — Delegates to state; typically calls BankService.validatePin().

ATM.processTransaction(transaction) — Delegates to state; executes the passed strategy.

ATM.ejectCard() — Cleans session and returns to ReadyState.

WithdrawalTransaction.execute(ctx) — Bank withdraw -> CashDispenser.dispense() -> print receipt.

DepositTransaction.execute(ctx) — Bank deposit -> print receipt.

BalanceInquiryTransaction.execute(ctx) — Print balance.

ChangePinTransaction.execute(ctx) — Bank change pin -> print receipt.

MiniStatementTransaction.execute(ctx) — Print last N entries.


# ATM System Design - Class Diagram

```mermaid
classDiagram
    class ATMState {
        +insertCard(c: Card)
        +ejectCard()
        +insertPin(pin: String, atm: ATM)
        +withdrawCash(amount: int, atm: ATM)
    }
    ATMState <|-- ReadyState
    ATMState <|-- ValidationState
    ATMState <|-- AuthenticationState
    ATMState <|-- ProcessState
    ATMState <|-- DispenseState

    class Transaction {
        +getAmount() int
        +setAmount(a: int)
    }
    Transaction <|-- WithdrawalTransaction
    Transaction <|-- BalanceInquiryTransaction
    Transaction <|-- DepositTransaction
    Transaction <|-- ChangePinTransaction
    Transaction <|-- MiniStatementTransaction

    class ATM {
        -ATMState state
        -Customer currentCust
        -Card currentCard
        -Transaction currentTxn
        +insertCard(c: Card)
        +ejectCard()
    }
    ATM --> ATMState
    ATM --> Card
    ATM --> Transaction
    ATM --> CashDispenser
    ATM --> InputReader
    ATM --> ReceiptPrinter

    class BankService {
        +validateCard(c: Card)
        +validatePin(c: Card, pin: String)
        +getBalance(c: Card)
        +withdraw(c: Card, amt: int)
        +deposit(c: Card, amt: int)
        +changePin(c: Card, pin: String)
        +getMiniStatement(c: Card)
    }
    BankService <|-- BankServiceImpl
    BankService <|-- BankServiceProxy



