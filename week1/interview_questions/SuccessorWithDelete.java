import edu.princeton.cs.algs4.QuickFindUF;

class SuccessorWithDelete {
    
    private QuickFindUF S;
    
    public SuccessorWithDelete(int N) {
        S = new QuickFindUF(N);
    }
    
    public void remove(int x) {
        S.union(x, x+1);
    }
    
    public int successor(int x) {
        return S.find(x);
    }
    
    public static void main(String[] args) {
        int N = 15;
        SuccessorWithDelete testSuccessor = new SuccessorWithDelete(N);
        System.out.println(testSuccessor.successor(3));
        testSuccessor.remove(7);
        testSuccessor.remove(6);
        System.out.println(testSuccessor.successor(6));
        
        testSuccessor.remove(11);
        System.out.println(testSuccessor.successor(10));
    }
}