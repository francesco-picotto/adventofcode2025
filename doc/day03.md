# Day 03: Lobby

## Introduction and Problem Overview

### The Problem
This solution tackles a bank code extraction puzzle where we need to extract specific numerical values from bank code strings by selecting digits based on maximum value constraints. The challenge involves:
- **Input**: Bank code strings containing digit sequences
- **Goal**: Extract specific digits by finding maximum values within constrained search ranges
- **Output**: Sum of all extracted numerical values from all bank codes

The challenge has two parts:
1. **Part One**: Extract a 2-digit value by finding maximum characters in two non-overlapping ranges
2. **Part Two**: Extract a 12-digit value using a sliding window approach with progressively constrained search ranges

### Core Data Structures

**String Processing**
- Bank codes represented as strings for easy character-by-character access
- Character comparison leverages Java's natural lexicographic ordering
- StringBuilder used for efficient string concatenation during extraction

**BankRule (Strategy Interface)**
- Defines different extraction algorithms for bank codes
- Returns long integer representing the extracted numerical value
- Includes static utility method for finding maximum character index

**Stream-Based Processing**
- Uses Java Streams for functional processing of bank code lists
- Filters empty strings before processing
- Maps each bank code to its extracted value and sums results

---

## Architecture and Data Flow

The solution follows a clean, functional architecture with clear separation between extraction algorithms and processing logic. Data flows from input → processing → rule evaluation → aggregation.

### Main.java - Entry Point
```
Input File → InputProvider → List<String> bank codes
                                ↓
         BankProcessor (SimpleBankRule) → Part 1 Sum
                                ↓
         BankProcessor (AdvancedBankRule) → Part 2 Sum
```

**Responsibilities:**
- Reads bank code strings from input file
- Creates two `BankProcessor` instances with different extraction rules
- Outputs sums for both parts

**Key Design Choice:** Uses the Strategy Pattern to apply different extraction algorithms to the same data set, demonstrating polymorphic behavior without code duplication.

---

### BankProcessor.java - Service Layer
```
List<String> bank codes → filter empty strings
                               ↓
                     rule.evaluate(bank)
                               ↓
                        aggregate sums
                               ↓
                          Total Sum
```

**Responsibilities:**
- Orchestrates the extraction process across all bank codes
- Filters out invalid/empty entries
- Delegates extraction logic to injected `BankRule`
- Aggregates results using stream operations

**Data Flow:**
1. Receives extraction rule via constructor (dependency injection)
2. Streams through bank code strings
3. Filters out empty strings
4. Maps each bank code to its extracted value using `rule.evaluate()`
5. Sums all extracted values into final total

**Functional Approach:** Uses `mapToLong()` for efficient primitive stream processing, avoiding boxing overhead for long values.

---

### BankRule.java - Strategy Interface
```java
public interface BankRule {
    long evaluate(String bank);
    
    static int findMaxIndex(String s, int start, int end) {
        // Utility method for finding max character index
    }
}
```

**Responsibilities:**
- Defines contract for extraction strategies
- Provides reusable utility method for finding maximum characters
- Enables polymorphic behavior in extraction logic

**Key Design Feature: Static Utility Method**
```java
static int findMaxIndex(String s, int start, int end) {
    int maxIdx = start;
    for (int i = start + 1; i <= end; i++) {
        if (s.charAt(i) > s.charAt(maxIdx)) {
            maxIdx = i;
        }
    }
    return maxIdx;
}
```

**Why include this in the interface?**
- **Code reuse**: Both implementations need this functionality
- **Interface evolution**: Java 8+ allows default/static methods in interfaces
- **Cohesion**: The logic is intimately related to the extraction strategy
- **Single source of truth**: Prevents duplicate implementations with potential bugs

**Algorithm:**
- Scans range from `start` to `end` (inclusive)
- Tracks index of character with highest value
- Returns first occurrence if multiple characters tie for maximum
- Time complexity: O(n) where n is range size

---

### SimpleBankRule.java - Two-Digit Extraction
```
Bank code "582917"
      ↓
Find max in [0, len-2] = [0, 4]
      ↓
Max is '9' at index 3
      ↓
Find max in [4, len-1] = [4, 5]
      ↓
Max is '7' at index 5
      ↓
Combine: "97" → 97
```

**Responsibilities:**
- Extracts exactly 2 digits from bank code
- Ensures non-overlapping selection ranges
- Maintains sequential order of selected digits

**Algorithm:**
1. Calculate bank code length
2. Find first digit:
    - Search range: `[0, length-2]`
    - This ensures at least one character remains for second digit
    - Store index of maximum character as `firstIdx`
3. Find second digit:
    - Search range: `[firstIdx+1, length-1]`
    - Starts after first digit to prevent overlap
    - Store index of maximum character as `secondIdx`
4. Extract characters at both indices and combine into 2-digit number
5. Parse and return as long integer

**Range Constraints:**
- First search ends at `length-2` to guarantee space for second digit
- Second search starts at `firstIdx+1` to ensure sequential ordering
- This design ensures we always have valid indices for both digits

**Example Walkthrough:**
```
Bank code: "582917"
Length: 6

Step 1: Find max in [0, 4]
  Candidates: '5', '8', '2', '9', '1'
  Maximum: '9' at index 3

Step 2: Find max in [4, 5]
  Candidates: '1', '7'
  Maximum: '7' at index 5

Result: "97" → 97
```

---

### AdvancedBankRule.java - Twelve-Digit Extraction
```
Bank code (length n)
      ↓
For k=0 to 11:
  start = position after last selected digit
  end = n - 12 + k (ensures 12-k chars remain)
      ↓
  Find max in [start, end]
      ↓
  Append to result
      ↓
Return 12-digit number
```

**Responsibilities:**
- Extracts exactly 12 digits from bank code
- Uses sliding window approach with progressively constrained ranges
- Ensures selected digits appear in sequential index order

**Algorithm:**
The key insight is the **constrained sliding window**:

```java
for (int k = 0; k < J_LENGTH; k++) {
    int start = currentLastIdx + 1;
    int end = len - J_LENGTH + k;
    
    currentLastIdx = BankRule.findMaxIndex(bank, start, end);
    joltagestr.append(bank.charAt(currentLastIdx));
}
```

**Window Constraints:**
- `start = currentLastIdx + 1`: Ensures we search after the previous digit (sequential order)
- `end = len - J_LENGTH + k`: Ensures enough characters remain for subsequent selections

**Why this formula works:**
- At iteration k, we need to select digit (k+1) out of 12
- Remaining digits to select: `12 - k - 1`
- Must leave this many characters after current selection
- Therefore, end position: `len - 1 - (12 - k - 1) = len - 12 + k`

**Example Walkthrough:**
```
Bank code: "98765432109876543210" (length=20)

Iteration 0 (k=0, need 12 more digits):
  start = 0, end = 20 - 12 + 0 = 8
  Search [0, 8]: "987654321"
  Max: '9' at index 0
  Selected: '9'

Iteration 1 (k=1, need 11 more digits):
  start = 1, end = 20 - 12 + 1 = 9
  Search [1, 9]: "876543210"
  Max: '8' at index 1
  Selected: '8'

Iteration 2 (k=2, need 10 more digits):
  start = 2, end = 20 - 12 + 2 = 10
  Search [2, 10]: "765432109"
  Max: '9' at index 9
  Selected: '9'

... continues for all 12 digits

Final result: 12-digit number constructed from selected maxima
```

**Key Properties:**
1. **Sequential ordering**: Each digit selected comes from an index later than the previous
2. **Sufficient remaining**: The end constraint guarantees enough characters remain
3. **Greedy maximization**: At each step, we pick the largest available digit within constraints
4. **No overlapping**: Each character can only be selected once

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**
*Each class has one clear reason to change.*

**Example: BankProcessor.java**
- **Only responsible for**: Orchestrating the extraction process and aggregating results
- **Does NOT**: Know how to extract digits, parse input formats, or implement extraction algorithms
- **Why it matters**: Changes to extraction logic don't affect the processing pipeline. Changes to how we aggregate results don't affect extraction algorithms.

**Example: SimpleBankRule vs AdvancedBankRule**
- Each rule has **one extraction algorithm**
- `SimpleBankRule` only knows how to extract 2 digits
- `AdvancedBankRule` only knows how to extract 12 digits
- Neither knows about file I/O, aggregation, or other rules
- **Why it matters**: Each rule can be modified independently without affecting others

**Example: BankRule.java**
- **Only responsible for**: Defining the extraction contract and providing utility method
- **Does NOT**: Process lists of bank codes, read files, or aggregate results
- **Why it matters**: Interface remains stable even as implementations evolve

---

### **O - Open/Closed Principle**
*Open for extension, closed for modification.*

**Example: Adding New Extraction Rules**
```java
// Want to extract the first and last digit? Just add a new rule!
public class EdgeDigitRule implements BankRule {
    @Override
    public long evaluate(String bank) {
        if (bank.length() < 2) return 0;
        char first = bank.charAt(0);
        char last = bank.charAt(bank.length() - 1);
        return Long.parseLong("" + first + last);
    }
}

// Want to extract median digits? Add another rule!
public class MedianDigitRule implements BankRule {
    @Override
    public long evaluate(String bank) {
        int mid = bank.length() / 2;
        return Long.parseLong("" + bank.charAt(mid-1) + bank.charAt(mid));
    }
}

// Use either without changing ANY existing code
new BankProcessor(new EdgeDigitRule()).solve(input);
new BankProcessor(new MedianDigitRule()).solve(input);
```

**Why it matters:**
- We solved Part Two by adding `AdvancedBankRule` without modifying `BankProcessor`, `SimpleBankRule`, or the interface
- The system is **open** to new extraction strategies but **closed** to modification of core processing logic
- Adding future extraction rules requires zero changes to existing code

---

### **L - Liskov Substitution Principle**
*Subtypes must be substitutable for their base types.*

**Example: Rule Interchangeability**
```java
// Both rules work identically from the processor's perspective
BankProcessor processor1 = new BankProcessor(new SimpleBankRule());
BankProcessor processor2 = new BankProcessor(new AdvancedBankRule());

// Both produce correct results using the same interface contract
long result1 = processor1.solve(bankCodes);  // Sum of 2-digit extractions
long result2 = processor2.solve(bankCodes);  // Sum of 12-digit extractions
```

**Contract Guarantee:**
- Every `BankRule` implementation must return a valid long integer
- The extracted value must be derived from the input bank code string
- The method must not throw exceptions for valid input
- Both `SimpleBankRule` and `AdvancedBankRule` uphold this contract

**Why it matters:**
The `BankProcessor.solve()` method works correctly with **any** BankRule implementation:
```java
public long solve(List<String> banks) {
    return banks.stream()
        .filter(bank -> !bank.isEmpty())
        .mapToLong(rule::evaluate)  // Works with ANY BankRule
        .sum();
}
```

We can substitute rules freely without breaking behavior or requiring code changes.

---

### **I - Interface Segregation Principle**
*Clients shouldn't depend on interfaces they don't use.*

**Example: BankRule Interface**
```java
public interface BankRule {
    long evaluate(String bank);  // Core method - all clients need this
    
    static int findMaxIndex(String s, int start, int end) {
        // Utility method - implementations CAN use, but aren't forced to
    }
}
```

**Why it's well-designed:**
- Minimal interface with one abstract method
- Static utility method is optional—implementations can use it or not
- No forced dependencies on logging, validation, or other unrelated concerns
- No unnecessary methods that would burden implementers

**Contrast with a BAD design:**
```java
// BAD: Bloated interface forcing unused dependencies
public interface BankRule {
    long evaluate(String bank);
    boolean validate(String bank);        // Not all rules need validation
    String getDescription();              // Metadata not always needed
    int getDigitCount();                  // Implementation detail leaked
    void logExtraction(String bank);      // Side effects forced on all
    List<Integer> getSelectedIndices();   // Forces state tracking
}
```

Our interface is **focused** and **minimal**—only what all clients truly need. The static utility is provided for convenience but doesn't pollute the contract.

---

### **D - Dependency Inversion Principle**
*Depend on abstractions, not concretions.*

**Example: BankProcessor Constructor**
```java
public class BankProcessor {
    private final BankRule rule;  // Depends on INTERFACE, not concrete class
    
    public BankProcessor(BankRule rule) {
        this.rule = rule;  // Accepts any implementation
    }
    
    public long solve(List<String> banks) {
        return banks.stream()
            .filter(bank -> !bank.isEmpty())
            .mapToLong(rule::evaluate)  // Polymorphic call
            .sum();
    }
}
```

**Why it matters:**
- `BankProcessor` (high-level module) doesn't depend on `SimpleBankRule` or `AdvancedBankRule` (low-level modules)
- Both high-level and low-level modules depend on `BankRule` abstraction
- Makes testing trivial: can inject mock rules for testing
- Provides runtime flexibility: decide which rule to use at runtime

**Example: Main.java demonstrates DIP**
```java
// High-level code controls which implementation to use
var processor1 = new BankProcessor(new SimpleBankRule());
var processor2 = new BankProcessor(new AdvancedBankRule());

// Could also use factory pattern, configuration, etc.
BankRule rule = getRuleFromConfig();  // Runtime decision
var processor3 = new BankProcessor(rule);
```

**Dependency Flow:**
```
Traditional Approach (BAD):
BankProcessor → SimpleBankRule (concrete dependency)
BankProcessor → AdvancedBankRule (concrete dependency)

Our Design (GOOD):
BankProcessor → BankRule ← SimpleBankRule
BankProcessor → BankRule ← AdvancedBankRule

Both depend on abstraction!
```

**Testing Benefits:**
```java
// Easy to create test double
class MockBankRule implements BankRule {
    @Override
    public long evaluate(String bank) {
        return 42;  // Predictable test value
    }
}

// Test without touching real implementations
@Test
void testProcessing() {
    var processor = new BankProcessor(new MockBankRule());
    assertEquals(126, processor.solve(List.of("a", "b", "c")));
}
```

---

## Additional Design Patterns

### **Strategy Pattern (Core Pattern)**
The entire solution is built around the Strategy Pattern:

**Components:**
- **Context**: `BankProcessor` (uses the strategy)
- **Strategy Interface**: `BankRule` (defines algorithm contract)
- **Concrete Strategies**: `SimpleBankRule` and `AdvancedBankRule` (implement algorithms)

**Benefits:**
- Algorithm selection at runtime
- Easy to add new extraction algorithms
- Algorithms can be tested independently
- Client code (Main) controls which algorithm to use

---

### **Template Method Pattern (Implicit)**
Both extraction rules follow a similar template:
1. Calculate bank code length
2. Determine search range(s)
3. Find maximum character(s) in range(s)
4. Extract and combine characters
5. Parse to long integer

The `findMaxIndex` utility method acts as a shared step in this template.

---

### **Functional Programming Paradigm**
The solution leverages functional programming concepts:

```java
// Stream pipeline with method references
banks.stream()
    .filter(bank -> !bank.isEmpty())  // Predicate
    .mapToLong(rule::evaluate)        // Method reference
    .sum();                           // Terminal operation

// Immutability
private final BankRule rule;  // Immutable field

// Stateless operations
static int findMaxIndex(...)  // Pure function
```

**Benefits:**
- Declarative style improves readability
- Lazy evaluation improves performance
- Easy to parallelize if needed (`parallelStream()`)
- Reduced risk of side effects and bugs

---

### **Utility Method in Interface**
Java 8+ allows static methods in interfaces:

```java
static int findMaxIndex(String s, int start, int end) {
    // Shared utility available to all implementations
}
```

**Advantages:**
- **Cohesion**: Related functionality lives with the interface
- **Code reuse**: No need for separate utility class
- **Discoverability**: Easy to find when implementing the interface
- **Namespace**: Clear that this utility is specific to BankRule context

**Alternative approaches avoided:**
- Separate `BankRuleUtils` class (more files, less cohesive)
- Duplicate code in each implementation (DRY violation)
- Default method (wouldn't make sense as instance method)

---

## Algorithm Analysis

### **SimpleBankRule Complexity**
- **Time**: O(n) where n is bank code length
    - First `findMaxIndex`: scans up to n-1 characters
    - Second `findMaxIndex`: scans remaining characters
    - Total: O(n) + O(n) = O(n)
- **Space**: O(1) - only stores indices and characters

### **AdvancedBankRule Complexity**
- **Time**: O(12 × n) = O(n) where n is bank code length
    - 12 iterations (constant)
    - Each iteration scans a portion of the string
    - Average scan length decreases as window narrows
    - Total work is linear in input length
- **Space**: O(1) - StringBuilder builds fixed-length result (12 chars)

### **BankProcessor Complexity**
- **Time**: O(m × n) where m is number of bank codes, n is average length
    - Processes each bank code once
    - Each processing is O(n) regardless of rule
- **Space**: O(1) for processing (streams don't store intermediate results)

**Performance Characteristics:**
- Both rules are efficient for reasonable input sizes
- No nested loops with quadratic behavior
- Stream processing is lazy and memory-efficient
- Could easily parallelize for large datasets: `banks.parallelStream()`

---

## Design Decisions and Rationale

### **Why Static Utility in Interface?**
**Decision**: Place `findMaxIndex` as static method in `BankRule` interface

**Rationale:**
- Both implementations need this exact functionality
- Method is stateless and doesn't depend on instance state
- Keeps related code together (high cohesion)
- Avoids creating separate utility class for one method
- Makes implementation code cleaner and more focused

**Alternative considered**: Abstract base class
```java
abstract class AbstractBankRule implements BankRule {
    protected int findMaxIndex(...) { ... }
}
```
**Why rejected**: Doesn't add value—implementations don't share other logic, and Java 8+ allows static methods in interfaces, making this cleaner.

---

### **Why Return Long Instead of String?**
**Decision**: `evaluate()` returns `long` instead of `String`

**Rationale:**
- Final result is a sum of numbers (mathematical operation)
- Long can represent up to 19 digits (sufficient for 12-digit extraction)
- Prevents unnecessary string-to-number conversions in processor
- Makes the intent clear: these are numerical values, not text
- Enables direct use of `mapToLong()` in streams

---

### **Why Filter Empty Strings in Processor?**
**Decision**: `BankProcessor` filters empty strings before evaluation

**Rationale:**
- Defensive programming: handles malformed input gracefully
- Keeps rule implementations simple—they don't need to validate input
- Single Responsibility: validation is processor's concern, extraction is rule's concern
- Empty strings would cause errors in `findMaxIndex` (invalid range)

---

### **Why Progressive Window Narrowing?**
**Decision**: `AdvancedBankRule` uses formula `end = len - J_LENGTH + k`

**Rationale:**
- Guarantees exactly 12 digits can always be extracted
- Ensures sequential ordering (selected indices are increasing)
- Maximizes each digit within constraints
- Mathematical correctness: remaining characters must accommodate remaining selections
- Elegant solution without explicit lookahead or backtracking