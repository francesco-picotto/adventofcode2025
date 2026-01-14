# Day 07: Laboratories

## Introduction and Problem Overview

### The Problem
Simulate a beam falling down a "Christmas tree" grid, splitting into two when it hits certain obstacles.
### Generalization of the problem
This solution simulates beam behavior through a tachyon manifold grid where beams encounter splitters. The challenge involves:
- **Input**: 2D character grid with 'S' (start) and '^' (splitters)
- **Goal**: Track beam propagation through splitters
- **Output**: Numerical analysis of beam behavior

Grid format:
```
  S
  .
  ^    ← Splitter splits beam into left + right
 . .
```

Two analysis approaches:
1. **Part One**: Count total beam split events
2. **Part Two**: Calculate total quantum timelines (path multiplicity)

### Core Data Structures

**TachyonManifold (Domain Model)**
- Encapsulates 2D grid with splitter positions
- Validates structure (requires 'S' marker)
- Provides boundary-safe access methods

**Set<Integer> (Active Beams)**
- Tracks column positions where beams exist
- Used by BeamSplitCounter for discrete beam tracking

**long[] (Timeline Counts)**
- Tracks number of timelines at each column
- Used by QuantumTimelineEstimator for path counting
- Enables dynamic programming approach

---

## Architecture and Data Flow

### Main.java - Entry Point
```
Input Grid → ManifoldProcessor (BeamSplitCounter) → Split Count
                              ↓
           ManifoldProcessor (QuantumTimelineEstimator) → Timeline Count
```

**Key Design:** Same grid, different metrics—one counts events, other counts paths.

---

### ManifoldProcessor.java - Service Layer
```
List<String> grid → analyzer.analyze(grid) → Result
```

Simple delegation to analyzer strategy.

---

### ManifoldAnalyzer.java - Strategy Interface
```java
public interface ManifoldAnalyzer {
    long analyze(List<String> grid);
}
```

Minimal interface for different beam tracking strategies.

---

### TachyonManifold.java - Domain Model
```java
public class TachyonManifold {
    public static final char SPLITTER = '^';
    public static final char START = 'S';
    
    public char getAt(int row, int col) { ... }
    public boolean isSplitter(int row, int col) { ... }
    public int getStartColumn() { ... }
}
```

**Key Features:**

**Validation in Constructor:**
```java
public TachyonManifold(List<String> grid) {
    if (grid == null || grid.isEmpty()) 
        throw new IllegalArgumentException("...");
    this.startColumn = grid.get(0).indexOf(START);
    if (this.startColumn == -1) 
        throw new IllegalStateException("'S' not found");
}
```

**Boundary-Safe Access:**
```java
public char getAt(int row, int col) {
    if (isOutOfBounds(row, col)) return ' ';
    return grid.get(row).charAt(col);
}
```

Returns space for out-of-bounds, preventing exceptions in simulation logic.

---

### BeamSplitCounter.java - Event Counting
```
Start: 1 beam at 'S' position
        ↓
For each row:
  Count splits (beams hitting '^')
  Calculate next row's beam positions
        ↓
Return total split count
```

**Algorithm:**
```java
@Override
public long analyze(List<String> grid) {
    TachyonManifold manifold = new TachyonManifold(grid);
    Set<Integer> activeBeams = new HashSet<>();
    activeBeams.add(manifold.getStartColumn());
    
    long totalSplits = 0;
    for (int r = 0; r < manifold.getHeight(); r++) {
        totalSplits += countSplitsInRow(manifold, r, activeBeams);
        activeBeams = calculateNextRowBeams(manifold, r, activeBeams);
    }
    return totalSplits;
}
```

**Beam Propagation Rules:**
- If splitter ('^'): beam splits → columns (c-1) and (c+1) in next row
- If empty ('.'): beam continues straight → same column in next row
- Multiple beams can converge on same column (Set handles deduplication)

**Example:**
```
Row 0: S at col 2 → 1 beam
Row 1: ^ at col 2 → SPLIT (count=1), beams to cols 1,3
Row 2: ^ at col 1 → SPLIT (count=2), beams to cols 0,2,3
Total splits: 2
```

---

### QuantumTimelineEstimator.java - Path Counting
```
Start: 1 timeline at 'S' position
        ↓
For each row:
  Propagate timeline counts to next row
  Splits distribute timelines to both branches
        ↓
Return sum of all timeline counts
```

**Key Insight:** Each split doubles the number of paths, tracked multiplicatively.

**Algorithm:**
```java
@Override
public long analyze(List<String> grid) {
    TachyonManifold manifold = new TachyonManifold(grid);
    long[] currentTimelines = new long[manifold.getWidth()];
    currentTimelines[manifold.getStartColumn()] = 1;
    
    for (int r = 0; r < manifold.getHeight(); r++) {
        currentTimelines = calculateNextRow(manifold, r, currentTimelines);
    }
    return Arrays.stream(currentTimelines).sum();
}

private long[] calculateNextRow(TachyonManifold manifold, int row, long[] current) {
    long[] next = new long[manifold.getWidth()];
    for (int c = 0; c < manifold.getWidth(); c++) {
        if (current[c] == 0) continue;
        
        if (manifold.isSplitter(row, c)) {
            // Split: timelines go to both adjacent columns
            if (c - 1 >= 0) next[c - 1] += current[c];
            if (c + 1 < manifold.getWidth()) next[c + 1] += current[c];
        } else {
            // Continue straight
            next[c] += current[c];
        }
    }
    return next;
}
```

**Dynamic Programming Approach:**
- Array tracks timeline count at each column
- When beams converge, timeline counts add
- When beams split, timeline counts distribute

**Example:**
```
Row 0: [0, 0, 1, 0, 0] (1 timeline at col 2)
Row 1: ^ at col 2 → [0, 1, 0, 1, 0] (splits to cols 1,3)
Row 2: ^ at col 1 → [1, 0, 1, 1, 0] (col 1 splits to 0,2; col 3 straight)
Total: 1+1+1 = 3 timelines
```

**Difference from BeamSplitCounter:**
- BeamSplitCounter: Tracks **where beams are** (Set of columns)
- QuantumTimelineEstimator: Tracks **how many paths** (array of counts)

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**

**TachyonManifold** - Only manages grid structure and access
- Does NOT: Simulate beams, count splits, or track timelines

**BeamSplitCounter** - Only counts split events
- Does NOT: Track timeline multiplicities or manage grid

**QuantumTimelineEstimator** - Only calculates path counts
- Does NOT: Count events or manage grid structure

---

### **O - Open/Closed Principle**

**Adding new analyzer:**
```java
// Count final beam positions
public class FinalBeamPositionCounter implements ManifoldAnalyzer {
    @Override
    public long analyze(List<String> grid) {
        TachyonManifold manifold = new TachyonManifold(grid);
        Set<Integer> beams = new HashSet<>();
        beams.add(manifold.getStartColumn());
        
        for (int r = 0; r < manifold.getHeight(); r++) {
            beams = propagateBeams(manifold, r, beams);
        }
        return beams.size();
    }
}

// Use without changing existing code
new ManifoldProcessor(new FinalBeamPositionCounter()).solve(grid);
```

---

### **L - Liskov Substitution Principle**

**Both analyzers are interchangeable:**
```java
ManifoldProcessor p1 = new ManifoldProcessor(new BeamSplitCounter());
ManifoldProcessor p2 = new ManifoldProcessor(new QuantumTimelineEstimator());

// Both work correctly with same interface
long result1 = p1.solve(grid);  // Split count
long result2 = p2.solve(grid);  // Timeline count
```

Different metrics, same contract: analyze grid → return long.

---

### **I - Interface Segregation Principle**

**ManifoldAnalyzer is minimal:**
```java
public interface ManifoldAnalyzer {
    long analyze(List<String> grid);
}
```

One method, no unnecessary dependencies.

---

### **D - Dependency Inversion Principle**

**Processor depends on abstraction:**
```java
public class ManifoldProcessor {
    private final ManifoldAnalyzer analyzer;  // Interface
    
    public ManifoldProcessor(ManifoldAnalyzer analyzer) {
        this.analyzer = analyzer;
    }
}
```

High-level processor doesn't depend on specific analyzers.

---

## Additional Design Patterns

### **Strategy Pattern (Core)**
- **Context:** ManifoldProcessor
- **Strategy:** ManifoldAnalyzer
- **Concrete Strategies:** BeamSplitCounter, QuantumTimelineEstimator

### **Domain Model with Validation**
TachyonManifold validates structure on construction:
- Ensures grid is non-null/non-empty
- Verifies 'S' marker exists
- Fails fast with clear error messages

### **Boundary-Safe Access**
```java
public char getAt(int row, int col) {
    if (isOutOfBounds(row, col)) return ' ';
    return grid.get(row).charAt(col);
}
```
Eliminates boundary checks in simulation code—defensive programming.

---