# Day 02: Gift Shop

## Introduction and Problem Overview

### The Problem
This solution tackles an ID validation puzzle where we need to process ranges of IDs and identify which ones meet specific repetition pattern criteria. The challenge involves:
- **Input**: Ranges of IDs in format "start-end" (e.g., "1102-2949")
- **Goal**: Find all IDs within these ranges that match certain digit repetition patterns
- **Output**: Sum of all valid IDs across all ranges

The challenge has two parts:
1. **Part One**: Find IDs where the first half of digits exactly matches the second half (e.g., "123123")
2. **Part Two**: Find IDs where a pattern repeats a prime number of times (e.g., "123123123" = "123" repeated 3 times)

### Core Data Structures

**IdRange (Domain Model)**
- Represents a range of IDs with inclusive start and end boundaries
- Immutable record type ensuring data integrity
- Encapsulates range parsing and validation logic

**IdRule (Strategy Interface)**
- Defines different validation patterns for IDs
- Returns the ID value if valid, 0 if invalid (clever design for easy summing)

**Stream-Based Processing**
- Uses Java Streams for efficient range processing
- Functional approach with `LongStream` for large ID ranges
- Lazy evaluation ensures memory efficiency

---

## Architecture and Data Flow

The solution follows a clean, functional architecture with clear data transformation pipelines. Data flows from input → parsing → range expansion → validation → aggregation.

### Main.java - Entry Point
```
Input File → InputProvider → Day02Mapper
                                ↓
                    List<String> ["1102-2949", "3000-4500"]
                                ↓
         IdProcessor (SimpleRepeatRule) → Part 1 Sum
                                ↓
         IdProcessor (MultipleRepeatRule) → Part 2 Sum
```

**Responsibilities:**
- Reads and parses input file using custom mapper
- Creates two `IdProcessor` instances with different validation rules
- Outputs sums for both parts

**Key Design Choice:** Uses the same processing pipeline with different rules, demonstrating the power of the Strategy Pattern for business logic variation.

---

### Day02Mapper.java - Input Transformation
```
Raw Lines ["1102,2949", "3000,4500"]
              ↓
    Trim and filter empty
              ↓
    Split by comma ["1102", "2949"], ["3000", "4500"]
              ↓
    Flatten to single list
              ↓
["1102", "2949", "3000", "4500"]
```

**Responsibilities:**
- Implements `InputMapper<List<String>>` interface
- Parses comma-separated values across multiple lines
- Flattens nested structure into a single list

**Processing Pipeline:**
1. **Trim**: Remove whitespace from each line
2. **Filter**: Remove empty lines
3. **Split**: Convert each line into array by comma delimiter
4. **Flatten**: Combine all arrays into single stream using `flatMap`
5. **Collect**: Gather into final list

**Design Pattern:** This is a **Mapper/Transformer** pattern, separating input parsing logic from business logic.

---

### IdProcessor.java - Service Layer
```
List<String> ranges → parse each to IdRange
                           ↓
              range.sumValidIds(rule)
                           ↓
                    aggregate sums
                           ↓
                      Total Sum
```

**Responsibilities:**
- Orchestrates the validation process across all ranges
- Delegates parsing to `IdRange.parse()`
- Delegates validation to injected `IdRule`
- Aggregates results using stream operations

**Data Flow:**
1. Receives validation rule via constructor (dependency injection)
2. Streams through range strings
3. Maps each string to `IdRange` object
4. Maps each range to its sum of valid IDs
5. Sums all results into final total

**Functional Approach:** Uses `mapToLong` for efficient primitive stream processing, avoiding boxing overhead.

---

### IdRange.java - Domain Model
```
Range String "1102-2949"
              ↓
    Parse to start=1102, end=2949
              ↓
    Generate LongStream [1102...2949]
              ↓
    Apply rule.evaluate() to each ID
              ↓
    Sum results (valid IDs + 0s for invalid)
```

**Responsibilities:**
- Represents an immutable range of IDs
- Parses range strings (factory method pattern)
- Generates all IDs in range and validates them
- Sums valid IDs efficiently

**Key Methods:**

**`parse(String rangeStr)`** - Factory Method
- Splits string by hyphen delimiter
- Converts both parts to long integers
- Returns new IdRange instance

**`sumValidIds(IdRule rule)`** - Core Logic
```java
LongStream.rangeClosed(start, end)  // Generate all IDs in range
    .map(rule::evaluate)            // Apply validation rule
    .sum();                         // Sum valid IDs (invalid = 0)
```

**Design Brilliance:** By having rules return 0 for invalid IDs, we can use simple `sum()` without filtering—elegant and efficient!

---

### IdRule.java - Strategy Interface
```java
public interface IdRule {
    long evaluate(long id);  // Returns id if valid, 0 if invalid
}
```

**Responsibilities:**
- Defines contract for ID validation strategies
- Enables polymorphic behavior in validation logic
- Clever design: return value is both validation result AND contribution to sum

**Design Benefit:** The interface is minimal yet powerful. The return type (long) serves double duty:
- **Validation indicator**: 0 means invalid, non-zero means valid
- **Accumulation value**: Valid IDs contribute themselves to the sum

---

### SimpleRepeatRule.java - Half-Repetition Validation
```
ID: 123123
      ↓
Convert to string "123123"
      ↓
Check length even? YES (6 digits)
      ↓
Split: first="123", second="123"
      ↓
Are they equal? YES
      ↓
Return 123123
```

**Responsibilities:**
- Validates IDs with exact half-repetition pattern
- Checks even-length requirement
- Compares first and second halves

**Algorithm:**
1. Convert ID to string for digit manipulation
2. Check if length is even (odd-length IDs can't have equal halves)
3. Extract first half: `substring(0, length/2)`
4. Extract second half: `substring(length/2)`
5. Compare halves: if equal, return ID; else return 0

**Examples:**
- `123123` → Valid (first="123", second="123")
- `7777` → Valid (first="77", second="77")
- `12345` → Invalid (odd length)
- `123456` → Invalid (first="123" ≠ second="456")

---

### MultipleRepeatRule.java - Prime-Repetition Validation
```
ID: 123123123
      ↓
Convert to string, length=9
      ↓
Check divisibility by primes [2,3,5,7...]
      ↓
9 % 3 == 0 → Check if pattern repeats 3 times
      ↓
partLen = 9/3 = 3, firstPart = "123"
      ↓
Verify: pos[0-3]="123", pos[3-6]="123", pos[6-9]="123"
      ↓
All match! Return 123123123
```

**Responsibilities:**
- Validates IDs with prime-repetition patterns
- Tests multiple prime divisors
- Verifies pattern consistency across repetitions

**Algorithm:**
1. Convert ID to string and get length
2. For each prime in predefined array:
    - Check if length is divisible by this prime
    - If yes, calculate pattern length: `length / prime`
    - Extract first pattern
    - Verify all subsequent parts match the first pattern
3. If any prime creates valid repetition, return ID
4. If no prime works, return 0

**Prime Array:** `[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]`
- Covers reasonable ID lengths
- Allows checking various repetition patterns efficiently

**isRepeated() Helper:**
```java
private boolean isRepeated(String idStr, int p) {
    int partLen = idStr.length() / p;
    String firstPart = idStr.substring(0, partLen);
    
    for(int i = 0; i < p; i++){
        if(!idStr.startsWith(firstPart, i * partLen)) return false;
    }
    return true;
}
```
- Uses `startsWith(prefix, offset)` for efficient comparison
- Avoids creating p substring objects

**Examples:**
- `123123` → Valid (pattern "123" × 2, where 2 is prime)
- `123123123` → Valid (pattern "123" × 3, where 3 is prime)
- `12121212` → Valid (pattern "12" × 4... wait, 4 is NOT prime!)
    - Actually tries "1212" × 2 (valid, 2 is prime)
- `123456` → Invalid (no repeating pattern)

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**
*Each class has one clear reason to change.*

**Example: IdRange.java**
- **Only responsible for**: Representing a range and generating IDs within it
- **Does NOT**: Validate IDs, parse input files, or know about validation rules
- **Why it matters**: If validation logic changes, we don't touch `IdRange`. If range parsing changes, we don't touch validation rules.

**Example: Day02Mapper.java**
- **Only responsible for**: Transforming raw input into structured format
- **Does NOT**: Validate IDs, process ranges, or know business rules
- **Why it matters**: Input format changes are isolated to this single class.

**Example: SimpleRepeatRule vs MultipleRepeatRule**
- Each rule has **one validation algorithm**
- Changing one rule doesn't affect the other
- Adding new rules doesn't require modifying existing ones

---

### **O - Open/Closed Principle**
*Open for extension, closed for modification.*

**Example: Adding New Validation Rules**
```java
// Want to check for palindrome IDs? Just add a new rule!
public class PalindromeRule implements IdRule {
    @Override
    public long evaluate(long id) {
        String idStr = String.valueOf(id);
        String reversed = new StringBuilder(idStr).reverse().toString();
        return idStr.equals(reversed) ? id : 0;
    }
}

// Use it without changing ANY existing code
new IdProcessor(new PalindromeRule()).solve(input);
```

**Why it matters**:
- We solved Part Two by adding `MultipleRepeatRule` without modifying `IdProcessor`, `IdRange`, or `SimpleRepeatRule`
- The system is **open** to new validation strategies but **closed** to modification of core processing logic
- This is textbook Open/Closed Principle

---

### **L - Liskov Substitution Principle**
*Subtypes must be substitutable for their base types.*

**Example: Rule Interchangeability**
```java
// Both rules work identically from the processor's perspective
IdProcessor processor1 = new IdProcessor(new SimpleRepeatRule());
IdProcessor processor2 = new IdProcessor(new MultipleRepeatRule());

// Both produce correct results using the same interface
long result1 = processor1.solve(ranges);  // Sum of half-repeat IDs
long result2 = processor2.solve(ranges);  // Sum of prime-repeat IDs
```

**Contract Guarantee:**
- Every `IdRule` implementation must return:
    - The ID value if it's valid according to that rule
    - Zero if the ID is invalid
- This contract is upheld by both `SimpleRepeatRule` and `MultipleRepeatRule`
- Any code using `IdRule` works correctly with any implementation

**Why it matters**: The `IdRange.sumValidIds()` method works correctly with **any** IdRule implementation. We can substitute rules freely without breaking behavior.

---

### **I - Interface Segregation Principle**
*Clients shouldn't depend on interfaces they don't use.*

**Example: IdRule Interface**
```java
public interface IdRule {
    long evaluate(long id);  // ONLY what's needed, nothing more
}
```

**Why it's good:**
- Minimal interface with exactly one method
- No unnecessary methods that implementers must stub
- No forced dependencies on unrelated functionality

**Counter-example (BAD design we avoided):**
```java
// BAD: Bloated interface
public interface IdRule {
    long evaluate(long id);
    boolean isValid(long id);           // Redundant!
    String getDescription();            // Not always needed!
    List<Long> findAllValid(IdRange r); // Too specific!
    void logValidation(long id);        // Side effect!
}
```

Our interface is **focused** and **cohesive**—only what clients truly need.

---

### **D - Dependency Inversion Principle**
*Depend on abstractions, not concretions.*

**Example: IdProcessor Constructor**
```java
public class IdProcessor {
    private final IdRule rule;  // Depends on INTERFACE, not concrete class
    
    public IdProcessor(IdRule rule) {
        this.rule = rule;  // Accepts any implementation
    }
}
```

**Why it matters:**
- `IdProcessor` (high-level module) doesn't depend on `SimpleRepeatRule` or `MultipleRepeatRule` (low-level modules)
- Both high-level and low-level modules depend on `IdRule` abstraction
- Makes testing easy: can inject mock rules for testing
- Provides runtime flexibility: choose which rule to use

**Example: IdRange.sumValidIds()**
```java
public long sumValidIds(IdRule rule) {
    return LongStream.rangeClosed(start, end)
        .map(rule::evaluate)  // Depends on abstraction
        .sum();
}
```

The domain model (`IdRange`) depends on the abstraction (`IdRule`), not on concrete validation implementations. This inverts the traditional dependency direction where domain models would depend on concrete business logic.

**Dependency Flow:**
```
Traditional (BAD):
IdRange → SimpleRepeatRule (concrete dependency)

Our Design (GOOD):
IdRange → IdRule ← SimpleRepeatRule
IdRange → IdRule ← MultipleRepeatRule
(Both depend on abstraction)
```

---

## Additional Design Patterns

### **Factory Method Pattern**
```java
public static IdRange parse(String rangeStr) {
    // Static factory method for object creation
    String[] parts = rangeStr.split("-");
    return new IdRange(
        Long.parseLong(parts[0].trim()),
        Long.parseLong(parts[1].trim())
    );
}
```
**Benefits:** Encapsulates parsing logic, provides clear API, allows adding validation before construction.

---

### **Functional Programming Paradigm**
The solution heavily leverages functional programming:

```java
// Immutable data structures
public record IdRange(long start, long end) { }

// Stream pipelines
ranges.stream()
    .map(IdRange::parse)           // Transform
    .mapToLong(range -> range.sumValidIds(rule))  // Map & reduce
    .sum();                        // Aggregate

// Method references
LongStream.rangeClosed(start, end)
    .map(rule::evaluate)           // Method reference for clean code
    .sum();
```

**Benefits:**
- Immutability prevents bugs
- Declarative style improves readability
- Lazy evaluation improves performance
- Easy to parallelize if needed

---

### **Mapper/Transformer Pattern**
```java
public class Day02Mapper implements InputMapper<List<String>> {
    @Override
    public List<String> map(List<String> lines) {
        return lines.stream()
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .flatMap(line -> Arrays.stream(line.split(",")))
            .toList();
    }
}
```

**Benefits:** Separates input transformation from business logic, reusable across different problems, testable in isolation.

---
