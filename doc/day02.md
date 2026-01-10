# Advent of Code 2025: Day 02 - Clean Code & Architecture Report

This project implements a solution for the Day 02 challenge, focusing on processing numeric ID ranges based on specific repetition rules. The architecture leverages **Java Records**, **Functional Programming**, and the **Strategy Pattern** to create a clean, testable system.

---

## 🏗️ Architectural Overview

The solution is built around a domain-centric model that separates data parsing, range iteration, and business rule evaluation.


### Key Components
* **`IdRange` (Domain Model)**: A Java Record representing a range of IDs. It handles its own parsing and knows how to iterate over its values.
* **`IdRule` (Strategy Interface)**: An abstraction for the validation logic.
* **`SimpleRepeatRule` & `MultipleRepeatRule`**: Concrete strategies that implement different mathematical validation logic for Part 1 and Part 2.

---

## 🛠️ Applied SOLID Principles

### 1. Single Responsibility Principle (SRP)
Each component has a focused responsibility:
* **`IdRange`**: Responsible only for range-related logic (parsing "100-200" and summing valid IDs within that range).
* **`SimpleRepeatRule`**: Responsible only for checking if an ID is a simple duplicate (e.g., "1212").
* **`MultipleRepeatRule`**: Responsible only for the prime-factor based repetition logic.

### 2. Dependency Inversion Principle (DIP)
The `IdRange` class does not depend on specific rule implementations. Instead, it accepts an `IdRule` interface in its `sumValidIds` method:
```java
public long sumValidIds(IdRule rule) {
    return LongStream.rangeClosed(start, end)
            .map(rule::evaluate)
            .sum();
}