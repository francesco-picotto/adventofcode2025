# Day 12: Christmas Tree Farm

## Introduction and Problem Overview

### The Problem
Fit various 3D present shapes into a shipping container without any overlaps.
The challenge involves:
- **Input**: Shape definitions (ASCII art) + region requests (dimensions + shape counts)
- **Goal**: Determine which regions can accommodate their required shapes
- **Output**: Count of successfully filled regions

Input format example:
```
L-Shape:
#..
#..
##.

5x3: 2 1 0  (5×3 region needs 2×shape[0], 1×shape[1], 0×shape[2])
```

Classic polyomino packing problem (like Tetris/Pentominoes) with backtracking solution.

### Core Data Structures

**Shape (Record)**
- Set<Point> representing filled cells
- Transformations: rotate, flip, normalize
- Variations: all possible orientations (up to 8)

**Point (Record)**
- Immutable 2D coordinate (x, y)
- Transformations relative to 3×3 grid

**RegionRequest (Record)**
- Dimensions (width, height)
- Shape counts (how many of each)

**Backtracking State**
- Set<Point> occupied cells
- Current shape index being placed

---

## Architecture and Data Flow

### Main.java - Entry Point
```
Input File → Day12Mapper → PuzzleData {shapes, regions}
                               ↓
    PackingProcessor (BacktrackingFittingStrategy) → Count
```

**Parallel processing:** Multiple regions solved concurrently.

---

### Day12Mapper.java - Two-Section Parsing

**Section Detection:**
```
Lines before first 'x' → Shape definitions
Lines with 'x' → Region requests
```

**Shape Parsing (ASCII Art):**
```
L-Shape:
#..
#..
##.

Parsed to: points {(0,0), (0,1), (0,2), (1,2)}
Then normalized to ensure top-left at origin
```

**Algorithm:**
```java
for each line:
  if line ends with ':' → new shape, finalize previous
  else if contains '#' → mark positions as points
  
After accumulating points: normalize() to (0,0) origin
```

**Region Parsing:**
```
"5x3: 2 1 0" 
  → split on ':' 
  → dims: [5, 3], counts: [2, 1, 0]
  → RegionRequest(5, 3, [2,1,0])
```

---

### PackingProcessor.java - Parallel Evaluation

**Algorithm:**
```
For each region (parallel):
  1. Flatten shape counts → actual list
  2. Get/cache variations for each shape
  3. Quick area check (total points ≤ w×h)
  4. Use strategy to check if fits
  5. Count successful regions
```

**Flattening:**
```
shapes: [A, B, C]
counts: [2, 0, 3]
result: [A, A, C, C, C]
```

**Variation Caching:**
```java
Map<Shape, Set<Shape>> variationsCache = new ConcurrentHashMap<>();

// Thread-safe caching for parallel processing
variationsList = required.stream()
    .map(s -> variationsCache.computeIfAbsent(s, Shape::getVariations))
    .toList();
```

**Why ConcurrentHashMap?** Multiple threads (parallel streams) may access cache simultaneously.

**Why cache variations?** Computing rotations/flips is expensive—cache once, reuse many times.

---

### FittingStrategy.java - Strategy Interface
```java
public interface FittingStrategy {
    boolean canFit(int width, int height, List<Set<Shape>> variations);
}
```

Minimal interface for different packing algorithms.

---

### BacktrackingFittingStrategy.java - Exhaustive Search

**Algorithm: Recursive Backtracking**
```
solve(width, height, occupied, variations, index):
  Base: if index == variations.size() → success!
  
  For each variation of current shape:
    For each position (x, y) in grid:
      If can place shape at (x, y):
        Place shape → mark occupied
        If solve(... index+1) → success!
        Backtrack → unmark occupied
  
  No valid placement → failure (trigger backtracking)
```

**Implementation:**
```java
private boolean solve(int w, int h, Set<Point> occupied, 
                      List<Set<Shape>> variations, int index) {
    if (index == variations.size()) return true;
    
    for (Shape variant : variations.get(index)) {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (canPlace(variant, x, y, w, h, occupied)) {
                    Set<Point> placed = place(variant, x, y);
                    occupied.addAll(placed);
                    
                    if (solve(w, h, occupied, variations, index + 1)) {
                        return true;  // Solution found!
                    }
                    
                    occupied.removeAll(placed);  // Backtrack
                }
            }
        }
    }
    return false;
}
```

**Placement Validation:**
```java
private boolean canPlace(Shape s, int dx, int dy, int w, int h, 
                         Set<Point> occupied) {
    for (Point p : s.points()) {
        int tx = p.x() + dx;
        int ty = p.y() + dy;
        
        // Check bounds
        if (tx >= w || ty >= h) return false;
        
        // Check collision
        if (occupied.contains(new Point(tx, ty))) return false;
    }
    return true;
}
```

**Complexity:** O(v^n × w × h) where:
- v = variations per shape
- n = number of shapes
- w, h = grid dimensions

**Exponential but complete:** Guaranteed to find solution if exists.

---

### Domain Models

**Point.java - 2D Coordinate with 3×3 Transformations**
```java
public record Point(int x, int y) {
    public Point rotate() {
        return new Point(2 - y, x);  // 90° clockwise in 3×3 grid
    }
    
    public Point flip() {
        return new Point(2 - x, y);  // Horizontal flip in 3×3 grid
    }
    
    public Point translate(int dx, int dy) {
        return new Point(x + dx, y + dy);
    }
}
```

**Why 3×3 grid?** Standard coordinate system for polyomino transformations.

**Rotation formula:** (x, y) → (2-y, x) for 90° clockwise
**Flip formula:** (x, y) → (2-x, y) for horizontal flip

---

**Shape.java - Polyomino Representation**
```java
public record Shape(Set<Point> points) {
    public Shape normalize() {
        int minX = points.stream().mapToInt(Point::x).min().orElse(0);
        int minY = points.stream().mapToInt(Point::y).min().orElse(0);
        
        return new Shape(points.stream()
            .map(p -> p.translate(-minX, -minY))
            .collect(Collectors.toSet()));
    }
    
    public Set<Shape> getVariations() {
        Set<Shape> variants = new HashSet<>();
        Shape current = this;
        
        for (int i = 0; i < 4; i++) {
            current = current.rotate().normalize();
            variants.add(current);
            variants.add(current.flip().normalize());
        }
        return variants;
    }
}
```

**Normalization:** Shifts shape so top-left is at (0,0)—essential for consistent placement.

**Variations:** 4 rotations × 2 flip states = up to 8 orientations (symmetric shapes have fewer).

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**

**Shape** - Only manages geometric representation and transformations
- Does NOT: Parse input, solve puzzles, or manage caching

**BacktrackingFittingStrategy** - Only implements backtracking algorithm
- Does NOT: Parse shapes, cache variations, or process regions

**PackingProcessor** - Only orchestrates processing and caching
- Does NOT: Implement placement logic or parse input

**Day12Mapper** - Only parses input format
- Does NOT: Solve puzzles or compute variations

---

### **O - Open/Closed Principle**

**Adding new strategy:**
```java
// Greedy heuristic: place largest shapes first
public class GreedyFittingStrategy implements FittingStrategy {
    @Override
    public boolean canFit(int width, int height, List<Set<Shape>> variations) {
        // Sort shapes by size descending
        List<Set<Shape>> sorted = variations.stream()
            .sorted((a, b) -> Integer.compare(
                b.iterator().next().points().size(),
                a.iterator().next().points().size()
            ))
            .toList();
        
        // Try greedy placement
        return attemptGreedyPlacement(width, height, sorted);
    }
}

// Use without changing existing code
new PackingProcessor(new GreedyFittingStrategy()).solve(data);
```

---

### **L - Liskov Substitution Principle**

**Strategy substitution:**
```java
PackingProcessor p1 = new PackingProcessor(new BacktrackingFittingStrategy());
PackingProcessor p2 = new PackingProcessor(new GreedyFittingStrategy());

// Both work correctly with same interface
long result1 = p1.solve(data);  // Exhaustive search
long result2 = p2.solve(data);  // Heuristic search
```

Different algorithms, same contract: determine if shapes fit → return boolean.

---

### **I - Interface Segregation Principle**

**FittingStrategy is minimal:**
```java
public interface FittingStrategy {
    boolean canFit(int width, int height, List<Set<Shape>> variations);
}
```

One method, focused interface.

---

### **D - Dependency Inversion Principle**

**Processor depends on abstraction:**
```java
public class PackingProcessor {
    private final FittingStrategy strategy;  // Interface
    
    public PackingProcessor(FittingStrategy strategy) {
        this.strategy = strategy;
    }
}
```

High-level processor doesn't depend on specific strategies.

---
