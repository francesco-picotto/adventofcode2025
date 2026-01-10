# Advent of Code 2025: Day 11 - Clean Code & Architecture Report

This project implements a solution for the Day 11 challenge, focusing on pathfinding within a directed acyclic graph (DAG) representing a reactor's node connections. The architecture utilizes **Recursive Memoization**, **Functional Streams**, and the **Strategy Pattern**.

---

## 🏗️ Architectural Overview

The system is designed to navigate a complex network of nodes (`ReactorMap`) using different traversal logic to solve for total path counts between specific terminals.


### Key Components
* **`ReactorMap` (Domain Model)**: A Java Record that encapsulates the adjacency list of the reactor. It contains the core recursive logic for counting paths between any two nodes using memoization.
* **`ReactorSolver` (Interface)**: The strategy abstraction that defines a unified way to process the reactor map.
* **`SimplePathSolver`**: Implements Part 1 by calculating all possible paths from the entry point (`you`) to the exit (`out`).
* **`MandatoryNodeSolver`**: Implements Part 2 by calculating path "chains" that must pass through specific mandatory nodes in different potential sequences.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Each class manages a distinct aspect of the problem:
* **`ReactorMap`**: Responsible only for graph representation and path counting math.
* **`Main`**: Responsible for file parsing and orchestrating the solvers.
* **`MandatoryNodeSolver`**: Dedicated to the logic of combining segments into a mandatory chain.

### 2. Open/Closed Principle (OCP)
The architecture is **open for extension** through the `ReactorSolver` interface. If a new puzzle part requires finding the shortest path or identifying bottlenecks, a new solver can be implemented without modifying existing classes.

### 3. Dependency Inversion Principle (DIP)
The `Main` class interacts with solvers through the `ReactorSolver` interface. This decouples the high-level application flow from the specific graph-traversal algorithms used for different puzzle parts.

---

## 🧼 Clean Code Highlights

### Recursive Memoization
The `ReactorMap` implementation uses a `Map<String, Long>` to cache results. This prevents the exponential time complexity typical of recursive path counting in dense graphs by ensuring each node's path count to the destination is calculated only once.

```java
// Logic from ReactorMap.java
public long countPaths(String start, String end, Map<String, Long> memo){
    if(start.equals(end)) return 1L;
    if(memo.containsKey(start)) return memo.get(start);

    long paths = getNeighbours(start).stream()
            .mapToLong(n -> countPaths(n, end, memo))
            .sum();

    memo.put(start, paths);
    return paths;
}