package software.ulpgc.adventofcode2025.day08;

public class UnionFind {
    private final int[] parent;
    private final int[] size;
    private int components;

    public UnionFind(int n){
        parent = new int[n];
        size = new int[n];
        components = n;
        for(int i = 0; i < n; i++){
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int p){
        while(parent[p] != p){
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public void union(int p, int q){
        int rootP = find(p);
        int rootQ = find(q);
        if(rootP == rootQ) return;

        if(size[rootP] < size[rootQ]){
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else{
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        components--;
    }

    public int getSize(int p){ return size[find(p)]; }
    public int getComponentsCount(){ return components; }
}
