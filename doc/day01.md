# Advent of Code 2025 - Day 01: The Chronal Dial

### Solution Overview
The challenge involves simulating the rotation of a dial numbered from 0 to 99. The core difficulty lies in the fact that "Part One" and "Part Two" require different logic for counting how many times the pointer interacts with the zero position during a movement.

### Software Architecture
The project is structured following **SOLID** principles, specifically the **Single Responsibility Principle** and the **Open/Closed Principle**. By decoupling the movement logic from the counting logic, the system remains flexible and easy to extend.



#### Key Components:
* **`Dial` (The Context)**: Maintains the internal state, specifically the current pointer position which starts at 50. It exposes the `rotate` method which accepts a raw instruction and a specific strategy.
* **`PasswordStrategy` (The Abstraction)**: An interface that defines the contract for counting zeros during or after a rotation. It allows the `Dial` to remain agnostic of the specific scoring rules.
* **`BasicStrategy` (Part One Logic)**: Implements a mathematical approach to check if the **final** position of a movement is zero. It uses the modulo operator to determine the landing spot.
* **`AdvancedStrategy` (Part Two Logic)**: Implements a simulation approach. It iterates through every individual step of the movement to count how many times the pointer **passes through** zero.
* **`Main` (The Orchestrator)**: Handles file I/O using Java Streams, cleans the input data, and executes the simulation for both parts by injecting the appropriate strategy into the solver.

### Design Decisions
* **Strategy Pattern**: This was chosen to separate the *mechanism of rotation* (which is common) from the *business rules* (which change between parts).
* **Mathematical Safety**: In `BasicStrategy`, the logic handles negative results from left turns by using the formula `((currentPosition + delta) % 100 + 100) % 100`.
* **Decoupling**: The `Dial` class does not know which "Part" of the challenge it is solving; it simply follows the rules provided by the injected `PasswordStrategy`.

---
### Performance Note
While `BasicStrategy` performs in $O(1)$ time per instruction, `AdvancedStrategy` performs in $O(n)$ where $n$ is the number of steps. This is a necessary trade-off to accurately track intermediate positions during the dial rotation.