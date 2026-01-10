# Advent of Code 2025: Day 03 - Clean Code & Architecture Report

This project implements a solution for the Day 03 challenge, focusing on extracting specific numeric values from "bank" strings using different algorithmic complexities.

---

## 🏗️ Architectural Overview

The architecture utilizes the **Strategy Design Pattern** to manage different "rules" for bank evaluation. This separates the iteration and summation logic from the specific string-parsing algorithms.



### Key Components
* **`BankRule` (Interface)**: Acts as the strategy abstraction, defining the `evaluate` method and providing a shared utility for index finding.
* **`SimpleBankRule`**: Implements Part 1 logic by finding two specific maxima in the string.
* **`AdvancedBankRule`**: Implements Part 2 logic using a sliding window and an iterative approach to find a 12-digit sequence.
* **`Main`**: The execution context that orchestrates input loading and result calculation via Java Streams.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
The system is decomposed into highly focused units:
* **`BankRule`**: Defines the "what" (evaluating a bank) and provides the "how" for common sub-tasks like `findMaxIndex`.
* **`AdvancedBankRule`**: Focused entirely on the greedy selection algorithm for the 12-digit "joltage" string.
* **`Main`**: Responsible only for file I/O and reporting results.

### 2. Open/Closed Principle (OCP)
The code is **open for extension** but **closed for modification**. New bank evaluation algorithms can be introduced by creating new classes implementing `BankRule` without altering the `solve` logic in the `Main` class.

### 3. Dependency Inversion Principle (DIP)
The `solve` method in `Main` does not depend on concrete implementations like `SimpleBankRule`. Instead, it depends on the `BankRule` interface, allowing for polymorphic behavior at runtime.

---

## 🧼 Clean Code Highlights

### Interface Default/Static Utilities
To avoid code duplication, the `findMaxIndex` logic is placed inside the `BankRule` interface as a static method. This allows both `SimpleBankRule` and `AdvancedBankRule` to reuse the same robust index-finding logic without inheritance.

```java
static int findMaxIndex(String s, int start, int end) {
    int maxIdx = start;
    for (int i = start + 1; i <= end; i++) {
        if (s.charAt(i) > s.charAt(maxIdx)) {
            maxIdx = i;
        }
    }
    return maxIdx;
}