# Advent of Code 2025
## Picotto Francesco - Solutions in Java

This repository contains my solutions for the [Advent of Code 2025](https://adventofcode.com/2025). The project is designed with a strong focus on **Clean Code**, **SOLID principles**, and **Software Architecture**, transforming puzzle challenges into robust, maintainable Java applications.

---

## 🏗️ Architectural Philosophy

Rather than writing simple scripts, each day is treated as a mini-system. Key architectural patterns applied throughout the project include:

* **Strategy Pattern**: Used to switch between Part 1 and Part 2 logic without modifying core execution classes.
* **Template Method**: Implemented in abstract analyzers to share grid-scanning and boundary-check logic.
* **Composition & Decorators**: Utilized to add iterative or recursive behavior to basic rules.
* **Domain-Driven Design (DDD)**: Logic is encapsulated within rich domain models like `TachyonManifold`, `IngredientRange`, and `ReactorMap`.


---

## 🛠️ Technical Stack & Features

* **Java 23**: Leveraging modern features like **Records** for immutable data models and **Pattern Matching**.
* **Functional Programming**: Extensive use of **Java Streams** and **Lambdas** for declarative data processing and reduction.
* **Complex Algorithms**: Implementation of advanced logic including:
    * **Backtracking with Pruning** (Day 12).
    * **Ray Casting** for Point-in-Polygon testing (Day 09).
    * **Graph Theory (Union-Find)** for network connectivity (Day 08).
    * **Dynamic Programming & Memoization** (Day 10, 11).

---

## 📂 Project Structure

The project follows the standard Maven directory layout:

```text
.
├── pom.xml                   # Maven project configuration
├── README.md                 # Project documentation
├── .gitignore                # Git exclusion rules
├── src/
│   ├── main/
│   │   ├── java/             # Source code
│   │   │   └── software.ulpgc.adventofcode2025/
│   │   │       ├── day01/     # Logic for Day 01
│   │   │       └── ...        # Days 02 through 12
│   │   └── resources/        # Input data and configurations
│   │      └── inputs/
│   │   │       ├── input_day01.txt    # Input for Day 01 
│   │   │       └── ...                # Input for Day 02 through 12
│   └── test/
│       └── java/             # Unit tests
│           └── software.ulpgc.adventofcode2025/
│               ├── day01/     # Tests for Day 01
│               └── ...        # Tests for Days 02 through 12
└── target/                   # Compiled build artifacts