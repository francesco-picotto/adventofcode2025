# Advent of Code 2025: Day 10 - Clean Code & Architecture Report

This project provides a sophisticated solution for the Day 10 challenge, which involves finding optimal button-press sequences to reach specific machine states. The architecture demonstrates advanced use of **Recursion with Memoization**, **Breadth-First Search (BFS)**, and **Regular Expression-based Parsing**.

---

## 🏗️ Architectural Overview

The system is designed as a modular solver framework where a single `Machine` definition is processed by different algorithmic strategies to solve distinct puzzle requirements.



### Key Components
* **`Machine` (Domain Model)**: A Java Record representing the machine's initial state, including target light configurations, available buttons, and target "joltage" levels.
* **`MachineSolver` (Interface)**: The strategy abstraction that allows the system to solve for different goals using a unified execution pipeline.
* **`InputParser`**: A specialized utility that uses Regex patterns to decompose complex string inputs into structured domain objects.
* **Concrete Solvers**:
    * **`LightConfigurationSolver`**: Uses **BFS** to find the shortest path to a binary light configuration.
    * **`JoltageSolver`**: Implements a **recursive top-down DP** (Dynamic Programming) approach with memoization to solve for numerical targets.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Responsibilities are strictly isolated to manage the high complexity of the puzzle:
* **`InputParser`**: Handles the mapping of raw text to domain objects.
* **`Machine`**: Acts as a pure data container.
* **`JoltageSolver`**: Only contains the logic for numerical state reduction and bitmask application.

### 2. Open/Closed Principle (OCP)
The architecture is **open for extension**. New machine behaviors or constraints can be added by implementing the `MachineSolver` interface without modifying the `Main` class or the existing solvers.

### 3. Dependency Inversion Principle (DIP)
The `Main` class orchestrates the solution by depending on the `MachineSolver` abstraction. This allows it to compute multiple puzzle parts by passing different solver implementations into a shared `solveAll` method.

---

## 🧼 Clean Code Highlights

### Advanced Pattern Matching
The `InputParser` uses a centralized `COMPONENT_PATTERN` to identify disparate block types (brackets, parentheses, and braces) in a single pass. This eliminates fragile string splitting and makes the parser resilient to varied input formats.

```java
// Logic from InputParser.java
private static final Pattern COMPONENT_PATTERN = 
    Pattern.compile("\\[[.#]+\\]|\\([^)]+\\)|\\{[^}]+\\}");