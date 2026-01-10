# Advent of Code 2025: Day 09 - Clean Code & Architecture Report

This project implements a geometric analysis system for Day 09, focusing on finding optimal rectangles within set boundaries using **Computational Geometry** and the **Strategy Pattern**.

---

## 🏗️ Architectural Overview

The solution is structured to separate geometric data structures from the algorithms used to analyze them. This allows for simple area calculations alongside complex spatial boundary validations.


### Key Components
* **`Tile` & `Rectangle` (Domain Models)**: Immutable records representing points and areas. They encapsulate basic geometric logic such as coordinate distances, area calculations, and center-point derivation.
* **`GridAnalyzer` (Strategy Interface)**: The abstraction layer that allows the `Main` class to execute different analysis algorithms interchangeably.
* **`GeometryUtils`**: A stateless utility class containing complex spatial algorithms, specifically a **Ray Casting Algorithm** for point-in-polygon testing.
* **Concrete Analyzers**:
    * **`MaxRectangleAnalyzer`**: A brute-force strategy to find the largest possible area between any two tiles.
    * **`LoopRectangleAnalyzer`**: A sophisticated strategy that validates if a rectangle is contained within a polygon defined by the tiles.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Each class is dedicated to a specific geometric or orchestrational task:
* **`Rectangle`**: Responsible only for defining an area and its properties.
* **`GeometryUtils`**: Dedicated solely to mathematical spatial checks (intersections and containment).
* **`Main`**: Handles file system interactions and the high-level flow of the application.

### 2. Open/Closed Principle (OCP)
The architecture is **open for extension** through the `GridAnalyzer` interface. New analysis requirements (e.g., finding rectangles with specific aspect ratios) can be added by creating a new class without modifying the existing geometric logic or the `Main` entry point.

### 3. Dependency Inversion Principle (DIP)
The `Main` class depends on the `GridAnalyzer` abstraction rather than specific implementations. This decouples the algorithm execution from the core data loading and reporting logic.

---

## 🧼 Clean Code Highlights

### Parallel Stream Processing
In `LoopRectangleAnalyzer`, the code utilizes Java's `parallel()` streams to optimize a computationally expensive $O(N^3)$ operation across large datasets, significantly improving performance without sacrificing readability.

```java
// Logic from LoopRectangleAnalyzer.java
return IntStream.range(0, tiles.size())
        .parallel() // Concurrent processing of tile-based max calculations
        .mapToLong(i -> calculateMaxForTile(i, tiles))
        .max()
        .orElse(0L);