{{ ... }}

Logo: P

## Overview
This project contains ONLY the ATM example (no Parking Lot). It demonstrates a crisp Java design using SOLID principles along with State, Strategy, and Proxy patterns.
Supported transactions: Withdrawal, Balance Inquiry, Deposit, Change PIN, Mini Statement.

Package root: `src/com/scaler/atm/`

## How to Open in VS Code
- Open the folder: `C:\Users\Scaler\CascadeProjects\atm-java` in VS Code.
- If prompted, install the "Extension Pack for Java" for best experience.

## How to Build and Run
- Using VS Code tasks (recommended):
  - Terminal > Run Task... > `Build (javac)`
  - Terminal > Run Task... > `Run (java)`
- Or with PowerShell from project root:
```powershell
javac -d out $(Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out com.scaler.atm.Main
```

Expected output shows: card insert -> PIN -> deposit -> withdrawal -> balance -> mini statement -> change pin -> eject.

## Class Diagram (ATM)
```mermaid
classDiagram
    class ATM {
{{ ... }}
      +getName()
      +execute(ctx)
    }
    class WithdrawalTransaction
    class BalanceInquiryTransaction
    class DepositTransaction
    class ChangePinTransaction
    class MiniStatementTransaction
    Transaction <|.. WithdrawalTransaction
    Transaction <|.. BalanceInquiryTransaction
    Transaction <|.. DepositTransaction
    Transaction <|.. ChangePinTransaction
    Transaction <|.. MiniStatementTransaction

    class BankService {
      <<interface>>
      +validatePin(Card, String)
      +getBalance(Card)
      +withdraw(Card, int)
      +deposit(Card, int)
      +changePin(Card, String)
      +getMiniStatement(Card, int)
    }
    class BankServiceImpl
    class BankServiceProxy
    BankService <|.. BankServiceImpl
    BankService <|.. BankServiceProxy
{{ ... }}
    ATM --> InputDevice
    ATM --> ReceiptPrinter
```

## Files & Responsibilities
- **`com.scaler.atm.Main`** — Wires dependencies and demonstrates a session by calling `ATM.insertCard()`, `enterPin()`, `processTransaction()` with various strategies, and `ejectCard()`.
- **`com.scaler.atm.orchestrator.ATM`** — Orchestrator that holds references to ports (`CardReader`, `CashDispenser`, `InputDevice`, `ReceiptPrinter`, `BankService`) and current `ATMState`.
  - Methods: `insertCard()`, `enterPin()`, `processTransaction()`, `ejectCard()` delegate to the current `ATMState`.
- **`com.scaler.atm.state.*`** — State pattern.
  - `ReadyState` — Accepts card and moves to `ValidatingState` when card is valid.
  - `ValidatingState` — Checks PIN through `BankService.validatePin()`; on success -> `AuthenticatedState`.
  - `AuthenticatedState` — Accepts `Transaction` strategies and triggers processing (`ProcessingState` then back).
  - `ProcessingState`/`DispensingState` — Intermediate states during execution/dispense.
- **`com.scaler.atm.strategy.*`** — Strategy pattern for transactions.
  - `WithdrawalTransaction.execute()` — Calls `BankService.withdraw()`, dispenses, prints receipt.
  - `BalanceInquiryTransaction.execute()` — Prints current balance from `BankService.getBalance()`.
  - `DepositTransaction.execute()` — Calls `BankService.deposit()` and prints receipt.
  - `ChangePinTransaction.execute()` — Calls `BankService.changePin()` and prints receipt.
  - `MiniStatementTransaction.execute()` — Uses `BankService.getMiniStatement()` and prints lines.
- **`com.scaler.atm.bank.*`** — Bank abstraction, in-memory impl, proxy.
  - `BankService` — Interface following DIP; easily swappable.
  - `BankServiceImpl` — In-memory balances, pins, and statements to keep example simple.
  - `BankServiceProxy` — Adds retry policy (Proxy pattern) around the service.
- **`com.scaler.atm.hardware.*`** — Ports and minimal adapters: `CardReader`, `CashDispenser`, `InputDevice`, `ReceiptPrinter` and simple console implementations.
- **`com.scaler.atm.model.Card`** — Card DTO.

## SOLID Principles (Where Applied)
- **Single Responsibility** — Each class has one reason to change (e.g., `ATM` orchestrates, transactions encapsulate business rules, bank/hardware are separate concerns).
- **Open/Closed** — Add new transactions by implementing `Transaction` without modifying orchestrator or states.
- **Liskov Substitution** — Any `ATMState` or `Transaction` implementation can be swapped via their interfaces.
- **Interface Segregation** — Small, focused hardware interfaces avoid fat contracts.
- **Dependency Inversion** — `ATM` depends on abstractions (`BankService`, hardware ports), allowing easy substitution.

## Design Patterns (Why)
- **State** — Removes complex conditionals in `ATM` by delegating behavior to `ATMState` implementations.
- **Strategy** — Each transaction is a strategy; adding a new one is isolated and does not affect others.
- **Proxy** — `BankServiceProxy` centralizes cross-cutting concerns (retry, potential logging) around the bank calls.

## Quick Function Guide
- **`ATM.insertCard(Card)`** — Validates card through `CardReader`, sets current card, transitions to PIN validation.
- **`ATM.enterPin(String)`** — Delegates to state; typically calls `BankService.validatePin()`.
- **`ATM.processTransaction(transaction)`** — Delegates to state; executes the passed strategy.
- **`ATM.ejectCard()`** — Cleans session and returns to `ReadyState`.
- **`WithdrawalTransaction.execute(ctx)`** — Bank withdraw -> `CashDispenser.dispense()` -> print receipt.
- **`DepositTransaction.execute(ctx)`** — Bank deposit -> print receipt.
- **`BalanceInquiryTransaction.execute(ctx)`** — Print balance.
- **`ChangePinTransaction.execute(ctx)`** — Bank change pin -> print receipt.
- **`MiniStatementTransaction.execute(ctx)`** — Print last N entries.

## Extending
- Add `TransferTransaction` by implementing `Transaction` and adding corresponding methods in `BankService`/impl.
- Swap hardware with other implementations without touching `ATM`.
CardReader`, `CashDispenser`, etc.
