# Advent of Code 2025: Day 04 - Clean Code & Architecture Report

This project solves the Day 04 challenge involving grid manipulation and iterative removal rules. The solution emphasizes **composability**, **encapsulation**, and the **Strategy Pattern**.

---

## 🏗️ Architectural Overview

The architecture is built around a grid-based simulation where different "removal rules" determine how the system evolves.


### Key Components
* **`Grid` (Data Wrapper)**: Encapsulates a 2D `char` array, providing safe accessors and spatial logic (like neighbor counting).
* **`RemovalRule` (Interface)**: Defines the strategy for modifying the grid and returning the number of modifications.
* **`BasicRemovalRule`**: Implements a single pass of the removal logic based on neighbor density.
* **`AdvancedRemovalRule`**: Uses **composition** to wrap the basic rule, applying it iteratively until no more removals occur.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Responsibilities are strictly divided:
* **`Grid`**: Handles coordinate validation and spatial queries (neighbor counts).
* **`BasicRemovalRule`**: Logic for a single state transition (identification and then removal).
* **`AdvancedRemovalRule`**: Logic for recursion/iteration control.
* **`Main`**: Infrastructure tasks like file loading and execution flow.

### 2. Open/Closed Principle (OCP)
The system is highly extensible:
* To change the removal criteria (e.g., from `< 4` neighbors to something else), one could extend `BasicRemovalRule` and override `shouldRemove` without touching the iteration logic in `AdvancedRemovalRule`.

### 3. Liskov Substitution Principle (LSP)
The `solve` method in `Main` accepts any `RemovalRule`. Because `AdvancedRemovalRule` and `BasicRemovalRule` both strictly adhere to the `apply(char[][] grid)` contract, they are interchangeable from the perspective of the caller.

---

## 🧼 Clean Code Highlights

### Composition over Inheritance
Instead of duplicating the removal logic, `AdvancedRemovalRule` contains an instance of `BasicRemovalRule`. This demonstrates the **Decorator**-like behavior where the advanced rule "decorates" the basic rule with iterative capabilities.

```java
// Logic from AdvancedRemovalRule.java
while((currentBatch = basicRule.apply(grid)) > 0) {
    count += currentBatch;
}