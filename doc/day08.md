# Advent of Code 2025: Day 08 - Clean Code & Architecture Report

This project implements a circuit analysis system for Day 08, focusing on 3D spatial connectivity and network topology using **Graph Theory** and the **Strategy Pattern**.

---

## 🏗️ Architectural Overview

The architecture is designed to handle connectivity problems in a 3D coordinate space. It separates the physical data model (points and boxes) from the connectivity logic (Union-Find) and the analysis strategies.


### Key Components
* **`JunctionBox` & `Point3D` (Domain Models)**: Immutable records representing nodes in a 3D space. They encapsulate spatial mathematics, such as Euclidean distance calculations.
* **`Connection`**: Represents an edge between two `JunctionBox` nodes. It implements `Comparable` to allow for greedy algorithms based on distance.
* **`UnionFind` (Data Structure)**: A highly optimized disjoint-set implementation using path compression and union-by-size to track connected components.
* **`CircuitAnalyzer` (Strategy Interface)**: The abstraction that defines how the network should be analyzed.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Responsibilities are strictly decoupled:
* **`Point3D`**: Solely responsible for 3D coordinate representation and distance math.
* **`UnionFind`**: Manages only the set partitioning logic and component tracking.
* **`Main`**: Handles file I/O and the initialization of the coordinate-to-connection graph.

### 2. Open/Closed Principle (OCP)
The use of the `CircuitAnalyzer` interface allows the system to support radically different analytical goals—such as component size product calculation in `BasicCircuitAnalyzer` or spanning tree logic in `AdvanceCircuitAnalyzer`—without modifying the core graph data structures.

### 3. Dependency Inversion Principle (DIP)
The `Main` orchestrator depends on the `CircuitAnalyzer` interface. This allows for the injection of different algorithms at runtime to solve Part 1 and Part 2 of the puzzle independently.

---

## 🧼 Clean Code Highlights

### Data Structure Optimization
The `UnionFind` class uses optimized path compression (`parent[p] = parent[parent[p]]`) and union-by-size. This ensures that the connectivity operations remain nearly constant time, which is critical when processing a large number of potential connections generated in `prepareConnections`.

### Meaningful Domain Modeling
Using **Java Records** for `Connection` and `JunctionBox` provides built-in immutability. This ensures that once the physical layout of the circuit is loaded, it cannot be accidentally modified during the analysis phase.

### Functional Analysis Pipelines
`BasicCircuitAnalyzer` utilizes the **Java Stream API** to perform complex aggregations in a readable, declarative manner:

```java
// Logic from BasicCircuitAnalyzer.java
return boxes.stream()
        .map(box -> uf.find(box.id()))
        .distinct()
        .map(root -> uf.getSize(root))
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .mapToLong(Integer::longValue)
        .reduce(1, (a, b) -> a * b); // Multiplies sizes of top 3 components