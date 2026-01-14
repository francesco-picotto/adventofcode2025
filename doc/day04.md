# Day 04: Printing Department

## Introduction and Problem Overview

### The Problem
Navigate a warehouse grid to identify which reams of paper can be safely moved based on their neighbors
### Generalization of the problem
This solution tackles a grid cell removal puzzle where we need to remove cells from a 2D character grid based on their neighbor count. The challenge involves:
- **Input**: A 2D character grid containing '@' and other characters
- **Goal**: Remove '@' cells that don't have sufficient '@' neighbors
- **Output**: Count of removed cells

The challenge has two parts:
1. **Part One**: Single-pass removal - identify and remove all qualifying cells in one operation
2. **Part Two**: Iterative removal - repeatedly remove cells until the grid stabilizes (cascade effect)

### Core Data Structures

**2D Character Array (char[][])**
- Represents the grid as a matrix of characters
- Direct array access for performance
- Mutable structure allowing in-place modifications

**Grid (Domain Model)**
- Wrapper around char[][] providing safe access and utilities
- Encapsulates boundary checking logic
- Provides neighbor counting functionality
- Acts as a facade simplifying grid operations

**Boolean Matrix (boolean[][])**
- Tracks which cells should be removed
- Separates identification phase from application phase
- Prevents cascade effects during single-pass removal

**RemovalRule (Strategy Interface)**
- Defines different removal strategies
- Returns count of removed cells
- Modifies grid in place by marking removed cells with 'X'

---

## Architecture and Data Flow

The solution follows a clean, layered architecture with domain logic encapsulated in a Grid class and removal strategies implementing the Strategy Pattern. Data flows from input → grid creation → rule application → result.

### Main.java - Entry Point
```
Input File → InputProvider → Day04Mapper
                                ↓
                    char[][] grid (2D array)
                                ↓
            GridUtils.copy() → Independent grid copies
                                ↓
         GridProcessor (BasicRemovalRule) → Part 1 Count
                                ↓
         GridProcessor (AdvancedRemovalRule) → Part 2 Count
```

**Responsibilities:**
- Reads input file and converts to 2D character grid
- Creates independent copies of the grid for each rule (prevents interference)
- Creates two `GridProcessor` instances with different removal strategies
- Outputs removal counts for both parts

**Key Design Choice:**
- Uses `GridUtils.copy()` to ensure both rules work with pristine copies of the original grid
- Demonstrates composition in `AdvancedRemovalRule` by passing `BasicRemovalRule` as parameter
- Strategy Pattern enables different removal behaviors without code duplication

---

### Day04Mapper.java - Grid Construction
```
Raw Lines ["ABC", "DEF", "GHI"]
              ↓
    Convert each line to char[]
              ↓
    Array of char arrays: [['A','B','C'], ['D','E','F'], ['G','H','I']]
              ↓
    Return char[][] grid
```

**Responsibilities:**
- Implements `InputMapper<char[][]>` interface
- Transforms list of strings into 2D character array
- Provides clean separation between input parsing and business logic

**Processing Pipeline:**
```java
lines.stream()
    .map(String::toCharArray)  // Convert each string to char[]
    .toArray(char[][]::new)    // Collect into 2D array
```

**Design Pattern:** Mapper/Transformer pattern isolating input format concerns from domain logic.

---

### GridProcessor.java - Service Layer
```
char[][] grid + RemovalRule
              ↓
        rule.apply(grid)
              ↓
    Returns count of removed cells
```

**Responsibilities:**
- Orchestrates grid processing
- Delegates all removal logic to injected `RemovalRule`
- Acts as a thin wrapper providing consistent interface

**Data Flow:**
1. Receives removal rule via constructor (dependency injection)
2. Receives grid to process via `solve()` method
3. Delegates to rule's `apply()` method
4. Returns count of removed cells

**Design Note:** This is an extremely simple service layer—its primary value is providing a consistent processing interface and demonstrating dependency injection. The real logic lives in the rules and domain model.

---

### Grid.java - Domain Model
```
char[][] cells (raw data)
              ↓
    Grid wrapper with utilities
              ↓
Methods: get(), set(), is(), countNeighbors()
              ↓
    Safe access + boundary checking
```

**Responsibilities:**
- Wraps 2D character array with domain-appropriate interface
- Provides boundary-safe access methods
- Encapsulates neighbor counting logic
- Simplifies grid operations for rule implementations

**Key Methods:**

**`get(int r, int c)`** - Safe Cell Access
```java
public char get(int r, int c) {
    if (!isValid(r, c)) return ' ';
    return cells[r][c];
}
```
- Returns space character for out-of-bounds access
- Eliminates need for boundary checks in calling code
- Defensive programming pattern

**`set(int r, int c, char value)`** - Safe Cell Modification
```java
public void set(int r, int c, char value) {
    if (isValid(r, c)) cells[r][c] = value;
}
```
- Silently ignores invalid positions
- Prevents ArrayIndexOutOfBoundsException
- Allows caller to write simpler code

**`is(int r, int c, char value)`** - Cell Comparison
```java
public boolean is(int r, int c, char value) {
    return get(r, c) == value;
}
```
- Convenient shorthand for checking cell contents
- Uses safe `get()` internally
- Makes rule code more readable: `grid.is(r, c, '@')` vs `grid.get(r, c) == '@'`

**`countNeighbors(int row, int col, char target)`** - Core Algorithm
```java
public int countNeighbors(int row, int col, char target) {
    int count = 0;
    for (int i = row - 1; i <= row + 1; i++) {
        for (int j = col - 1; j <= col + 1; j++) {
            if (i == row && j == col) continue;  // Skip center cell
            if (is(i, j, target)) count++;
        }
    }
    return count;
}
```

**Algorithm Details:**
- Examines 3x3 area centered on specified cell
- Excludes center cell itself (8 neighbors total)
- Uses `is()` method which handles boundaries safely
- Out-of-bounds positions return false (treated as not matching)

**The 8 Neighbors:**
```
[i-1,j-1] [i-1,j] [i-1,j+1]
[i,j-1]   [i,j]   [i,j+1]     (center cell excluded)
[i+1,j-1] [i+1,j] [i+1,j+1]
```

**Design Benefits:**
- Encapsulates boundary checking complexity
- Makes rule implementations clean and readable
- Provides domain-appropriate abstractions
- Acts as a facade over raw 2D array

---

### RemovalRule.java - Strategy Interface
```java
public interface RemovalRule {
    int apply(char[][] grid);
}
```

**Responsibilities:**
- Defines contract for cell removal strategies
- Specifies that rules modify grid in place
- Returns count of removed cells for reporting

**Design Characteristics:**
- Minimal interface with single method
- Clear contract: analyze grid, remove cells, return count
- Enables polymorphic behavior in grid processing

**Contract Details:**
- **Input**: 2D character array (mutable)
- **Side Effect**: Modifies grid by replacing removed cells with 'X'
- **Output**: Count of removed cells
- **Idempotency**: Not required—some rules may be iterative

---

### BasicRemovalRule.java - Single-Pass Removal
```
Grid State Snapshot
        ↓
For each cell:
  if '@' and neighbors < 4:
    mark for removal in boolean[][]
        ↓
Apply all removals simultaneously
        ↓
Return count
```

**Responsibilities:**
- Implements single-pass removal strategy
- Separates identification from application (two-phase approach)
- Ensures removal decisions based on original grid state

**Algorithm - Two-Phase Approach:**

**Phase 1: Identification**
```java
boolean[][] toRemove = new boolean[grid.rows()][grid.cols()];
int count = 0;

for (int i = 0; i < grid.rows(); i++) {
    for (int j = 0; j < grid.cols(); j++) {
        if (shouldRemove(grid, i, j)) {
            toRemove[i][j] = true;
            count++;
        }
    }
}
```
- Scans entire grid without modifying it
- Records which cells should be removed in boolean matrix
- Counts total removals

**Phase 2: Application**
```java
private void applyRemovals(Grid grid, boolean[][] toRemove) {
    for(int i = 0; i < grid.rows(); i++) {
        for(int j = 0; j < grid.cols(); j++) {
            if(toRemove[i][j]) grid.set(i, j, 'X');
        }
    }
}
```
- Applies all marked removals simultaneously
- Replaces removed cells with 'X'
- Modifications happen after all decisions are made

**`shouldRemove()` - Removal Criteria**
```java
protected boolean shouldRemove(Grid grid, int r, int c) {
    return grid.is(r, c, '@') && grid.countNeighbors(r, c, '@') < 4;
}
```

**Removal Conditions:**
1. Cell must contain '@' character
2. Cell must have fewer than 4 '@' neighbors (out of maximum 8)

**Why Protected?** Allows subclasses to override removal criteria if needed (template method pattern hint).

**Why Two Phases?**
- Prevents cascade effects within single pass
- All removal decisions based on original grid state
- Cell at (5,5) removal doesn't affect decision for cell (6,6)
- Deterministic and predictable behavior

**Example:**
```
Original Grid:
@ @ @
@ @ @
@ @ @

After Phase 1 (Identification):
- Center cell (1,1) has 8 neighbors → NOT removed
- Edge cells have 5 neighbors → NOT removed
- Corner cells have 3 neighbors → REMOVED

After Phase 2 (Application):
X @ X
@ @ @
X @ X
```

---

### AdvancedRemovalRule.java - Iterative Removal
```
Initial Grid State
        ↓
Apply BasicRemovalRule → count1 removed
        ↓
Apply BasicRemovalRule → count2 removed
        ↓
Apply BasicRemovalRule → 0 removed (stable!)
        ↓
Return total count (count1 + count2 + ...)
```

**Responsibilities:**
- Implements iterative removal strategy with cascade effects
- Applies basic rule repeatedly until stabilization
- Uses composition to delegate actual removal logic

**Algorithm:**
```java
@Override
public int apply(char[][] grid) {
    int count = 0;
    int currentBatch;
    
    while((currentBatch = basicRule.apply(grid)) > 0) {
        count += currentBatch;
    }
    return count;
}
```

**How It Works:**
1. Apply basic rule to current grid state → get count of removed cells
2. If count > 0, cells were removed; grid state has changed
3. Repeat step 1 with new grid state
4. Continue until basic rule returns 0 (no more removals possible)
5. Return total accumulated count across all iterations

**Cascade Effect Example:**
```
Iteration 0 (Initial):
@ @ @ @ @
@ @ @ @ @
@ @ @ @ @
@ @ @ @ @
@ @ @ @ @

Iteration 1 (remove corners, 4 neighbors < 4):
X @ @ @ X
@ @ @ @ @
@ @ @ @ @
@ @ @ @ @
X @ @ @ X

Iteration 2 (cells near removed corners now have < 4 neighbors):
X X @ X X
X @ @ @ X
@ @ @ @ @
X @ @ @ X
X X @ X X

Iteration 3 (more cells become vulnerable):
X X X X X
X X @ X X
X @ @ @ X
X X @ X X
X X X X X

... continues until stable
```

**Key Properties:**
- **Composition over inheritance**: Uses `RemovalRule` instance rather than extending `BasicRemovalRule`
- **Flexibility**: Works with any `RemovalRule` implementation, not just `BasicRemovalRule`
- **Accumulation**: Tracks total removed across all iterations
- **Stabilization**: Automatically stops when no more changes occur

**Constructor Design:**
```java
public AdvancedRemovalRule(RemovalRule basicRule) {
    this.basicRule = basicRule;
}
```
- Accepts any `RemovalRule` (not just BasicRemovalRule)
- Could use different rules for different iteration strategies
- Demonstrates Dependency Inversion Principle

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**
*Each class has one clear reason to change.*

**Example: Grid.java**
- **Only responsible for**: Grid data access and neighbor counting
- **Does NOT**: Decide removal criteria, orchestrate processing, or read input files
- **Why it matters**: If we change removal rules, we don't touch Grid. If we change access patterns, we don't touch removal logic.

**Example: BasicRemovalRule.java**
- **Only responsible for**: Implementing single-pass removal algorithm
- **Does NOT**: Iterate until stable, read files, or manage grid structure
- **Why it matters**: Changing iteration strategy doesn't affect single-pass logic. Each rule focuses on one algorithm.

**Example: Day04Mapper.java**
- **Only responsible for**: Converting text lines to 2D char array
- **Does NOT**: Process grids, apply rules, or know about '@' characters
- **Why it matters**: Input format changes are isolated here. Business logic elsewhere doesn't need to know about file formats.

**Example: GridProcessor.java**
- **Only responsible for**: Orchestrating rule application
- **Does NOT**: Implement removal logic or manipulate grid structure
- **Why it matters**: Provides clean separation between coordination and execution.

---

### **O - Open/Closed Principle**
*Open for extension, closed for modification.*

**Example: Adding New Removal Rules**
```java
// Want to remove cells with exactly 3 neighbors? Just add a new rule!
public class ExactNeighborRule implements RemovalRule {
    private final int requiredNeighbors;
    
    public ExactNeighborRule(int required) {
        this.requiredNeighbors = required;
    }
    
    @Override
    public int apply(char[][] cells) {
        Grid grid = new Grid(cells);
        boolean[][] toRemove = new boolean[grid.rows()][grid.cols()];
        int count = 0;
        
        for (int i = 0; i < grid.rows(); i++) {
            for (int j = 0; j < grid.cols(); j++) {
                if (grid.is(i, j, '@') && 
                    grid.countNeighbors(i, j, '@') == requiredNeighbors) {
                    toRemove[i][j] = true;
                    count++;
                }
            }
        }
        // Apply removals...
        return count;
    }
}

// Want to remove based on diagonal neighbors only?
public class DiagonalRemovalRule implements RemovalRule {
    @Override
    public int apply(char[][] cells) {
        // Custom logic counting only diagonal neighbors
        // ...
    }
}

// Use any rule without changing existing code
new GridProcessor(new ExactNeighborRule(3)).solve(grid);
new GridProcessor(new DiagonalRemovalRule()).solve(grid);
```

**Why it matters:**
- We solved Part Two by adding `AdvancedRemovalRule` without modifying `GridProcessor`, `Grid`, or `BasicRemovalRule`
- The system is **open** to new removal strategies but **closed** to modification of core processing
- Future requirements (new removal patterns) require zero changes to existing code

---

### **L - Liskov Substitution Principle**
*Subtypes must be substitutable for their base types.*

**Example: Rule Interchangeability**
```java
// Both rules work identically from the processor's perspective
GridProcessor processor1 = new GridProcessor(new BasicRemovalRule());
GridProcessor processor2 = new GridProcessor(new AdvancedRemovalRule(new BasicRemovalRule()));

// Both produce correct results using the same interface contract
int result1 = processor1.solve(grid1);  // Single-pass removal count
int result2 = processor2.solve(grid2);  // Iterative removal count
```

**Contract Guarantee:**
Every `RemovalRule` implementation must:
- Accept a 2D char array
- Analyze and modify the grid according to its criteria
- Return an integer count of removed cells
- Mark removed cells with 'X'

Both `BasicRemovalRule` and `AdvancedRemovalRule` uphold this contract perfectly.

**Example: Composition in AdvancedRemovalRule**
```java
public class AdvancedRemovalRule implements RemovalRule {
    private final RemovalRule basicRule;  // Can be ANY RemovalRule!
    
    public AdvancedRemovalRule(RemovalRule basicRule) {
        this.basicRule = basicRule;
    }
    
    @Override
    public int apply(char[][] grid) {
        int count = 0;
        int currentBatch;
        
        while((currentBatch = basicRule.apply(grid)) > 0) {
            count += currentBatch;
        }
        return count;
    }
}
```

`AdvancedRemovalRule` works correctly with:
- `BasicRemovalRule` (intended use)
- `ExactNeighborRule` (custom rule)
- `DiagonalRemovalRule` (custom rule)
- **ANY** implementation of `RemovalRule`

This is Liskov Substitution in action—subtypes are fully substitutable.

---

### **I - Interface Segregation Principle**
*Clients shouldn't depend on interfaces they don't use.*

**Example: RemovalRule Interface**
```java
public interface RemovalRule {
    int apply(char[][] grid);  // ONLY what's needed
}
```

**Why it's excellent design:**
- Minimal interface with exactly one method
- No forced dependencies on logging, configuration, or metadata
- No unnecessary methods that implementers must stub
- Focused on single concern: applying removal logic

**Contrast with a BAD design:**
```java
// BAD: Bloated interface
public interface RemovalRule {
    int apply(char[][] grid);
    boolean validate(char[][] grid);          // Not all rules need this
    String getDescription();                  // Metadata forcing
    RuleStatistics getStatistics();           // Tracking not always needed
    void reset();                             // State management forced
    List<Position> getRemovedPositions();     // Detailed tracking forced
    void setConfiguration(Map<String, ?> config);  // Configuration complexity
}
```

Our interface is **lean and focused**—only what every implementation truly needs.

---

### **D - Dependency Inversion Principle**
*Depend on abstractions, not concretions.*

**Example: GridProcessor Constructor**
```java
public class GridProcessor {
    private final RemovalRule rule;  // Depends on INTERFACE
    
    public GridProcessor(RemovalRule rule) {
        this.rule = rule;  // Accepts any implementation
    }
    
    public int solve(char[][] grid) {
        return rule.apply(grid);  // Polymorphic call
    }
}
```

**Why it matters:**
- `GridProcessor` (high-level) doesn't depend on `BasicRemovalRule` or `AdvancedRemovalRule` (low-level)
- Both depend on `RemovalRule` abstraction
- Easy testing with mock rules
- Runtime flexibility in rule selection

**Example: AdvancedRemovalRule Constructor**
```java
public class AdvancedRemovalRule implements RemovalRule {
    private final RemovalRule basicRule;  // Depends on abstraction!
    
    public AdvancedRemovalRule(RemovalRule basicRule) {
        this.basicRule = basicRule;
    }
}
```