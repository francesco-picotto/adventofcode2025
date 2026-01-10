# Advent of Code 2025: Day 12 - Clean Code & Architecture Report

This project implements a sophisticated 2D tiling and packing solution for Day 12. The architecture utilizes a **Backtracking Search Engine**, **Geometric Normalization**, and **Parallel Stream Processing** to solve complex spatial optimization problems.

---

## 🏗️ Architectural Overview

The system is designed to determine if a set of polyomino-like shapes can be perfectly packed into a rectangular region. It separates the physical representation of space from the algorithmic "fitting" logic.


### Key Components
* **`Point` & `Shape` (Geometric Domain)**: Primitive records that handle spatial transformations like rotation, flipping, translation, and normalization.
* **`FittingEngine` (Core Algorithm)**: Encapsulates a recursive backtracking solver that attempts to place shapes within a grid.
* **`PuzzleData` & `RegionRequest` (Data Models)**: Structured records that represent the input constraints and target regions.
* **`Main` (Orchestrator)**: Handles complex input parsing and manages the high-level parallel execution of region-fitting tasks.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Each class manages a specific layer of the problem:
* **`Shape`**: Responsible only for generating its own geometric variations (symmetries).
* **`FittingEngine`**: Dedicated solely to the search algorithm and state management of "occupied" points.
* **`Main`**: Responsible for data ingestion and "flattening" shape requests.

### 2. Open/Closed Principle (OCP)
The `FittingEngine` is designed to be independent of the specific shapes it packs. By using a generic `Set<Point>` representation for shapes, the engine can accommodate any new polyomino or irregular shape without modification to the `solve` or `canPlace` logic.

### 3. Dependency Inversion Principle (DIP)
The `Main` class orchestrates the solution by feeding `PuzzleData` into the `FittingEngine`. The engine does not know about file paths or input formatting; it only depends on the abstractions of `Shape` and `Point`.

---

## 🧼 Clean Code Highlights

### Recursive Backtracking with Pruning
The `FittingEngine` uses an optimized recursive approach. It employs a **"Largest-First" heuristic** (sorting pieces by size) to fail faster in impossible configurations and uses a pruning check in `Main` to discard regions where total shape area exceeds available space.

```java
// Logic from FittingEngine.java: Recursive backtracking with state restoration
private boolean solve(Set<Point> occupied, List<Shape> remaining, int index) {
    if (index == remaining.size()) return true; // Base Case
    // ... logic for placing variations
    occupied.addAll(placed); // Choose
    if (solve(occupied, remaining, index + 1)) return true; // Explore
    occupied.removeAll(placed); // Un-choose (Backtrack)
}