# Advent of Code 2025: Day 01 - Clean Code & Architecture Report

This project implements a solution for the Day 01 challenge using **SOLID principles** and the **Strategy Design Pattern** to ensure the code is maintainable, readable, and easily extensible.

---

## 🏗️ Architectural Overview

The core architecture follows a **Decomposition Strategy** where state management is separated from business logic variations.

### The Strategy Pattern
To handle the differing requirements between Part 1 and Part 2 of the challenge, I implemented the **Strategy Pattern**.
* **`PasswordStrategy` Interface**: Defines the contract for counting zeros during a dial rotation.
* **`BasicStrategy`**: Implements the logic for Part 1, calculating if the final position lands on zero.
* **`AdvancedStrategy`**: Implements the logic for Part 2, counting every time the dial passes through zero during its movement.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Each class in the system has one clear reason to change:
* **`Dial`**: Manages only the state of the dial (current position) and the mechanics of rotation.
* **`Main`**: Handles file I/O, input parsing, and execution orchestration.
* **`BasicStrategy` / `AdvancedStrategy`**: Contain only the specific mathematical logic for the puzzle parts.

### 2. Open/Closed Principle (OCP)
The system is **open for extension** but **closed for modification**.
* I can add a new calculation logic (e.g., a "Part 3") by creating a new class that implements `PasswordStrategy`.
* This extension requires zero changes to the `Dial` class or the existing strategy classes.

### 3. Dependency Inversion Principle (DIP)
High-level modules do not depend on low-level modules; both depend on abstractions.
* The `Dial` class depends on the `PasswordStrategy` interface rather than concrete implementations.
* The specific strategy is "injected" into the `rotate` method at runtime.

---

## 🧼 Clean Code Highlights

### Meaningful Abstractions
Instead of using magic numbers, I used constants and well-named methods:
* **`SIZE = 100`**: Defines the dial boundary in one central location.
* **`calculateNewPosition`**: A private helper method that clarifies the coordinate wrapping logic ($mod\ 100$).

### Declarative Execution
In the `Main` class, I utilized the **Java Stream API** to solve the puzzle:
```java
private static int solve(List<String> instructions, PasswordStrategy strategy) {