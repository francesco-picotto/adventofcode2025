# Advent of Code 2025: Day 07 - Clean Code & Architecture Report

This project implements a solution for the Day 07 challenge, involving the simulation of particles or "beams" traversing a 2D grid called a `TachyonManifold`. The architecture focuses on **dynamic programming**, **polymorphism**, and **encapsulation**.

---

## 🏗️ Architectural Overview

The system is designed around a grid-based simulation where different analyzers process a "Manifold" to count distinct events or estimate timeline probabilities.


### Key Components
* **`TachyonManifold` (Domain Model)**: Encapsulates the 2D grid logic. It identifies the starting point (`S`) and the location of "Splitters" (`^`) while providing safe boundary checks.
* **`ManifoldAnalyzer` (Strategy Interface)**: A functional contract for analyzing the grid.
* **`BeamSplitCounter`**: Implements Part 1 using a `Set` to track unique active beam positions row-by-row, counting every time a beam hits a splitter.
* **`QuantumTimelineEstimator`**: Implements Part 2 using a dynamic programming approach (long array) to track the total number of possible timelines created by splitting events.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Responsibilities are clearly delimited to ensure maintainability:
* **`TachyonManifold`**: Only manages the spatial structure and character identification within the grid.
* **`BeamSplitCounter`**: Focused exclusively on tracking distinct beam positions and counting intersections.
* **`QuantumTimelineEstimator`**: Responsible for the mathematical accumulation of paths across the grid.

### 2. Open/Closed Principle (OCP)
The `Main` class demonstrates OCP by using the `ManifoldAnalyzer` interface. New analyzers (e.g., a "ProbabilityHeatmapAnalyzer") can be added without modifying the `Main` logic or the `TachyonManifold` class.

### 3. Dependency Inversion Principle (DIP)
High-level simulation logic depends on the `ManifoldAnalyzer` abstraction. This decouples the execution of the puzzle parts from the underlying grid implementation, allowing different algorithms to process the same `TachyonManifold` instance.

---

## 🧼 Clean Code Highlights

### Dynamic Programming vs. Set Tracking
The project showcases two different ways to handle state transitions:
* **Set-based tracking**: In `BeamSplitCounter`, a `HashSet` ensures that if multiple beams merge onto the same column, they are treated as a single active position for the next row.
* **Array-based accumulation**: In `QuantumTimelineEstimator`, a `long[]` array tracks the "weight" (number of timelines) at each column, effectively implementing a row-by-row DP transition.

### Robust Error Handling
The `TachyonManifold` constructor performs defensive programming by validating the input grid and ensuring the starting character exists, preventing `IndexOutOfBoundsException` or `NullPointerException` later in the pipeline.

```java
// Logic from TachyonManifold.java
public TachyonManifold(List<String> grid){
    if (grid == null || grid.isEmpty()) {
        throw new IllegalArgumentException("Grid cannot be null or empty");
    }
    this.startColumn = grid.get(0).indexOf(START);
    if (this.startColumn == -1) {
        throw new IllegalStateException("'S' starting column is not found");
    }
}