# Advent of Code 2025
## Picotto Francesco - Solutions in Java

This repository contains my solutions for the [Advent of Code 2025](https://adventofcode.com/2025). The project is designed with a strong focus on **Clean Code**, **SOLID principles**, and **Software Architecture**, transforming puzzle challenges into robust, maintainable Java applications.

---
## Project Overview

This project contains solutions for 12 days of programming puzzles, each showcasing different algorithms, data structures, and architectural patterns. Every solution follows SOLID principles and emphasizes clean code, maintainability, and testability.

## Architecture

The project follows a consistent layered architecture across all days:

```
src/main/java/software/ulpgc/adventofcode2025/
├── core/                          # Shared infrastructure
│   ├── InputMapper.java          # Interface for parsing input
│   └── InputProvider.java        # File reading utility
├── days/                         # Daily solutions
│   ├── day01/                    # Password dial simulation
│   ├── day02/                    # ID validation patterns
│   ├── day03/                    # Bank code extraction
│   ├── day04/                    # Grid cell removal
│   ├── day05/                    # Inventory freshness analysis
│   ├── day06/                    # Math worksheet parsing
│   ├── day07/                    # Tachyon beam simulation
│   ├── day08/                    # Circuit connectivity (Union-Find)
│   ├── day09/                    # Rectangle analysis (Geometry)
│   ├── day10/                    # Machine puzzles (BFS + DP)
│   ├── day11/                    # Reactor path counting (Graph)
│   └── day12/                    # Polyomino packing (Backtracking)
└── utils/                        # Shared utilities
    └── GridUtils.java

src/main/resources/
└── inputs/                       # Input data files

doc/                              # Detailed documentation
├── day01.md through day12.md    # Per-day architecture docs
```

### Common Patterns

Each day solution typically includes:

- **Domain**: Core business objects (records for immutability)
- **Service**: Processing orchestration
- **Analyzer/Solver/Strategy**: Algorithm implementations (Strategy Pattern)
- **Mapper**: Input parsing and transformation
- **Main**: Entry point with dependency injection

## Key Design Principles

### SOLID Principles
- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Extensible without modification via Strategy Pattern
- **Liskov Substitution**: Strategies are fully interchangeable
- **Interface Segregation**: Minimal, focused interfaces
- **Dependency Inversion**: High-level modules depend on abstractions

### Design Patterns Used
- **Strategy Pattern**: Different algorithms for same problem (all days)
- **Template Method**: Common processing structure with varying steps
- **Factory Method**: Object creation encapsulation
- **Delegation**: Complex parsing delegated to specialized classes
- **Memoization**: Caching for optimization (DP problems)
- **Backtracking**: Exhaustive search with pruning
- **Union-Find**: Efficient component tracking


### Prerequisites
- Java 23 or higher
- Maven 3.8+

### Build
```bash
mvn clean compile
```

### Run a Specific Day
```bash
# Run Day 01
mvn exec:java -Dexec.mainClass="software.ulpgc.adventofcode2025.days.day01.Main"

# Run Day 05
mvn exec:java -Dexec.mainClass="software.ulpgc.adventofcode2025.days.day05.Main"
```

### Run All Days
```bash
for day in {01..12}; do
    mvn exec:java -Dexec.mainClass="software.ulpgc.adventofcode2025.days.day${day}.Main"
done
```

## Documentation

Each day has comprehensive documentation in the `doc/` directory:

- **Introduction**: Problem statement and approach
- **Architecture**: Component breakdown and data flow
- **SOLID Principles**: Applied with concrete examples
- **Design Patterns**: Used patterns with rationale
- **Algorithm Analysis**: Complexity and performance (where applicable)

### Reading the Documentation

For a quick overview of a solution:
```bash
# View Day 01 documentation
cat doc/day01.md
```

---