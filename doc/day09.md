# Day 09: Movie Theater

## Introduction and Problem Overview

### The Problem
Calculate the area of complex shapes and determine if specific rectangles fall "out of bounds."
### Generalization of the problem
This solution finds rectangles in a 2D grid formed by tile coordinates. The challenge involves:
- **Input**: List of 2D coordinates (x,y) representing tiles
- **Goal**: Find maximum rectangle areas with different constraints
- **Output**: Maximum rectangle area

Two analysis approaches:
1. **Part One**: Find maximum rectangle from any tile pair (no constraints)
2. **Part Two**: Find maximum valid rectangle within polygon boundary (with validation)

### Core Data Structures

**Tile (Record)**
- Immutable 2D coordinate (x, y)
- Distance calculations between tiles

**Rectangle (Record)**
- Defined by xMin, xMax, yMin, yMax
- Area calculation and center point methods
- Factory method to create from two tiles

**GeometryUtils (Utility Class)**
- Ray casting algorithm for point-in-polygon
- Segment intersection checks
- Perimeter detection

---

## Architecture and Data Flow

### Main.java - Entry Point
```
Input Coords → Day09Mapper → List<Tile>
                                ↓
           GridProcessor (MaxRectangleAnalyzer) → Max area (any pair)
                                ↓
           GridProcessor (LoopRectangleAnalyzer) → Max area (valid)
```

**Key Design:** Same tile list, different constraints—one unconstrained, one validates polygon containment.

---

### Day09Mapper.java - Coordinate Parsing
```
"10,20" → parse → Tile(10, 20)
"30,40" → parse → Tile(30, 40)
    ↓
List<Tile>
```

Simple stream-based parsing:
```java
lines.stream()
    .map(String::trim)
    .filter(line -> !line.isEmpty())
    .map(line -> {
        String[] coords = line.split(",");
        return new Tile(
            Integer.parseInt(coords[0]),
            Integer.parseInt(coords[1])
        );
    })
    .collect(Collectors.toList());
```

---

### GridProcessor.java - Service Layer
```
List<Tile> → analyzer.analyze(tiles) → Maximum area
```

Simple delegation to analyzer strategy.

---

### GridAnalyzer.java - Strategy Interface
```java
public interface GridAnalyzer {
    long analyze(List<Tile> tiles);
}
```

Minimal interface for different rectangle analysis strategies.

---

### Domain Models

**Tile.java - 2D Coordinate**
```java
public record Tile(int x, int y) {
    public int distanceX(Tile other) {
        return Math.abs(this.x - other.x) + 1;  // Inclusive count
    }
    
    public int distanceY(Tile other) {
        return Math.abs(this.y - other.y) + 1;  // Inclusive count
    }
}
```

**Why +1 in distance?** Counts cells inclusively: distance from 0 to 5 is 6 cells (0,1,2,3,4,5).

---

**Rectangle.java - Bounding Box**
```java
public record Rectangle(int xMin, int xMax, int yMin, int yMax) {
    public static Rectangle from(Tile t1, Tile t2) {
        return new Rectangle(
            Math.min(t1.x(), t2.x()),
            Math.max(t1.x(), t2.x()),
            Math.min(t1.y(), t2.y()),
            Math.max(t1.y(), t2.y())
        );
    }
    
    public long area() {
        return (long) (xMax - xMin + 1) * (yMax - yMin + 1);
    }
    
    public double centerX() { return (xMin + xMax) / 2.0; }
    public double centerY() { return (yMin + yMax) / 2.0; }
}
```

**Factory Method:** Creates rectangle from any two tiles (treats as opposite corners).

**Area Calculation:** Inclusive: rectangle from (0,0) to (5,5) has area 6×6=36.

---

### GeometryUtils.java - Computational Geometry

**Point-in-Polygon: Ray Casting Algorithm**
```java
public static boolean isPointInside(double px, double py, List<Tile> polygon) {
    // First check if point is on perimeter
    for (int i = 0; i < polygon.size(); i++) {
        if (isPointOnSegment(px, py, polygon.get(i), 
                             polygon.get((i + 1) % polygon.size()))) {
            return true;
        }
    }
    
    // Ray casting: count edge crossings
    boolean inside = false;
    int n = polygon.size();
    for (int i = 0, j = n - 1; i < n; j = i++) {
        Tile pi = polygon.get(i);
        Tile pj = polygon.get(j);
        
        if (((pi.y() > py) != (pj.y() > py)) &&
            (px < (double)(pj.x() - pi.x()) * (py - pi.y()) / 
                  (pj.y() - pi.y()) + pi.x())) {
            inside = !inside;
        }
    }
    return inside;
}
```

**Algorithm:** Cast horizontal ray from point to infinity, count edge crossings:
- Odd crossings → inside
- Even crossings → outside

**Perimeter Check:** Points on polygon boundary count as "inside".

---

**Segment Intersection Check**
```java
private static boolean isPointOnSegment(double px, double py, Tile a, Tile b) {
    if (a.x() == b.x()) {
        // Vertical segment
        return Math.abs(px - a.x()) < 1e-9 &&
               py >= Math.min(a.y(), b.y()) &&
               py <= Math.max(a.y(), b.y());
    } else {
        // Horizontal segment
        return Math.abs(py - a.y()) < 1e-9 &&
               px >= Math.min(a.x(), b.x()) &&
               px <= Math.max(a.x(), b.x());
    }
}
```

**Floating-point tolerance:** Uses epsilon (1e-9) for comparison to handle rounding errors.

---

### MaxRectangleAnalyzer.java - Unconstrained Search
```
For all tile pairs (i, j):
    Create rectangle from tiles
    Track maximum area
```

**Algorithm:**
```java
@Override
public long analyze(List<Tile> tiles) {
    long maxArea = 0;
    
    for (int i = 0; i < tiles.size(); i++) {
        for (int j = i + 1; j < tiles.size(); j++) {
            Rectangle rect = Rectangle.from(tiles.get(i), tiles.get(j));
            maxArea = Math.max(maxArea, rect.area());
        }
    }
    return maxArea;
}
```

**Complexity:** O(n²) - examines all unique tile pairs.

**No validation** - simply finds largest bounding box between any two tiles.

---

### LoopRectangleAnalyzer.java - Constrained Search with Validation
```
For all tile pairs (i, j):
    Create rectangle from tiles
    Validate: center inside polygon + no edge intersections
    Track maximum valid area
```

**Algorithm:**
```java
@Override
public long analyze(List<Tile> tiles) {
    if (tiles == null || tiles.size() < 2) return 0L;
    
    return IntStream.range(0, tiles.size())
        .parallel()  // Parallel processing for performance
        .mapToLong(i -> calculateMaxForTile(i, tiles))
        .max()
        .orElse(0L);
}

private long calculateMaxForTile(int i, List<Tile> tiles) {
    long localMax = 0;
    Tile t1 = tiles.get(i);
    
    for (int j = i + 1; j < tiles.size(); j++) {
        Rectangle rect = Rectangle.from(t1, tiles.get(j));
        long area = rect.area();
        
        if (area > localMax && isValid(rect, tiles)) {
            localMax = area;
        }
    }
    return localMax;
}
```

**Parallel Processing:** Uses parallel streams to speed up O(n³) validation.

---

**Validation Logic:**
```java
private boolean isValid(Rectangle rect, List<Tile> polygon) {
    // 1. Center must be inside polygon
    if (!GeometryUtils.isPointInside(rect.centerX(), rect.centerY(), polygon)) {
        return false;
    }
    
    // 2. No polygon edge can intersect rectangle interior
    for (int i = 0; i < polygon.size(); i++) {
        if (isEdgeIntersectingRect(
                polygon.get(i), 
                polygon.get((i + 1) % polygon.size()), 
                rect)) {
            return false;
        }
    }
    return true;
}
```

**Two-step validation:**
1. **Center check** - Quick rejection if center outside
2. **Edge intersection** - Verify no polygon edges cut through rectangle

---

**Edge-Rectangle Intersection:**
```java
private boolean isEdgeIntersectingRect(Tile a, Tile b, Rectangle rect) {
    if (a.x() == b.x()) {
        // Vertical edge
        return a.x() > rect.xMin() && a.x() < rect.xMax() &&
               Math.max(a.y(), b.y()) > rect.yMin() &&
               Math.min(a.y(), b.y()) < rect.yMax();
    } else {
        // Horizontal edge
        return a.y() > rect.yMin() && a.y() < rect.yMax() &&
               Math.max(a.x(), b.x()) > rect.xMin() &&
               Math.min(a.x(), b.x()) < rect.xMax();
    }
}
```

**Interior intersection only:** Edge must pass **through** rectangle, not just touch boundary.

**Complexity:** O(n³) - for each pair (O(n²)), validate against all edges (O(n)).

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**

**GeometryUtils** - Only provides geometric calculations
- Does NOT: Analyze rectangles, track maximum areas, or parse input

**MaxRectangleAnalyzer** - Only finds maximum unconstrained rectangle
- Does NOT: Validate polygons or perform geometric checks

**LoopRectangleAnalyzer** - Only finds maximum valid rectangle with polygon constraints
- Does NOT: Parse coordinates or provide general geometry utilities

---

### **O - Open/Closed Principle**

**Adding new analyzer:**
```java
// Find maximum rectangle with minimum area threshold
public class MinAreaRectangleAnalyzer implements GridAnalyzer {
    private static final long MIN_AREA = 100;
    
    @Override
    public long analyze(List<Tile> tiles) {
        return tiles.stream()
            .flatMap(t1 -> tiles.stream()
                .map(t2 -> Rectangle.from(t1, t2)))
            .filter(rect -> rect.area() >= MIN_AREA)
            .mapToLong(Rectangle::area)
            .max()
            .orElse(0L);
    }
}

// Use without changing existing code
new GridProcessor(new MinAreaRectangleAnalyzer()).solve(tiles);
```

---

### **L - Liskov Substitution Principle**

**Both analyzers are interchangeable:**
```java
GridProcessor p1 = new GridProcessor(new MaxRectangleAnalyzer());
GridProcessor p2 = new GridProcessor(new LoopRectangleAnalyzer());

// Both work correctly with same interface
long result1 = p1.solve(tiles);  // Maximum area (any)
long result2 = p2.solve(tiles);  // Maximum area (valid)
```

Different constraints, same contract: analyze tiles → return maximum area.

---

### **I - Interface Segregation Principle**

**GridAnalyzer is minimal:**
```java
public interface GridAnalyzer {
    long analyze(List<Tile> tiles);
}
```

One method, focused interface.

---

### **D - Dependency Inversion Principle**

**Processor depends on abstraction:**
```java
public class GridProcessor {
    private final GridAnalyzer analyzer;  // Interface
    
    public GridProcessor(GridAnalyzer analyzer) {
        this.analyzer = analyzer;
    }
}
```

High-level processor doesn't depend on specific analyzers.

---
