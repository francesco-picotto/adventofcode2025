# Day 11: Reactor

## Introduction and Problem Overview

### The Problem
Navigate a graph of interconnected components (SVR, FFT, DAC) to find a path to the output.
### Generalization of the problem
This solution counts paths through a directed graph representing a reactor network. The challenge involves:
- **Input**: Adjacency list defining node connections
- **Goal**: Count distinct paths between nodes
- **Output**: Total path count with different constraints

Input format example:
```
you: svr fft
svr: dac out
fft: dac
dac: out
```

Two path-counting approaches:
1. **Part One**: Count all paths from start to exit (unconstrained)
2. **Part Two**: Count paths through mandatory nodes in specific order (constrained)

### Core Data Structures

**ReactorMap (Domain Model)**
- Directed graph: Map<String, Set<String>>
- Node name → set of target nodes
- Provides path counting with memoization

**Memoization Map**
- Key: node name
- Value: path count from that node to destination
- Prevents redundant DFS traversals

---

## Architecture and Data Flow

### Main.java - Entry Point
```
Input Adjacency List → Day11Mapper → ReactorMap
                                        ↓
         ReactorProcessor (SimplePathSolver) → Path count 1
                                        ↓
         ReactorProcessor (MandatoryNodeSolver) → Path count 2
```

**Key Design:** Same graph, different constraints—one finds all paths, other enforces mandatory nodes.

---

### Day11Mapper.java - Graph Construction
```
"you: svr fft" → parse → Map entry: "you" → {"svr", "fft"}
"svr: dac out" → parse → Map entry: "svr" → {"dac", "out"}
    ↓
ReactorMap(connections)
```

**Parsing Algorithm:**
```java
@Override
public ReactorMap map(List<String> lines) {
    Map<String, Set<String>> connections =
        lines.stream()
            .map(String::trim)
            .filter(line -> !line.isEmpty() && !line.startsWith("["))
            .map(line -> line.split(":\\s*"))
            .filter(parts -> parts.length >= 2)
            .collect(Collectors.toMap(
                parts -> parts[0].trim(),                      // Node name
                parts -> Arrays.stream(parts[1].split("\\s+")) // Targets
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toSet())
            ));
    
    return new ReactorMap(connections);
}
```

**Flexible format:** Handles various whitespace and filters comments (lines starting with `[`).

---

### ReactorProcessor.java - Service Layer
```
ReactorMap → solver.solve(map) → Result
```

Simple delegation to solver strategy.

---

### ReactorSolver.java - Strategy Interface
```java
public interface ReactorSolver {
    long solve(ReactorMap map);
}
```

Minimal interface for different path-counting strategies.

---

### ReactorMap.java - Domain Model with Graph Operations

**Graph Representation:**
```java
public record ReactorMap(Map<String, Set<String>> connections) {
    public Set<String> getNeighbours(String node) {
        return connections.getOrDefault(node, Collections.emptySet());
    }
}
```

**Path Counting with Memoization:**
```java
public long countPaths(String start, String end, Map<String, Long> memo) {
    // Base case: reached destination
    if (start.equals(end)) return 1L;
    
    // Return cached result
    if (memo.containsKey(start)) return memo.get(start);
    
    // Sum paths from all neighbors
    long paths = getNeighbours(start).stream()
        .mapToLong(n -> countPaths(n, end, memo))
        .sum();
    
    // Cache result
    memo.put(start, paths);
    return paths;
}
```

**Algorithm: Recursive DFS with Memoization**
- Base case: start == end → 1 path (reached destination)
- Recursive case: sum of paths from all neighbors
- Memoization: cache results to avoid recomputing same subpaths

**Example:**
```
Graph: A→B, A→C, B→D, C→D
countPaths(A, D):
  = countPaths(B, D) + countPaths(C, D)
  = (countPaths(D, D)) + (countPaths(D, D))
  = 1 + 1 = 2 paths
```

**Complexity:** O(V + E) with memoization (each node computed once).

---

### SimplePathSolver.java - Unconstrained Path Counting
```
Start: "you", End: "out" → Count all paths
```

**Implementation:**
```java
@Override
public long solve(ReactorMap map) {
    memo.clear();
    return map.countPaths("you", "out", new HashMap<>());
}
```

**Simple delegation** to ReactorMap's path counting.

**Use case:** "How many different routes through the reactor?"

---

### MandatoryNodeSolver.java - Constrained Path Counting

**Algorithm: Evaluate Multiple Routing Chains**
```
Chain A: svr → fft → dac → out
Chain B: svr → dac → fft → out

For each chain:
  Path count = segment1 × segment2 × segment3
  
Return: max(chain A, chain B)
```

**Implementation:**
```java
@Override
public long solve(ReactorMap map) {
    // Evaluate two possible orderings of mandatory nodes
    long pathA = calculateChain(map, "svr", "fft", "dac", "out");
    long pathB = calculateChain(map, "svr", "dac", "fft", "out");
    
    return Math.max(pathA, pathB);
}

private long calculateChain(ReactorMap map, String... nodes) {
    long total = 1L;
    
    // Multiply path counts between consecutive pairs
    for (int i = 0; i < nodes.length - 1; i++) {
        long segment = map.countPaths(nodes[i], nodes[i + 1], new HashMap<>());
        
        if (segment == 0) return 0;  // Chain breaks if any segment unreachable
        
        total *= segment;
    }
    return total;
}
```

**Multiplicative Path Counting:**

**Example:**
```
Chain: A → B → C → D
- A to B: 2 paths
- B to C: 3 paths  
- C to D: 2 paths
Total: 2 × 3 × 2 = 12 paths
```

**Why multiply?** Each path segment can be combined with each subsequent segment, creating all possible combinations through the mandatory sequence.

**Two chains evaluated:** Finds best ordering of mandatory nodes (fft before/after dac).

**Use case:** "Maximum routes through required checkpoints?"

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**

**ReactorMap** - Only manages graph structure and path counting
- Does NOT: Parse input, evaluate chains, or choose strategies

**SimplePathSolver** - Only counts unconstrained paths
- Does NOT: Evaluate mandatory nodes or parse input

**MandatoryNodeSolver** - Only evaluates constrained routing chains
- Does NOT: Implement basic path counting or parse input

**Day11Mapper** - Only parses adjacency list format
- Does NOT: Count paths or analyze graph properties

---

### **O - Open/Closed Principle**

**Adding new solver:**
```java
// Find shortest path using BFS
public class ShortestPathSolver implements ReactorSolver {
    @Override
    public long solve(ReactorMap map) {
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distances = new HashMap<>();
        queue.add("you");
        distances.put("you", 0);
        
        while (!queue.isEmpty()) {
            String node = queue.poll();
            if (node.equals("out")) return distances.get(node);
            
            for (String neighbor : map.getNeighbours(node)) {
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, distances.get(node) + 1);
                    queue.add(neighbor);
                }
            }
        }
        return -1;
    }
}

// Use without changing existing code
new ReactorProcessor(new ShortestPathSolver()).solve(map);
```

---

### **L - Liskov Substitution Principle**

**Both solvers are interchangeable:**
```java
ReactorProcessor p1 = new ReactorProcessor(new SimplePathSolver());
ReactorProcessor p2 = new ReactorProcessor(new MandatoryNodeSolver());

// Both work correctly with same interface
long result1 = p1.solve(map);  // Unconstrained paths
long result2 = p2.solve(map);  // Constrained paths
```

Different constraints, same contract: analyze graph → return count.

---

### **I - Interface Segregation Principle**

**ReactorSolver is minimal:**
```java
public interface ReactorSolver {
    long solve(ReactorMap map);
}
```

One method, focused interface.

---

### **D - Dependency Inversion Principle**

**Processor depends on abstraction:**
```java
public class ReactorProcessor {
    private final ReactorSolver solver;  // Interface
    
    public ReactorProcessor(ReactorSolver solver) {
        this.solver = solver;
    }
}
```

High-level processor doesn't depend on specific solvers.

---
