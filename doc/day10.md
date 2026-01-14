# Day 10: Factory

## Introduction and Problem Overview

### The Problem
This solution solves machine puzzles where buttons manipulate lights or joltage values. The challenge involves:
- **Input**: Machine specifications with buttons and targets
- **Goal**: Find minimum button presses to reach target state
- **Output**: Total button presses across all machines

Input format example: `[.##.](1,3)(0,2){3,5,4,7}`
- `[.##.]` - Target light states (. = off, # = on)
- `(1,3)` - Button affecting indices 1 and 3
- `{3,5,4,7}` - Target joltage values

Two puzzle types:
1. **Part A (Lights)**: Toggle lights to match target using BFS
2. **Part B (Joltage)**: Reduce values to zero using DP with halving steps

### Core Data Structures

**Machine (Record)**
- Target lights (List<Boolean>)
- Buttons (List<Set<Integer>>) - indices each button affects
- Target joltage (List<Integer>)

**BFS Queue (Light Solver)**
- State: (current lights, press count)
- Visited set prevents revisiting configurations

**Memoization Map (Joltage Solver)**
- Key: current joltage state
- Value: minimum presses needed
- Handles overlapping subproblems in DP

---

## Architecture and Data Flow

### Main.java - Entry Point
```
Input File → Day10Mapper → List<Machine>
                               ↓
    MachineProcessor (LightConfigurationSolver) → Total presses A
                               ↓
    MachineProcessor (JoltageSolver) → Total presses B
```

**Key Design:** Same machine list, different solving algorithms—one uses BFS, one uses DP.

---

### Day10Mapper.java - Delegation Pattern
```
Lines → filter/trim → InputParser.parseLine() → List<Machine>
```

Simple mapper that delegates parsing complexity to utility class.

---

### InputParser.java - Regex-Based Parsing

**Component Recognition:**
```java
Pattern COMPONENT_PATTERN = Pattern.compile(
    "\\[[.#]+\\]|\\([^)]+\\)|\\{[^}]+\\}"
);
```

Matches three component types:
- `[lights]` - Square brackets with dots/hashes
- `(buttons)` - Parentheses with numbers
- `{joltage}` - Curly braces with numbers

**Parsing Algorithm:**
```java
public static Machine parseLine(String line) {
    List<String> components = COMPONENT_PATTERN.matcher(line)
        .results()
        .map(MatchResult::group)
        .toList();
    
    // Parse lights: [.##.] -> [false, true, true, false]
    List<Boolean> lights = components.stream()
        .filter(s -> s.startsWith("["))
        .flatMap(s -> s.substring(1, s.length() - 1)
                       .chars()
                       .mapToObj(c -> c == '#'))
        .toList();
    
    // Parse buttons: (1,3) -> Set{1, 3}
    List<Set<Integer>> buttons = components.stream()
        .filter(s -> s.startsWith("("))
        .map(InputParser::parseCsvToSet)
        .toList();
    
    // Parse joltage: {3,5,4,7} -> [3, 5, 4, 7]
    List<Integer> joltage = components.stream()
        .filter(s -> s.startsWith("{"))
        .flatMap(s -> Arrays.stream(s.substring(1, s.length() - 1).split(","))
                           .map(String::trim)
                           .filter(v -> !v.isEmpty())
                           .map(Integer::parseInt))
        .toList();
    
    return new Machine(lights, buttons, joltage);
}
```

**Flexible parsing:** Components can appear in any order in input.

---

### MachineProcessor.java - Service Layer
```
List<Machine> → solve each → sum results
```

**Simple aggregation:**
```java
public long solve(List<Machine> machines) {
    return machines.stream()
        .mapToLong(solver::solve)
        .sum();
}
```

---

### MachineSolver.java - Strategy Interface
```java
public interface MachineSolver {
    long solve(Machine machine);
}
```

Minimal interface for different solving algorithms.

---

### LightConfigurationSolver.java - BFS Approach

**Algorithm: Breadth-First Search**
```
Start: All lights off
Queue: [(initial state, 0 presses)]
Visited: {initial state}
    ↓
While queue not empty:
  Current state = dequeue
  If matches target → return presses
  For each button:
    Next state = toggle lights
    If not visited:
      Add to queue with presses + 1
```

**Implementation:**
```java
@Override
public long solve(Machine machine) {
    record State(List<Boolean> lights, int presses) {}
    
    Set<List<Boolean>> visited = new HashSet<>();
    Queue<State> queue = new ArrayDeque<>();
    
    List<Boolean> initial = Collections.nCopies(
        machine.targetLights().size(), 
        false
    );
    queue.add(new State(initial, 0));
    visited.add(initial);
    
    while (!queue.isEmpty()) {
        State current = queue.poll();
        
        if (current.lights.equals(machine.targetLights())) {
            return current.presses;
        }
        
        for (Set<Integer> button : machine.buttons()) {
            List<Boolean> nextState = toggle(current.lights, button);
            
            if (visited.add(nextState)) {
                queue.add(new State(nextState, current.presses + 1));
            }
        }
    }
    return 0;  // Unreachable
}
```

**Toggle Operation:**
```java
private List<Boolean> toggle(List<Boolean> current, Set<Integer> buttonIndices) {
    List<Boolean> next = new ArrayList<>(current);
    for (int idx : buttonIndices) {
        if (idx < next.size()) {
            next.set(idx, !next.get(idx));
        }
    }
    return next;
}
```

**Why BFS?** Guarantees shortest path (minimum presses) due to level-by-level exploration.

**Complexity:**
- Time: O(2^n × b) where n = lights, b = buttons
- Space: O(2^n) for visited states

---

### JoltageSolver.java - Dynamic Programming with Halving

**Algorithm: Recursive DP with Memoization**
```
Goal: Reduce all joltage values to zero
Actions:
  1. Press buttons (decrease specific indices by 1)
  2. Halve all values (when all even and ≥ 0)
    
Recursion:
  If all zero → return 0
  If any negative → return ∞
  Try all button combinations:
    If resulting state is all-even:
      Cost = button_presses + 2 × solve(halved_state)
  Return minimum cost
```

**Implementation:**
```java
@Override
public long solve(Machine machine) {
    memo.clear();
    return compute(new ArrayList<>(machine.targetJoltage()), machine.buttons());
}

private long compute(List<Integer> target, List<Set<Integer>> buttons) {
    if (isTargetReached(target)) return 0;
    if (isInvalid(target)) return INF;
    if (memo.containsKey(target)) return memo.get(target);
    
    Long minPresses = INF;
    int numButtons = buttons.size();
    
    // Try all 2^numButtons button combinations
    for (int i = 0; i < (1 << numButtons); i++) {
        List<Integer> nextState = applyButtons(target, buttons, i);
        
        if (canDivideByTwo(nextState)) {
            long subResult = compute(halveState(nextState), buttons);
            
            if (subResult != INF) {
                int currentStepPresses = Integer.bitCount(i);
                minPresses = Math.min(minPresses, 
                    currentStepPresses + 2 * subResult);
            }
        }
    }
    
    memo.put(target, minPresses);
    return minPresses;
}
```

**Key Operations:**

**Apply Buttons (Bitmask):**
```java
private List<Integer> applyButtons(List<Integer> current, 
                                    List<Set<Integer>> buttons, 
                                    int bitmask) {
    List<Integer> next = new ArrayList<>(current);
    
    for (int b = 0; b < buttons.size(); b++) {
        if (((bitmask >> b) & 1) == 1) {  // Button b pressed
            for (int idx : buttons.get(b)) {
                if (idx < next.size()) {
                    next.set(idx, next.get(idx) - 1);
                }
            }
        }
    }
    return next;
}
```

**Halving Check & Operation:**
```java
private boolean canDivideByTwo(List<Integer> state) {
    return state.stream().allMatch(v -> v >= 0 && v % 2 == 0);
}

private List<Integer> halveState(List<Integer> state) {
    return state.stream().map(v -> v / 2).toList();
}
```

**Cost Formula:** `button_presses + 2 × subproblem_cost`

**Why ×2?** Cost propagates upward through recursive structure—each halving step doubles the effective cost of operations below it.

**Complexity:**
- Time: O(2^b × states) where b = buttons, states = unique joltage configurations
- Space: O(states) for memoization

---

## SOLID Principles Applied

### **S - Single Responsibility Principle**

**InputParser** - Only parses machine specifications
- Does NOT: Solve puzzles or process multiple machines

**LightConfigurationSolver** - Only solves light puzzles with BFS
- Does NOT: Solve joltage puzzles or parse input

**JoltageSolver** - Only solves joltage puzzles with DP
- Does NOT: Toggle lights or parse input

**MachineProcessor** - Only aggregates results across machines
- Does NOT: Implement solving algorithms

---

### **O - Open/Closed Principle**

**Adding new solver:**
```java
// Solve using A* search with heuristic
public class HeuristicSolver implements MachineSolver {
    @Override
    public long solve(Machine machine) {
        // Priority queue with estimated cost
        PriorityQueue<State> pq = new PriorityQueue<>(
            Comparator.comparingInt(s -> s.cost + heuristic(s))
        );
        // A* algorithm implementation
    }
}

// Use without changing existing code
new MachineProcessor(new HeuristicSolver()).solve(machines);
```

---

### **L - Liskov Substitution Principle**

**Both solvers are interchangeable:**
```java
MachineProcessor p1 = new MachineProcessor(new LightConfigurationSolver());
MachineProcessor p2 = new MachineProcessor(new JoltageSolver());

// Both work correctly with same interface
long result1 = p1.solve(machines);  // BFS solution
long result2 = p2.solve(machines);  // DP solution
```

Different algorithms, same contract: solve machine → return presses.

---

### **I - Interface Segregation Principle**

**MachineSolver is minimal:**
```java
public interface MachineSolver {
    long solve(Machine machine);
}
```

One method, focused interface.

---

### **D - Dependency Inversion Principle**

**Processor depends on abstraction:**
```java
public class MachineProcessor {
    private final MachineSolver solver;  // Interface
    
    public MachineProcessor(MachineSolver solver) {
        this.solver = solver;
    }
}
```

High-level processor doesn't depend on specific solvers.

---
