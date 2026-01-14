# Day 08: Circuit Component Connectivity Analysis

## Introduction and Problem Overview

### The Problem
This solution analyzes connectivity in a 3D circuit network of junction boxes. The challenge involves:
- **Input**: 3D coordinates (x,y,z) of junction boxes
- **Goal**: Analyze component formation as connections are added
- **Output**: Numerical metrics based on component structure

Two analysis approaches:
1. **Part One**: Use first 1000 connections → calculate product of three largest component sizes
2. **Part Two**: Find critical connection that unifies entire circuit → return metric based on that connection

### Core Data Structures

**UnionFind (Disjoint Set Union)**
- Tracks connected components efficiently
- Path compression + union by size optimizations
- Near-constant time operations (α(n) amortized)
- Maintains component count and sizes

**Point3D / JunctionBox / Connection**
- Point3D: Immutable 3D coordinates with distance calculation
- JunctionBox: ID + position in 3D space
- Connection: Two boxes + distance, sorted by distance (Comparable)

**Day08Data (DTO)**
- Encapsulates boxes and pre-sorted connections
- Complete graph: all pairwise connections generated

---

## Architecture and Data Flow

### Main.java - Entry Point
```
Input Coords → Day08Mapper → Day08Data {boxes, connections}
                                ↓
         CircuitProcessor (BasicCircuitAnalyzer) → Product of sizes
                                ↓
         CircuitProcessor (AdvanceCircuitAnalyzer) → Critical connection
```

**Key Design:** Pre-compute all connections, sort by distance, then apply different analysis strategies.

---

### Day08Mapper.java - Complete Graph Construction
```
Parse coordinates → Create boxes
                       ↓
    Generate all pairwise connections
                       ↓
    Sort by distance (Kruskal preparation)
                       ↓
        Day08Data(boxes, connections)
```

**Key Responsibility:** Creates complete graph with n(n-1)/2 connections.

**Algorithm:**
```java
for (int i = 0; i < boxes.size(); i++) {
    for (int j = i + 1; j < boxes.size(); j++) {
        JunctionBox a = boxes.get(i);
        JunctionBox b = boxes.get(j);
        connections.add(new Connection(a, b, a.distanceTo(b)));
    }
}
Collections.sort(connections);  // Sort by distance
```

**Why pre-compute all connections?** Both analyzers need sorted connections—compute once, use multiple times.

---

### CircuitProcessor.java - Service Layer
```
Day08Data → analyzer.analyze(boxes, connections) → Result
```

Simple delegation to analyzer strategy.

---

### CircuitAnalyzer.java - Strategy Interface
```java
public interface CircuitAnalyzer {
    long analyze(List<JunctionBox> boxes, List<Connection> allConnections);
}
```

Minimal interface for different component analysis strategies.

---

### Domain Models

**Point3D.java - 3D Coordinate**
```java
public record Point3D(double x, double y, double z) {
    public double distance(Point3D other) {
        return Math.sqrt(
            Math.pow(x - other.x, 2) +
            Math.pow(y - other.y, 2) +
            Math.pow(z - other.z, 2)
        );
    }
}
```

Simple immutable 3D point with Euclidean distance.

---

**JunctionBox.java - Circuit Node**
```java
public record JunctionBox(int id, Point3D position) {
    public double distanceTo(JunctionBox other) {
        return this.position.distance(other.position);
    }
}
```

Combines ID (for Union-Find indexing) with 3D position.

---

**Connection.java - Weighted Edge**
```java
public record Connection(JunctionBox a, JunctionBox b, double distance) 
    implements Comparable<Connection> {
    
    @Override
    public int compareTo(Connection other) {
        return Double.compare(this.distance, other.distance);
    }
}
```

**Comparable implementation:** Enables sorting by distance for greedy algorithms.

---

### UnionFind.java - Component Tracker

**Core Operations:**

**Constructor:** Initialize n disjoint components
```java
public UnionFind(int n) {
    parent = new int[n];
    size = new int[n];
    components = n;
    for (int i = 0; i < n; i++) {
        parent[i] = i;  // Each is its own root
        size[i] = 1;
    }
}
```

**Find with Path Compression:**
```java
public int find(int p) {
    while (parent[p] != p) {
        parent[p] = parent[parent[p]];  // Compress path
        p = parent[p];
    }
    return p;
}
```

**Union by Size:**
```java
public void union(int p, int q) {
    int rootP = find(p);
    int rootQ = find(q);
    if (rootP == rootQ) return;
    
    // Attach smaller tree under larger
    if (size[rootP] < size[rootQ]) {
        parent[rootP] = rootQ;
        size[rootQ] += size[rootP];
    } else {
        parent[rootQ] = rootP;
        size[rootP] += size[rootQ];
    }
    components--;
}
```

**Key Features:**
- Path compression: Flattens trees for faster future finds
- Union by size: Keeps trees balanced
- Component counting: Tracks number of disjoint sets
- Size tracking: Maintains size of each component

---

### BasicCircuitAnalyzer.java - Limited Connections
```
Process first 1000 connections → Form components → Find 3 largest → Multiply
```

**Algorithm:**
```java
@Override
public long analyze(List<JunctionBox> boxes, List<Connection> allConnections) {
    UnionFind uf = new UnionFind(boxes.size());
    
    // Add first 1000 connections
    allConnections.stream()
        .limit(CONNECTION_LIMIT)
        .forEach(c -> uf.union(c.a().id(), c.b().id()));
    
    // Find 3 largest components, multiply their sizes
    return boxes.stream()
        .map(box -> uf.find(box.id()))       // Get component root
        .distinct()                          // Unique components
        .map(root -> uf.getSize(root))       // Get size
        .sorted(Comparator.reverseOrder())   // Descending
        .limit(3)                            // Top 3
        .mapToLong(Integer::longValue)
        .reduce(1, (a, b) -> a * b);         // Multiply
}
```

**Example:**
- 100 boxes, add first 1000 connections
- Forms 3 components: sizes [45, 35, 20]
- Result: 45 × 35 × 20 = 31,500

**Question answered:** "What's the fragmentation after limited connections?"

---

### AdvanceCircuitAnalyzer.java - Critical Connection
```
Add connections until components = 1 → Return metric of unifying connection
```

**Algorithm:**
```java
@Override
public long analyze(List<JunctionBox> boxes, List<Connection> allConnections) {
    UnionFind uf = new UnionFind(boxes.size());
    
    for (Connection c : allConnections) {
        int rootA = uf.find(c.a().id());
        int rootB = uf.find(c.b().id());
        
        // Only unite if different components
        if (rootA != rootB) {
            uf.union(rootA, rootB);
            
            // Check if this unified everything
            if (uf.getComponentsCount() == 1) {
                // Return product of x-coordinates
                return (long) c.a().position().x() * 
                       (long) c.b().position().x();
            }
        }
    }
    return 0;  // Couldn't fully connect
}
```

**Key Insight:** Find the exact connection that drops component count from 2→1.

**Example:**
- Start: 100 components (100 separate boxes)
- Add connections: 100 → 50 → 25 → 10 → 5 → 2
- Add connection between boxes A (x=7) and B (x=11): 2 → 1
- Result: 7 × 11 = 77

**Question answered:** "Which connection completes the circuit?"

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**

**UnionFind** - Only manages component connectivity
- Does NOT: Parse input, calculate distances, or apply analysis strategies

**Day08Mapper** - Only parses input and creates graph
- Does NOT: Analyze components or track connectivity

**BasicCircuitAnalyzer** - Only analyzes limited connection scenario
- Does NOT: Find critical connections or parse input

**AdvanceCircuitAnalyzer** - Only finds unifying connection
- Does NOT: Calculate component products or parse input

---

### **O - Open/Closed Principle**

**Adding new analyzer:**
```java
// Find minimum spanning tree total weight
public class MSTWeightCalculator implements CircuitAnalyzer {
    @Override
    public long analyze(List<JunctionBox> boxes, List<Connection> allConnections) {
        UnionFind uf = new UnionFind(boxes.size());
        double totalWeight = 0;
        
        for (Connection c : allConnections) {
            if (uf.find(c.a().id()) != uf.find(c.b().id())) {
                uf.union(c.a().id(), c.b().id());
                totalWeight += c.distance();
                if (uf.getComponentsCount() == 1) break;
            }
        }
        return (long) totalWeight;
    }
}

// Use without changing existing code
new CircuitProcessor(new MSTWeightCalculator()).solve(data);
```

---

### **L - Liskov Substitution Principle**

**Both analyzers are interchangeable:**
```java
CircuitProcessor p1 = new CircuitProcessor(new BasicCircuitAnalyzer());
CircuitProcessor p2 = new CircuitProcessor(new AdvanceCircuitAnalyzer());

// Both work correctly with same interface
long result1 = p1.solve(data);  // Component product
long result2 = p2.solve(data);  // Critical connection
```

Different questions, same contract: analyze circuit → return long.

---

### **I - Interface Segregation Principle**

**CircuitAnalyzer is minimal:**
```java
public interface CircuitAnalyzer {
    long analyze(List<JunctionBox> boxes, List<Connection> allConnections);
}
```

One method, focused interface.

---

### **D - Dependency Inversion Principle**

**Processor depends on abstraction:**
```java
public class CircuitProcessor {
    private final CircuitAnalyzer analyzer;  // Interface
    
    public CircuitProcessor(CircuitAnalyzer analyzer) {
        this.analyzer = analyzer;
    }
}
```

High-level processor doesn't depend on specific analyzers.

---

## Additional Design Patterns

### **Strategy Pattern (Core)**
- **Context:** CircuitProcessor
- **Strategy:** CircuitAnalyzer
- **Concrete Strategies:** BasicCircuitAnalyzer, AdvanceCircuitAnalyzer

### **Union-Find Data Structure**
Classic algorithm for dynamic connectivity:
- Path compression: O(α(n)) per operation
- Union by size: Keeps trees balanced
- Critical for efficient component tracking

### **Complete Graph Generation**
Pre-computation pattern:
- Generate all O(n²) connections once
- Sort by distance once
- Multiple analyzers reuse same data
- Trade memory for time

### **Records for Domain Models**
All domain models use records:
- Point3D, JunctionBox, Connection, Day08Data
- Immutability by default
- Auto-generated equals/hashCode
- Clean, concise syntax

### **Comparable for Natural Ordering**
Connection implements Comparable<Connection>:
- Enables natural sorting by distance
- Essential for greedy algorithms (Kruskal's MST)
- Clean integration with Collections.sort()

---
