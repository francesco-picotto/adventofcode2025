# Advent of Code 2025: Day 05 - Clean Code & Architecture Report

This project implements an inventory analysis system for Day 05, focusing on range-based calculations and set theory logic (intersections and unions). The solution utilizes **Domain-Driven Design (DDD)** concepts and the **Strategy Pattern**.

---

## 🏗️ Architectural Overview

The architecture decouples the data model from the analytical logic, allowing for distinct calculation methods for Part 1 and Part 2.


### Key Components
* **`IngredientRange` (Domain Model)**: A Java Record that represents a numeric interval $[start, end]$. It encapsulates interval logic like containment and size calculation.
* **`InventoryAnalyzer` (Strategy Interface)**: Defines the contract for analyzing fresh ranges against available IDs.
* **`StockFreshnessChecker`**: Implements Part 1 logic—filtering specific IDs to see if they fall within any fresh range.
* **`TotalFreshCapacityEstimator`**: Implements Part 2 logic—merging overlapping or adjacent ranges to calculate total unique capacity.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Responsibilities are clearly isolated:
* **`IngredientRange`**: Handles interval mathematics and parsing.
* **`TotalFreshCapacityEstimator`**: Exclusively manages the range-merging algorithm.
* **`Main`**: Manages file I/O and splits the input into logical "blocks".

### 2. Open/Closed Principle (OCP)
The system is open for extension via the `InventoryAnalyzer` interface. Adding a third type of analysis (e.g., finding gaps in inventory) would only require a new class implementation, without modifying `Main.java` or `IngredientRange.java`.

### 3. Interface Segregation & Dependency Inversion
The `Main` class treats all analyzers through the `InventoryAnalyzer` interface. This ensures that the high-level orchestration doesn't depend on the specific algorithmic implementation of the freshness checker or the capacity estimator.

---

## 🧼 Clean Code Highlights

### Range Merging Algorithm
In `TotalFreshCapacityEstimator`, a clear procedural approach is used to merge overlapping intervals. By sorting the ranges first (using the `Comparable` implementation in `IngredientRange`), the complexity of the merge is reduced to $O(n \log n)$.

```java
// Simplified logic for merging adjacent ranges
if (next.start() <= current.end() + 1) {
    current = new IngredientRange(current.start(), Math.max(current.end(), next.end()));
}