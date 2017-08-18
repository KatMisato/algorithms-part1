import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnionUFS
{
    private int[] id;
    private int[] sz;
    private int[] maximumInComponent;
    private int   count;
    
    public QuickUnionUFS(int N) {
        count = N;
        id = new int[N];
        sz = new int[N];
        maximumInComponent = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
            maximumInComponent[i] = i;
        }
    }
    
    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }
    
    public int count() {
        return count;
    }
    
    public int find(int i) {
        return maximumInComponent[root(i)];
    }
    
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
    
    public void union(int p, int q)
    {
        int i = root(p);
        int j = root(q);
        int maxI = maximumInComponent[i];
        int maxJ = maximumInComponent[j];
        if (i == j) return;
        if  (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; if (maximumInComponent[i] > maximumInComponent[j]) maximumInComponent[j] = maximumInComponent[i]; } 
        else                { id[j] = i; sz[i] += sz[j]; if (maximumInComponent[j] > maximumInComponent[i]) maximumInComponent[i] = maximumInComponent[j]; }  
        count--;
    }
    
    public static void main(String[] args) {
        QuickUnionUFS uf = new QuickUnionUFS(10);
        uf.union(2, 6);
        uf.union(6, 9);
        uf.union(1, 2);        
        StdOut.println("uf.find(1): " + uf.find(1));
        StdOut.println("uf.find(2): " + uf.find(2));
        StdOut.println("uf.find(6): " + uf.find(6));
    }
}