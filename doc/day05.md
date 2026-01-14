# Day 05: Cafeteria

## Introduction and Problem Overview

### The Problem
Process a series of numeric ranges to determine how many given numbers fall within their boundaries. The second part introduces complexity by requiring you to merge overlapping ranges efficiently.
### Generalization of the problem
This solution analyzes inventory freshness by comparing ingredient ranges against available stock. The challenge involves:
- **Input**: Two sections—fresh ingredient ranges (e.g., "100-200") and available IDs (e.g., "150")
- **Goal**: Analyze relationship between fresh ranges and available inventory
- **Output**: Numerical analysis results

Two analysis approaches:
1. **Part One**: Count how many available IDs fall within fresh ranges
2. **Part Two**: Calculate total capacity of all fresh ranges (with overlap handling)

### Core Data Structures

**IngredientRange (Domain Model)**
- Represents ID range with inclusive boundaries
- Implements `Comparable` for sorting (needed for range merging)
- Provides containment checking and size calculation

**Day05Data (DTO)**
- Encapsulates two input sections: ranges and IDs
- Immutable record type for data transfer

**InventoryAnalyzer (Strategy Interface)**
- Defines different analysis algorithms
- Returns long integer result

---

## Architecture and Data Flow

### Main.java - Entry Point
```
Input File → Day05Mapper → Day05Data {ranges, ids}
                                ↓
    InventoryProcessor (StockFreshnessChecker) → Count
                                ↓
    InventoryProcessor (TotalFreshCapacityEstimator) → Capacity
```

**Key Design:** Strategy Pattern enables different analyses on same data.

---

### Day05Mapper.java - Section Parsing
```
Lines with blank separator
        ↓
Join to single string
        ↓
Split on blank lines (\n\s*\n)
        ↓
sections[0] → ranges, sections[1] → ids
        ↓
Day05Data(ranges, ids)
```

**Unique Challenge:** Input has two distinct sections separated by blank lines.

**Algorithm:**
```java
String content = String.join("\n", lines);
String[] sections = content.split("\\n\\s*\\n");
return new Day05Data(
    Arrays.asList(sections[0].split("\\R")),
    Arrays.asList(sections[1].split("\\R"))
);
```

---

### InventoryProcessor.java - Service Layer
```
Day05Data → extract ranges & ids
              ↓
    analyzer.analyze(ranges, ids)
              ↓
          Result
```

**Responsibilities:**
- Orchestrates analysis process
- Provides input validation
- Offers multiple convenience methods

**Multiple Input Methods:**
```java
analyze(Day05Data data)                    // Main method
analyze(List<String> ranges, List<String> ids)  // Convenience overload
```

**Validation:** Null checks for all inputs with clear error messages.

---

### Day05Data.java - Data Transfer Object
Simple record holding both input sections:
```java
public record Day05Data(List<String> ranges, List<String> ids) {}
```

**Benefits:**
- Immutability
- Clean parameter passing (2 lists → 1 object)
- Auto-generated equals/hashCode/toString

---

### IngredientRange.java - Domain Model
```
String "100-200" → parse → IngredientRange(100, 200)
                              ↓
            Methods: contains(), size(), compareTo()
```

**Key Features:**

**Validation in Constructor:**
```java
public IngredientRange {
    if(start > end) throw new IllegalArgumentException("...");
}
```

**Comparable Implementation:**
```java
@Override
public int compareTo(IngredientRange o) {
    return Long.compare(start, o.start);  // Sort by start position
}
```

**Why Comparable?** Required for TotalFreshCapacityEstimator's merge algorithm.

---

### InventoryAnalyzer.java - Strategy Interface
```java
public interface InventoryAnalyzer {
    long analyze(List<String> freshRanges, List<String> availableIds);
}
```

Minimal interface enabling different analysis strategies.

---

### StockFreshnessChecker.java - Containment Analysis
```
For each available ID:
    Check if ANY fresh range contains it
        ↓
    Count matches
        ↓
Return count of fresh IDs in stock
```

**Algorithm:**
```java
@Override
public long analyze(List<String> freshRanges, List<String> availableIds) {
    List<IngredientRange> ranges = freshRanges.stream()
        .map(IngredientRange::parse)
        .toList();
    
    return availableIds.stream()
        .mapToLong(Long::parseLong)
        .filter(id -> ranges.stream().anyMatch(r -> r.contains(id)))
        .count();
}
```

**Example:**
- Fresh ranges: ["100-200", "300-400"]
- Available IDs: ["150", "250", "350"]
- Result: 2 (150 and 350 are fresh, 250 is not)

---

### TotalFreshCapacityEstimator.java - Capacity with Merging
```
Parse ranges → Sort by start → Merge overlaps → Sum sizes
```

**Why Merge?** Overlapping ranges would double-count IDs.

**Example:**
- Ranges: ["100-200", "150-250", "300-400"]
- Merged: ["100-250", "300-400"]
- Result: 151 + 101 = 252 (not 101+101+101=303)

**Merge Algorithm:**
```java
private List<IngredientRange> mergeRanges(List<IngredientRange> ranges) {
    if(ranges.isEmpty()) return Collections.emptyList();
    
    List<IngredientRange> merged = new ArrayList<>();
    IngredientRange current = ranges.get(0);
    
    for(int i = 1; i < ranges.size(); i++){
        IngredientRange next = ranges.get(i);
        if(next.start() <= current.end() + 1){
            // Merge: overlapping or adjacent
            current = new IngredientRange(
                current.start(), 
                Math.max(current.end(), next.end())
            );
        } else {
            // Gap: save current and start new
            merged.add(current);
            current = next;
        }
    }
    merged.add(current);  // Don't forget last range!
    return merged;
}
```

**Merge Conditions:**
- `next.start() <= current.end()` → Overlapping: [100-200] & [150-250]
- `next.start() == current.end() + 1` → Adjacent: [100-200] & [201-300]

**Both cases:** `next.start() <= current.end() + 1`

**Visual Example:**
```
Before: [100-200] [150-250] [300-400] [350-500]
After:  [100-250]           [300-500]
```

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**

**IngredientRange** - Only manages range data and operations
- Does NOT: Perform analysis, merge ranges, or read input

**StockFreshnessChecker** - Only counts fresh IDs in stock
- Does NOT: Merge ranges, calculate capacity, or process files

**TotalFreshCapacityEstimator** - Only calculates total capacity
- Does NOT: Check individual IDs or read input

**Day05Mapper** - Only parses input format
- Does NOT: Analyze data or know business rules

---

### **O - Open/Closed Principle**

**Adding new analyzers is trivial:**
```java
// Count IDs outside fresh ranges (expired stock)
public class ExpiredStockCounter implements InventoryAnalyzer {
    @Override
    public long analyze(List<String> freshRanges, List<String> availableIds) {
        List<IngredientRange> ranges = freshRanges.stream()
            .map(IngredientRange::parse)
            .toList();
        
        return availableIds.stream()
            .mapToLong(Long::parseLong)
            .filter(id -> ranges.stream().noneMatch(r -> r.contains(id)))
            .count();
    }
}

// Use without changing existing code
new InventoryProcessor(new ExpiredStockCounter()).analyze(data);
```

**Why it matters:** Part Two added by creating new analyzer class, zero modifications to existing code.

---

### **L - Liskov Substitution Principle**

**Both analyzers are fully interchangeable:**
```java
InventoryProcessor processor1 = new InventoryProcessor(new StockFreshnessChecker());
InventoryProcessor processor2 = new InventoryProcessor(new TotalFreshCapacityEstimator());

// Both work correctly with same interface
long result1 = processor1.analyze(ranges, ids);  // Count of fresh IDs
long result2 = processor2.analyze(ranges, ids);  // Total capacity
```

Both return long, both process same inputs, both uphold the contract.

---

### **I - Interface Segregation Principle**

**InventoryAnalyzer is minimal:**
```java
public interface InventoryAnalyzer {
    long analyze(List<String> freshRanges, List<String> availableIds);
}
```

One method, no forced dependencies. Implementations aren't burdened with unused methods.

---

### **D - Dependency Inversion Principle**

**InventoryProcessor depends on abstraction:**
```java
public class InventoryProcessor {
    private final InventoryAnalyzer analyzer;  // Interface, not concrete class
    
    public InventoryProcessor(InventoryAnalyzer analyzer) {
        this.analyzer = analyzer;
    }
}
```

High-level processor doesn't depend on low-level analyzers—both depend on the abstraction.

---

