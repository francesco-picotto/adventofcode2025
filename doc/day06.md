# Day 06: Math Worksheet Parser Solution

## Introduction and Problem Overview

### The Problem
This solution parses visual math worksheet representations where problems are arranged in columns. The challenge involves:
- **Input**: Multi-line text representing math problems in columnar format
- **Goal**: Parse problems from visual layout and calculate results
- **Output**: Sum of all problem solutions

Visual format example:
```
123  456
789  012
 +    *
```
Two problems: 123+789 and 456×012

Two parsing approaches:
1. **Part One**: Standard left-to-right horizontal reading
2. **Part Two**: Reverse right-to-left with vertical digit reading

### Core Data Structures

**CephalopodProblem (Domain Model)**
- Represents single math problem with numbers and operator
- Immutable record with `solve()` method
- Self-contained computation

**Operator (Enum)**
- Encapsulates operation behavior (ADD, MULTIPLY)
- Stores symbol, operation function, and identity value
- Applies operation to list of numbers using reduce

**WorksheetUtils (Utility Class)**
- Provides column analysis methods
- Shared between different analyzer implementations
- Prevents code duplication

---

## Architecture and Data Flow

### Main.java - Entry Point
```
Input Lines → MathWorksheetProcessor (StandardColumnAnalyzer) → Sum 1
                                    ↓
            MathWorksheetProcessor (ReverseVerticalAnalyzer) → Sum 2
```

**Key Design:** Same visual input, different parsing strategies yield different interpretations.

---

### MathWorksheetProcessor.java - Service Layer
```
List<String> lines → analyzer.analyze(lines) → Sum of results
```

**Responsibilities:**
- Orchestrates worksheet processing
- Delegates all parsing logic to analyzer
- Thin wrapper maintaining consistent interface

**Simple delegation:**
```java
public long solve(List<String> input) {
    return analyzer.analyze(input);
}
```

---

### MathWorksheetAnalyzer.java - Strategy Interface
```java
public interface MathWorksheetAnalyzer {
    long analyze(List<String> lines);
}
```

Minimal interface for different parsing strategies.

---

### CephalopodProblem.java - Domain Model
```java
public record CephalopodProblem(List<Long> numers, Operator operator) {
    public long solve() {
        return operator.apply(numers);
    }
}
```

**Simple self-solving model:**
- Numbers + operator = complete problem representation
- Delegates calculation to operator
- Immutable record

---

### Operator.java - Enum with Behavior
```java
public enum Operator {
    ADD('+', (a, b) -> a + b, 0L),
    MULTIPLY('*', (a, b) -> a * b, 1L);
    
    public long apply(List<Long> numbers) {
        return numbers.stream().reduce(identity, operation);
    }
}
```

**Key Features:**

**Encapsulated Operation:**
- Symbol for parsing
- Binary operation function
- Identity value for reduction

**Reduce Pattern:**
```java
ADD.apply([5, 3, 2]) → 0 + 5 + 3 + 2 = 10
MULTIPLY.apply([5, 3, 2]) → 1 × 5 × 3 × 2 = 30
```

**Factory Method:**
```java
Operator.fromChar('+') → ADD
Operator.fromChar('*') → MULTIPLY
```

---

### WorksheetUtils.java - Shared Utilities
Provides column operations to avoid duplication:

**`isColumnEmpty(List<String> lines, int col)`**
- Checks if column is all spaces across all lines
- Used to identify problem separators

**`getColumnPart(List<String> lines, int col)`**
- Extracts non-space characters from a column vertically
- Used for vertical digit reading

**Why static utilities?** Both analyzers need these operations but have different parsing logic.

---

### StandardColumnAnalyzer.java - Horizontal Parsing
```
Scan left→right → Find empty columns → Extract blocks → Parse horizontally
```

**Algorithm:**
1. Scan columns left-to-right
2. Empty columns separate problems
3. Within each block: read rows horizontally
4. Last row contains operator, other rows contain numbers

**Visual Example:**
```
123  456
789  012
 +    *
```

**Parsing:**
- Block 1 (cols 0-2): numbers=[123, 789], operator='+'
- Block 2 (cols 5-7): numbers=[456, 012], operator='*'

**Code Structure:**
```java
// Scan for blocks
for (int j = 0; j <= width; j++) {
    if (j == width || WorksheetUtils.isColumnEmpty(lines, j)) {
        if (j > startCol) {
            problems.add(extractProblem(lines, startCol, j));
        }
        startCol = j + 1;
    }
}

// Extract problem from block
for (int i = 0; i < lines.size(); i++) {
    String part = line.substring(start, end).trim();
    if (i == lines.size() - 1) {
        opChar = part.charAt(0);  // Last row = operator
    } else {
        nums.add(Long.parseLong(part));  // Other rows = numbers
    }
}
```

---

### ReverseVerticalAnalyzer.java - Vertical Parsing
```
Scan right→left → Find empty columns → Extract blocks → Parse vertically
```

**Algorithm:**
1. Scan columns right-to-left
2. Empty columns separate problems
3. Within each block: read columns vertically, right-to-left
4. Each column forms one number (digits stacked vertically)

**Visual Example:**
```
1 2
3 4
 *
```

**Vertical reading (right-to-left):**
- Column 2: '2','4' → number 24
- Column 0: '1','3' → number 13
- Result: [24, 13], operator='*' → 24×13

**Key Difference:** Each **column** is a number (digits stacked), not each **row**.

**Code Structure:**
```java
// Scan right-to-left
for (int j = width - 1; j >= -1; j--) {
    if (j == -1 || WorksheetUtils.isColumnEmpty(lines, j)) {
        if (endCol > j + 1) {
            problems.add(extractVerticalProblem(lines, j + 1, endCol));
        }
        endCol = j;
    }
}

// Extract with vertical reading
for (int j = end - 1; j >= start; j--) {
    // Read column vertically (excluding operator row)
    String columnDigits = WorksheetUtils.getColumnPart(
        lines.subList(0, lines.size() - 1), j
    ).trim();
    if (!columnDigits.isEmpty()) {
        nums.add(Long.parseLong(columnDigits));
    }
}
```

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**

**CephalopodProblem** - Only represents and solves a single problem
- Does NOT: Parse input, iterate worksheets, or manage collections

**Operator** - Only encapsulates operation behavior
- Does NOT: Parse worksheets, store problems, or manage state

**WorksheetUtils** - Only provides column utilities
- Does NOT: Parse problems, execute operations, or store data

**StandardColumnAnalyzer** - Only parses left-to-right horizontally
- Does NOT: Parse vertically, execute operations, or store state

---

### **O - Open/Closed Principle**

**Adding new parsing strategy:**
```java
// Parse diagonally from top-left to bottom-right
public class DiagonalAnalyzer implements MathWorksheetAnalyzer {
    @Override
    public long analyze(List<String> lines) {
        // Custom diagonal parsing logic
    }
}

// Use without changing existing code
new MathWorksheetProcessor(new DiagonalAnalyzer()).solve(input);
```

**Adding new operator:**
```java
public enum Operator {
    ADD('+', (a, b) -> a + b, 0L),
    MULTIPLY('*', (a, b) -> a * b, 1L),
    SUBTRACT('-', (a, b) -> a - b, 0L),  // Just add to enum!
    // ...
}
```

---

### **L - Liskov Substitution Principle**

**Both analyzers are interchangeable:**
```java
MathWorksheetProcessor p1 = new MathWorksheetProcessor(new StandardColumnAnalyzer());
MathWorksheetProcessor p2 = new MathWorksheetProcessor(new ReverseVerticalAnalyzer());

// Both work correctly with same interface
long result1 = p1.solve(lines);
long result2 = p2.solve(lines);
```

Different interpretations of same input, but both uphold the contract: parse and sum.

---

### **I - Interface Segregation Principle**

**MathWorksheetAnalyzer is minimal:**
```java
public interface MathWorksheetAnalyzer {
    long analyze(List<String> lines);
}
```

One method, no forced dependencies. Simple and focused.

---

### **D - Dependency Inversion Principle**

**Processor depends on abstraction:**
```java
public class MathWorksheetProcessor {
    private final MathWorksheetAnalyzer analyzer;  // Interface, not concrete
    
    public MathWorksheetProcessor(MathWorksheetAnalyzer analyzer) {
        this.analyzer = analyzer;
    }
}
```

High-level processor doesn't depend on specific analyzers—both depend on abstraction.

---

## Additional Design Patterns

### **Strategy Pattern (Core)**
- **Context:** MathWorksheetProcessor
- **Strategy:** MathWorksheetAnalyzer
- **Concrete Strategies:** StandardColumnAnalyzer, ReverseVerticalAnalyzer

### **Enum with Behavior**
`Operator` enum isn't just constants—it encapsulates operations:
```java
MULTIPLY('*', (a, b) -> a * b, 1L)
```
Each enum value is a complete strategy with symbol, function, and identity.

### **Utility Class**
`WorksheetUtils` provides shared functionality without inheritance:
- Static methods for column operations
- Composition over inheritance
- Reusable across different analyzers

