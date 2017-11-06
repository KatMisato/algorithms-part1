import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class Solver {
    
    private boolean isSolvable;
    private final Stack<Board> boards;
    
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private int manhattan;
        private SearchNode previous;
        private int cachedPriority = -1;

        SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.manhattan = board.manhattan();
            if (this.previous == null) {
                this.moves = 0;
            }
            else {
                moves = previous.moves + 1;
            }
        }

        private int priority() {
            if (cachedPriority == -1)
                cachedPriority = this.manhattan + this.moves;

            return cachedPriority;
        }

        public boolean equals(Object y) {
            if (y == this) return true;
            if (y == null) return false;
            if (y.getClass() != this.getClass()) return false;

            SearchNode that = (SearchNode) y;
            if (that.board.equals(board))
                return true;
            return false;
        }
        
        public int compareTo(SearchNode that) {
            return this.priority() - that.priority();
        }
    }
        
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
       
        boards = new Stack<Board>();

        if (initial.isGoal()) {
            isSolvable = true;
            this.boards.push(initial);
            return;
        }

        if (initial.twin().isGoal()) {
            isSolvable = false;
            return;
        }
        
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();

        int moves = 0;

        Board board = initial;
        Board boardTwin = initial.twin();

        SearchNode node = new SearchNode(board, 0, null);
        SearchNode nodeTwin = new SearchNode(boardTwin, 0, null);

        minPQ.insert(node);
        minPQTwin.insert(nodeTwin);

        while (!boardTwin.isGoal()) {

            node = minPQ.delMin();
            nodeTwin = minPQTwin.delMin();

            board = node.board;
            boardTwin = nodeTwin.board;

            if (boardTwin.isGoal()) {
                isSolvable = false;
                return;
            }

            if (board.isGoal()) {
                isSolvable = true;
                this.boards.push(board);
                while (node.previous != null) {
                    node = node.previous;
                    this.boards.push(node.board);
                }
                return;
            }

            node.moves++;
            nodeTwin.moves++;

            Iterable<Board> neighbors = board.neighbors();
            for (Board neighbor : neighbors) {
                if (node.previous != null && neighbor.equals(node.previous.board))
                    continue;

                SearchNode newNode = new SearchNode(neighbor, node.moves, node);
                minPQ.insert(newNode);
            }

            Iterable<Board> neighborsTwin = boardTwin.neighbors();
            for (Board neighbor : neighborsTwin) {
                if (nodeTwin.previous != null && neighbor.equals(nodeTwin.previous.board))
                    continue;

                SearchNode newNode = new SearchNode(neighbor, nodeTwin.moves, nodeTwin);

                minPQTwin.insert(newNode);
            }
            
            moves++;
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable()
    {
        return isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        if (isSolvable)
            return boards.size() - 1;

        return -1;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() 
    {
        if (isSolvable)
             return boards;    
        return null;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) 
    {
       int[][] twinSquares = new int[][] {
            {1, 2, 3}, 
            {4, 5, 6}, 
            {8, 7, 0}
        };
        
        /*int[][] twinSquares = new int[][] {
            {0, 1, 3}, 
            {4, 2, 5}, 
            {7, 8, 6}
        };*/
        
        Board initial = new Board(twinSquares);
        
        System.out.println(initial.toString() + " ");         
        System.out.println("hamming: " + initial.hamming());       
        System.out.println("manhattan: " + initial.manhattan());     
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}