# Advent of Code 2025: Day 06 - Clean Code & Architecture Report

This project implements a grid-based mathematical parser for Day 06, designed to handle vertical worksheets where numbers and operators are arranged in columns. The architecture utilizes **Template Method**, **Strategy**, and **Enums as Functional Objects**.

---

## 🏗️ Architectural Overview

The solution is built on a flexible analysis framework that handles different scanning directions (Standard vs. Reverse) using an inheritance hierarchy and specialized data structures.


### Key Components
* **`MathWorksheetAnalyzer` (Interface)**: Defines the high-level contract for analyzing the input worksheet.
* **`AbstractWorksheetAnalyzer` (Base Class)**: Implements the **Template Method** pattern by providing shared protected helper methods (`isColumnEmpty`, `getColumnPart`) used by all scanning implementations.
* **`StandardColumnAnalyzer` & `ReverseVerticalAnalyzer`**: Concrete implementations that define the specific scanning direction and problem extraction logic.
* **`CephalopodProblem` (Record)**: A lightweight domain object that pairs a list of operands with an operator.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Responsibilities are clearly isolated to prevent logic leaking:
* **`Operator`**: Encapsulates the mathematical behavior and identity values for each symbol.
* **`CephalopodProblem`**: Responsible only for triggering the calculation of its own data.
* **`AbstractWorksheetAnalyzer`**: Centralizes the logic for grid navigation and boundary detection.

### 2. Open/Closed Principle (OCP)
The system is designed to handle new worksheet formats without modifying existing code.
* To add a "Diagonal" analyzer, you would simply extend `AbstractWorksheetAnalyzer`.
* To add a "Power" operator, you only need to add an entry to the `Operator` enum without touching the parsing logic.

### 3. Liskov Substitution Principle (LSP)
The `Main` class treats both `StandardColumnAnalyzer` and `ReverseVerticalAnalyzer` as instances of `MathWorksheetAnalyzer`. Both implementations fulfill the contract, allowing the orchestrator to process the same input using different strategies interchangeably.

---

## 🧼 Clean Code Highlights

### Enums with Behavior
The `Operator` enum is a prime example of clean code, where each constant carries its own behavior (as a `BinaryOperator`) and its identity element for stream reduction. This eliminates `switch` statements or `if-else` blocks during calculation.

```java
public enum Operator {
    ADD('+', (a, b) -> a + b, 0L),
    MULTIPLY('*', (a, b) -> a * b, 1L);

    public long apply(List<Long> numbers) {
        return numbers.stream().reduce(identity, operation);
    }
}