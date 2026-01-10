# Advent of Code 2025 - Day 02: ID Validation Architecture

## Overview
The solution for Day 02 involves validating numeric IDs within specific ranges provided in the input. The goal is to identify IDs that follow certain repetition patterns (either split in two or repeating based on prime factors). The architecture is designed to be modular, efficient, and easy to extend using modern Java features.

## Architectural Design

### 1. Strategy Pattern
The core of the system is built around the **Strategy Pattern**. By using the `IdRule` interface, we decouple the validation logic from the execution flow.



* **`IdRule` (Interface):** Defines the contract for validation strategies through the `evaluate(long id)` method.
* **`SimpleRepeatRule`:** Implement the logic for Part 1, checking for IDs that consist of two identical halves.
* **`MultipleRepeatRule`:** Implement the logic for Part 2, checking if an ID is composed of $p$ identical repeating blocks, where $p$ is a prime number.

### 2. Domain Modeling with Records
Instead of handling raw strings or primitive arrays throughout the application, we introduced the `IdRange` record.

* **`IdRange` (Record):** Encapsulates the start and end of a numeric range.
* **Static Factory Method:** The `parse(String rangeStr)` method handles the transformation from the input format (e.g., "100-200") into a structured domain object.
* **Internal Logic:** It manages its own iteration and summation using `LongStream.rangeClosed`, delegating the validity check to the injected `IdRule`.

## Responsibilities and Flow

The application follows a clear **Separation of Concerns (SoC)**:

1.  **Input Loading (`Main`):** The `loadInput` method reads the file and splits the content into individual range strings.
2.  **Orchestration (`Main`):** The `solve` method creates a stream of ranges, parses them into `IdRange` objects, and triggers the calculation.
3.  **Validation Logic:** Each `IdRule` implementation focuses strictly on the mathematical properties of the ID string.

| Class | Responsibility |
| :--- | :--- |
| **`Main`** | Entry point, file I/O, and high-level stream orchestration. |
| **`IdRange`** | Parsing logic and streaming over a specific range of numbers. |
| **`IdRule`** | Abstraction for ID validation criteria. |
| **`SimpleRepeatRule`** | Validates 2-part identical repetitions. |
| **`MultipleRepeatRule`** | Validates $p$-part repetitions using prime divisors. |

## Software Engineering Principles Applied

* **Open/Closed Principle:** New validation rules can be added by creating a new class implementing `IdRule` without modifying the `Main` or `IdRange` classes.
* **Single Responsibility Principle (SRP):**
    * `Main` doesn't know *how* to validate an ID.
    * `IdRule` doesn't know *how* to parse a file or handle ranges.
    * `IdRange` doesn't know the specific rules of the challenge.
* **Efficiency:** The optimized `isRepeated` method in `MultipleRepeatRule` uses `startsWith` with offsets to avoid creating unnecessary substring objects in memory.thod can simply use `.sum()` to get the final result, making the stream pipeline very clean.