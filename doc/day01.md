# Day 01: Password Dial Problem - Architecture & SOLID Principles

## Problem Overview

The password dial problem involves a circular dial with 100 positions (0-99) that starts at position 50. Given a series of rotation instructions (e.g., "R25" for rotate right 25 positions, "L10" for rotate left 10 positions), the challenge is to count how many times the dial lands on or passes through position 0.

**Two Parts:**
- **Part 1**: Count only if the *final position* after each rotation is zero
- **Part 2**: Count *every time* the dial passes through zero during rotation

## Project Architecture

The project follows a clean, layered architecture organized into four main packages:

```
day01/
├── domain/          # Core business entities
│   └── Dial
├── strategy/        # Strategy pattern implementations
│   ├── PasswordStrategy (interface)
│   ├── BasicStrategy
│   └── AdvancedStrategy
├── service/         # Business logic orchestration
│   └── PasswordProcessor
└── Main             # Application entry point
```

### Architecture Layers

1. **Domain Layer** (`domain/`)
    - Contains `Dial` - the core entity representing the circular dial
    - Encapsulates dial behavior and position management
    - Independent of counting strategies

2. **Strategy Layer** (`strategy/`)
    - Defines the `PasswordStrategy` interface
    - Implements two concrete strategies for counting zeros
    - Allows runtime selection of counting algorithms

3. **Service Layer** (`service/`)
    - `PasswordProcessor` orchestrates the solution
    - Processes multiple instructions using a configured strategy
    - Acts as the application's business logic coordinator

4. **Application Layer**
    - `Main` class serves as the entry point
    - Demonstrates solving both parts with different strategies

## SOLID Principles in Action

### 1. **Single Responsibility Principle (SRP)**

Each class has ONE well-defined responsibility:

- **`Dial`**: Manages dial position and rotation mechanics
    - Handles position wrapping (0-99)
    - Executes rotation instructions
    - Does NOT decide how to count zeros

- **`BasicStrategy`**: Counts zeros by checking final position only
    - Calculates end position directly
    - Single algorithm implementation

- **`AdvancedStrategy`**: Counts zeros by checking all intermediate positions
    - Simulates step-by-step rotation
    - Different algorithm, different class

- **`PasswordProcessor`**: Processes multiple instructions and accumulates results
    - Orchestrates the solving process
    - Manages the dial lifecycle

### 2. **Open/Closed Principle (OCP)**

The system is **open for extension, closed for modification**:

```java
// CLOSED: The Dial class doesn't need modification for new strategies
public int rotate(String instruction, PasswordStrategy strategy) {
    char dir = instruction.charAt(0);
    int steps = Integer.parseInt(instruction.substring(1));
    int zerosFound = strategy.countZeros(this.position, steps, dir);
    this.position = calculateNewPosition(this.position, steps, dir);
    return zerosFound;
}

// OPEN: New strategies can be added without changing existing code
public class CustomStrategy implements PasswordStrategy {
    @Override
    public int countZeros(int currentPosition, int steps, char direction) {
        // New counting logic here
    }
}
```

**Key Benefits:**
- Adding a third strategy (e.g., "count only if passing through 0 twice") requires NO changes to `Dial`, `PasswordProcessor`, or other strategies
- The core system remains stable while being extensible

### 3. **Liskov Substitution Principle (LSP)**

Any `PasswordStrategy` implementation can be substituted without breaking the system:

```java
// Both work identically from the client's perspective
PasswordProcessor processor1 = new PasswordProcessor(new BasicStrategy());
PasswordProcessor processor2 = new PasswordProcessor(new AdvancedStrategy());

// Same method call, different behavior - no special handling needed
long result1 = processor1.solve(instructions);
long result2 = processor2.solve(instructions);
```

**Contract Guarantee:**
- All strategies accept the same parameters: `(currentPosition, steps, direction)`
- All strategies return an integer count of zeros
- Clients don't need to know which strategy is being used

### 4. **Interface Segregation Principle (ISP)**

The `PasswordStrategy` interface is **minimal and focused**:

```java
public interface PasswordStrategy {
    int countZeros(int currentPosition, int steps, char direction);
}
```

**Why this matters:**
- Implementers only need to provide ONE method
- No "fat interface" with unused methods
- Each strategy only implements what it needs
- Clear contract: "If you can count zeros, you're a valid strategy"

### 5. **Dependency Inversion Principle (DIP)**

High-level modules depend on abstractions, not concrete implementations:

```java
// PasswordProcessor depends on the INTERFACE, not concrete classes
public class PasswordProcessor {
    private final PasswordStrategy strategy;  // ← Abstraction
    
    public PasswordProcessor(PasswordStrategy strategy) {
        this.strategy = strategy;
    }
}

// Dial depends on the INTERFACE
public int rotate(String instruction, PasswordStrategy strategy) {
    // ...
    int zerosFound = strategy.countZeros();  // ← Abstraction
}
```

**Dependency Flow:**
```
High-level:    Main → PasswordProcessor → Dial
                ↓          ↓              ↓
Abstraction:   PasswordStrategy (interface)
                         ↑
Low-level:    BasicStrategy | AdvancedStrategy
```

**Benefits:**
- `Main` decides which strategy to inject
- `PasswordProcessor` and `Dial` don't care about concrete implementations
- Easy to test with mock strategies
- Loose coupling between layers

## Design Pattern: Strategy Pattern

The Strategy Pattern allows selecting an algorithm at runtime:

### Problem Without Strategy Pattern:
```java
// BAD: Hard-coded logic with if-else
public int rotate(String instruction, boolean useAdvanced) {
    if (useAdvanced) {
        // Count all intermediate zeros
    } else {
        // Count only final position
    }
}
```

**Issues:**
- Violates OCP (must modify Dial for new algorithms)
- Violates SRP (Dial knows all counting algorithms)
- Hard to test individual strategies

### Solution With Strategy Pattern:
```java
// GOOD: Delegate to strategy
public int rotate(String instruction, PasswordStrategy strategy) {
    int zerosFound = strategy.countZeros(this.position, steps, dir);
    // ...
}
```

**Benefits:**
- Algorithms are encapsulated in separate classes
- Easy to add new strategies
- Strategies can be tested independently
- Runtime flexibility

## Code Flow Example

```java
// 1. Main creates processor with chosen strategy
PasswordProcessor processor = new PasswordProcessor(new AdvancedStrategy());

// 2. Processor creates a dial and processes instructions
Dial dial = new Dial();  // Starts at position 50

// 3. For instruction "R25":
//    a. Dial delegates counting to strategy
//    b. AdvancedStrategy counts: 50→51→...→74→75 (passes 0? No)
//    c. Dial updates position to 75
//    d. Returns count: 0

// 4. For instruction "L80" (from position 75):
//    a. AdvancedStrategy counts: 75→74→...→1→0→99→98→...→95
//    b. Passes through 0 once!
//    c. Dial updates position to 95
//    d. Returns count: 1

// 5. Processor accumulates all counts
```

## Key Takeaways

### Why This Architecture Matters:

1. **Maintainability**: Each class has a clear, single purpose
2. **Testability**: Components can be tested in isolation
3. **Flexibility**: New strategies can be added without risk
4. **Reusability**: Strategies can be reused in other contexts
5. **Clarity**: The code structure mirrors the problem domain

### SOLID in One Sentence Each:

- **SRP**: "One class, one job" - Each class does exactly one thing
- **OCP**: "Extend, don't modify" - Add features without changing existing code
- **LSP**: "Swap freely" - All strategies work the same way to clients
- **ISP**: "Small contracts" - Simple, focused interface with one method
- **DIP**: "Depend on abstractions" - Classes use interfaces, not concrete types

### Real-World Analogy:

Think of this like a restaurant:
- **Dial** = The kitchen equipment (oven, stove)
- **PasswordStrategy** = Recipe interface (all recipes have "cook" method)
- **BasicStrategy/AdvancedStrategy** = Different recipes (same equipment, different techniques)
- **PasswordProcessor** = The head chef (coordinates the cooking)
- **Main** = The restaurant manager (decides which recipes to use today)

The kitchen doesn't change when you add new recipes. You just need new recipes that follow the same format!

## Conclusion

This project demonstrates that good architecture isn't about complexity—it's about clarity and flexibility. By applying SOLID principles and the Strategy Pattern, we've created a system that is:

- Easy to understand (clear separation of concerns)
- Easy to extend (add strategies without fear)
- Easy to test (each component is independent)
- Easy to maintain (changes are localized)

The extra structure pays dividends when requirements change, new features are needed, or bugs need to be fixed.