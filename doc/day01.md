# Day 01: Secret Entrance

## Introduction and Problem Overview

### The Problem
This solution tackles a password dial puzzle where a circular dial with 100 positions (0-99) needs to be rotated according to a series of instructions. The dial starts at position 50, and each instruction specifies:
- **Direction**: 'L' (left/counter-clockwise) or 'R' (right/clockwise)
- **Steps**: Number of positions to rotate (e.g., "R25" means rotate right 25 steps)

The challenge has two parts:
1. **Part One**: Count how many times the dial lands on position 0 after each rotation
2. **Part Two**: Count how many times the dial passes through position 0 during rotations (including intermediate positions)

### Core Data Structures

**Dial (Domain Model)**
- Represents the physical dial with a `position` field (starts at 50)
- Encapsulates rotation logic and position wrapping
- Size constant: 100 positions (0-99)

**Strategy Pattern**
- Defines different counting behaviors without changing the core dial logic
- Allows switching between "final position only" vs "all crossings" counting

**List of Instructions**
- Simple string list where each instruction is parsed into direction + steps
- Processed sequentially to maintain state across rotations

---

## Architecture and Data Flow

The solution follows a clean, layered architecture with clear separation of concerns. Data flows from input → processing → domain logic → strategy → result.

### Main.java - Entry Point
```
Input File → InputProvider → List<String> instructions
                ↓
    PasswordProcessor (BasicStrategy) → Part 1 Result
                ↓
    PasswordProcessor (AdvancedStrategy) → Part 2 Result
```

**Responsibilities:**
- Reads input file using `InputProvider`
- Creates two `PasswordProcessor` instances with different strategies
- Outputs results for both parts

**Key Design Choice:** Demonstrates **Dependency Injection** by passing different strategies to the processor, making it easy to solve both parts with the same processing logic.

---

### PasswordProcessor.java - Service Layer
```
Instructions List → iterate each instruction
                         ↓
                    Dial.rotate()
                         ↓
                  accumulate zeros
                         ↓
                   Total Count
```

**Responsibilities:**
- Orchestrates the solving process
- Creates and manages the `Dial` instance
- Accumulates zero counts across all instructions
- Filters out null/blank instructions

**Data Flow:**
1. Receives strategy via constructor (dependency injection)
2. Creates a fresh dial starting at position 50
3. For each instruction: calls `dial.rotate()` and accumulates the result
4. Returns total zero count

---

### Dial.java - Domain Model
```
Current Position + Instruction + Strategy
              ↓
    Parse instruction (direction + steps)
              ↓
    Strategy.countZeros() → get zeros found
              ↓
    calculateNewPosition() → update position
              ↓
    Return zeros found
```

**Responsibilities:**
- Maintains current dial position
- Parses rotation instructions
- Delegates zero counting to strategy (polymorphism)
- Updates position using modular arithmetic for wrapping

**Key Logic:**
```java
// Wrapping formula handles negative values correctly
((current + delta) % SIZE + SIZE) % SIZE
```
This ensures positions always stay in range [0, 99], even when rotating left from position 0.

---

### PasswordStrategy.java - Interface
```java
public interface PasswordStrategy {
    long countZeros(int currentPosition, int steps, char direction);
}
```

**Responsibilities:**
- Defines the contract for zero-counting strategies
- Enables **Open/Closed Principle**: open for extension (new strategies), closed for modification

**Design Benefit:** The `Dial` class doesn't need to know *how* zeros are counted, only *that* they can be counted. This is pure polymorphism.

---

### BasicStrategy.java - Simple Counting
```
Current Position + Steps + Direction
              ↓
    Calculate final position directly
              ↓
    finalPos == 0 ? return 1 : return 0
```

**Responsibilities:**
- Counts only if the **final position** is zero
- Uses efficient direct calculation (no loops)

**Algorithm:**
1. Calculate delta based on direction (negative for left, positive for right)
2. Apply modular arithmetic to get final position
3. Return 1 if final position is 0, otherwise return 0

**Use Case:** Part One of the puzzle (simpler requirement)

---

### AdvancedStrategy.java - Comprehensive Counting
```
Current Position + Steps + Direction
              ↓
    Loop for each step
              ↓
    Move one position → check if zero → increment counter
              ↓
    Return total zeros found
```

**Responsibilities:**
- Counts **every time** the dial passes through position 0
- Simulates step-by-step rotation

**Algorithm:**
1. Start from current position
2. For each of the `steps` iterations:
    - Move one position in the specified direction
    - If new position is 0, increment counter
3. Return total count

**Use Case:** Part Two of the puzzle (more detailed requirement)

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**
*Each class has one clear reason to change.*

**Example: Dial.java**
- **Only responsible for**: Managing position and rotation mechanics
- **Does NOT**: Count zeros, process instructions list, or read input
- **Why it matters**: If we need to change how zeros are counted, we don't touch `Dial`. If we need to change rotation logic, we don't touch counting strategies.

**Example: PasswordProcessor.java**
- **Only responsible for**: Orchestrating the solution process
- **Does NOT**: Know about rotation mechanics or counting algorithms
- **Why it matters**: Changes to how we count zeros or rotate the dial don't affect the processing logic.

---

### **O - Open/Closed Principle**
*Open for extension, closed for modification.*

**Example: Strategy Pattern**
```java
// Adding a new strategy doesn't require changing existing code
public class OptimizedStrategy implements PasswordStrategy {
    @Override
    public long countZeros(int currentPosition, int steps, char direction) {
        // Mathematical formula to count zeros without simulation
        // Can add this WITHOUT modifying Dial, PasswordProcessor, or other strategies
    }
}
```

**Why it matters**: We solved Part Two by adding `AdvancedStrategy` without changing any existing classes. The system is **open** to new counting strategies but **closed** to modification of core logic.

---

### **L - Liskov Substitution Principle**
*Subtypes must be substitutable for their base types.*

**Example: Strategy Implementations**
```java
// Both strategies can be used interchangeably
PasswordProcessor processor1 = new PasswordProcessor(new BasicStrategy());
PasswordProcessor processor2 = new PasswordProcessor(new AdvancedStrategy());

// Both work correctly with the same interface
long result1 = processor1.solve(instructions);
long result2 = processor2.solve(instructions);
```

**Why it matters**: `Dial.rotate()` works correctly with **any** implementation of `PasswordStrategy`. We can substitute `BasicStrategy` with `AdvancedStrategy` (or any future strategy) without breaking the dial's behavior.

---

### **I - Interface Segregation Principle**
*Clients shouldn't depend on interfaces they don't use.*

**Example: PasswordStrategy**
```java
public interface PasswordStrategy {
    long countZeros(int currentPosition, int steps, char direction);
    // ONLY one method - exactly what clients need, nothing more
}
```

**Why it matters**: The interface is minimal and focused. `Dial` only needs the `countZeros()` method, so that's all the interface provides. No extra methods that implementers would have to stub out or that clients would ignore.

---

### **D - Dependency Inversion Principle**
*Depend on abstractions, not concretions.*

**Example: PasswordProcessor Constructor**
```java
public class PasswordProcessor {
    private final PasswordStrategy strategy;  // Depends on interface, not concrete class
    
    public PasswordProcessor(PasswordStrategy strategy) {
        this.strategy = strategy;  // Injection of abstraction
    }
}
```

**Why it matters**:
- `PasswordProcessor` depends on the `PasswordStrategy` **interface**, not on `BasicStrategy` or `AdvancedStrategy` directly
- High-level module (PasswordProcessor) doesn't depend on low-level modules (concrete strategies)
- Both depend on abstraction (PasswordStrategy interface)
- This makes testing easier (mock strategies) and allows runtime flexibility

**Example: Main.java**
```java
// High-level code controls which implementation to use
new PasswordProcessor(new BasicStrategy()).solve(input);    // Part 1
new PasswordProcessor(new AdvancedStrategy()).solve(input); // Part 2
```

---