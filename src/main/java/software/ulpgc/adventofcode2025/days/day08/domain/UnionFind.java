package software.ulpgc.adventofcode2025.days.day08.domain;

/**
 * Union-Find (Disjoint Set Union) data structure for tracking connected components.
 *
 * This implementation uses path compression and union by size optimizations to
 * achieve near-constant time operations. It efficiently tracks which elements
 * belong to the same connected component and supports merging components.
 *
 * Key features:
 * - Path compression: Flattens tree structure during find operations
 * - Union by size: Attaches smaller trees under larger ones
 * - Component counting: Tracks the total number of separate components
 * - Size tracking: Maintains the size of each component
 */
public class UnionFind {
    private final int[] parent;
    private final int[] size;
    private int components;

    /**
     * Constructs a Union-Find structure with n initially disconnected elements.
     *
     * Initializes n elements (indexed 0 to n-1) where each element starts as
     * its own component of size 1. The parent of each element is initially
     * itself, indicating it's a root of its own tree.
     *
     * @param n The number of elements in the structure
     */
    public UnionFind(int n){
        parent = new int[n];
        size = new int[n];
        components = n;
        for(int i = 0; i < n; i++){
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Finds the root representative of the component containing element p.
     *
     * Uses path compression optimization: as it traverses up to the root,
     * it updates each node's parent to point directly to its grandparent,
     * effectively flattening the tree structure. This makes future find
     * operations faster.
     *
     * Time complexity: Nearly O(1) amortized with path compression
     *
     * @param p The element to find the root of
     * @return The root representative of the component containing p
     */
    public int find(int p){
        while(parent[p] != p){
            parent[p] = parent[parent[p]];  // Path compression
            p = parent[p];
        }
        return p;
    }

    /**
     * Unites the components containing elements p and q.
     *
     * Uses union by size optimization: attaches the root of the smaller tree
     * under the root of the larger tree. This keeps the trees balanced and
     * ensures efficient find operations. If p and q are already in the same
     * component, does nothing.
     *
     * When a successful union occurs, decrements the total component count.
     *
     * Time complexity: Nearly O(1) amortized with union by size
     *
     * @param p The first element
     * @param q The second element
     */
    public void union(int p, int q){
        int rootP = find(p);
        int rootQ = find(q);
        if(rootP == rootQ) return;  // Already in same component

        // Union by size: attach smaller tree under larger tree
        if(size[rootP] < size[rootQ]){
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else{
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        components--;
    }

    /**
     * Returns the size of the component containing element p.
     *
     * Finds the root of p's component and returns that component's size.
     * The size represents how many elements are connected in that component.
     *
     * @param p The element whose component size to query
     * @return The number of elements in p's component
     */
    public int getSize(int p){ return size[find(p)]; }

    /**
     * Returns the current number of separate components.
     *
     * Initially equals n (each element is its own component). Decreases by 1
     * each time a union successfully merges two previously separate components.
     * Reaches 1 when all elements are connected in a single component.
     *
     * @return The number of disjoint components currently in the structure
     */
    public int getComponentsCount(){ return components; }
}