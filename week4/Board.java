import edu.princeton.cs.algs4.StdRandom;
import java.util.Stack;
import java.util.Arrays;

public class Board {
    
    private int[] arrSquares;
    
    private final int dimension;
    
    private int blankSquareRow = -1;
        
    private int blankSquareCol = -1;
    
    private int hammingBlocks = -1;
    
    private int manhattanBlocks = -1;
    
    private String out = "";
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        dimension = blocks.length;

        arrSquares = new int[dimension * dimension];

        for (int i = 0; i < blocks.length; i++) {
            int[] row = blocks[i];
            for (int j = 0; j < row.length; j++) {
                arrSquares[dimension * i + j] = blocks[i][j];               
                if (blocks[i][j] == 0) {
                    blankSquareRow = i;
                    blankSquareCol = j;
                }
            }
        }
    }
    
    private Board(Board that, int fromIndex, int toIndex, int newBlankSquareRow, int newBlankSquareCol) {
        this.arrSquares = Arrays.copyOf(that.arrSquares, that.arrSquares.length);
        this.dimension = that.dimension;
        
        this.blankSquareRow = newBlankSquareRow;
        this.blankSquareCol = newBlankSquareCol;        
        
        arrSquares[fromIndex] = arrSquares[toIndex];
        arrSquares[toIndex] = 0;
    }
    
    // board dimension n
    public int dimension()     
    {
        return dimension;
    }
    
    // number of blocks out of place
    public int hamming()
    {
        if (hammingBlocks != -1 || hammingBlocks == 0)
            return hammingBlocks;
        
        hammingBlocks = 0;

        for (int i = 0; i < arrSquares.length; i++) {
            if (arrSquares[i] == 0) {
                continue;
            }

            if (arrSquares[i] != (i + 1)) {
                hammingBlocks++;
            }
        }

        return hammingBlocks;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        if (manhattanBlocks != -1)
            return manhattanBlocks;
        
        manhattanBlocks = 0;
        
        for (int i = 0; i < arrSquares.length; i++) {
            if (arrSquares[i] == 0) {
                continue;
            }

            int row = Math.abs((i) % dimension - (arrSquares[i] - 1) % dimension);
            int col = Math.abs((i) / dimension - (arrSquares[i] - 1) / dimension);
            
            manhattanBlocks += row + col;
        }

        return manhattanBlocks;
    }
        
    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin()
    {
        boolean success = false;

        int[][] twinSquares = new int[dimension][dimension];
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                twinSquares[i][j] = arrSquares[i * dimension + j];
            }
        }      
       
        while (!success) {
            int row = StdRandom.uniform(dimension);
            int col = StdRandom.uniform(dimension - 1);                  
            
            if (dimension == 2)
                col = 1;

            if (col < 1 || twinSquares[row][col] == 0 || twinSquares[row][col - 1] == 0)
                continue;            
            
            int temp = twinSquares[row][col];
            twinSquares[row][col] = twinSquares[row][col - 1];
            twinSquares[row][col - 1] = temp;         

            success = true;
        }
       
        Board tstBoard = new Board(twinSquares);
        //System.out.print( tstBoard.toString());         
        
        return tstBoard;
    }
    
    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;        

        Board that = (Board) y;
        if (that.dimension != this.dimension)
            return false;
        
        for (int i = 0; i < dimension*dimension; i++) {
            if (this.arrSquares[i] != that.arrSquares[i])
                return false;
        }
         
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        Stack<Board> boards = new Stack<Board>(); 
        
        int fromIndex = getSquareIndex(blankSquareRow, blankSquareCol);
        
        if (blankSquareRow != 0) {            
            int toIndex = getSquareIndex(blankSquareRow - 1, blankSquareCol);
            boards.push(new Board(this, fromIndex, toIndex, blankSquareRow - 1, blankSquareCol));
        }

        if (blankSquareRow < dimension - 1) {
            int toIndex = getSquareIndex(blankSquareRow + 1, blankSquareCol);
            boards.push(new Board(this, fromIndex, toIndex, blankSquareRow + 1, blankSquareCol));
        }

        if (blankSquareCol != 0) {
            int toIndex = getSquareIndex(blankSquareRow, blankSquareCol - 1);
            boards.push(new Board(this, fromIndex, toIndex, blankSquareRow, blankSquareCol - 1));
        }

        if (blankSquareCol < dimension - 1) {
            int toIndex = getSquareIndex(blankSquareRow, blankSquareCol + 1);
            boards.push(new Board(this, fromIndex, toIndex, blankSquareRow, blankSquareCol + 1));
        }
        
        return boards;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString()   
    {
        if (out.length() > 0)
            return out;        

        StringBuilder s = new StringBuilder();
        
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                s.append(String.format("%2d ", arrSquares[getSquareIndex(i, j)]));
            s.append("\n");
        }
        
        out = s.toString();
        return out;
    }
    
    private int getSquareIndex(int i, int j) {
        return i * dimension + j;
    }
    
    private static void printBoardInfo(Board tstBoard)
    {
        System.out.print(tstBoard.toString());         
        System.out.println("hamming: " + tstBoard.hamming());       
        System.out.println("manhattan: " + tstBoard.manhattan());     
        System.out.println("isGoal: " + tstBoard.isGoal());     
        
        for (Board board : tstBoard.neighbors()) {
            System.out.print(board.toString());      
        } 
        
        System.out.println();
    }
    
    private static void doTest(int[][] twinSquares)
    {
        Board tstBoard = new Board(twinSquares);
        
        System.out.println("tstBoard");
        printBoardInfo(tstBoard);     
        
        
        Board twinBoard = tstBoard.twin();
        System.out.println("twinBoard");
        printBoardInfo(twinBoard); 
        
        Board twinBoard1 = twinBoard.twin();
        System.out.println("twinBoard1");
        printBoardInfo(twinBoard1); 
    }
    
    // unit tests (not graded)
    public static void main(String[] args) 
    {       
        /*int[][] twinSquares = new int[][] {
            {8, 1, 3}, 
            {4, 0, 2}, 
            {7, 6, 5}
        };*/
        
        int[][] twinSquares = new int[][] {
            {1, 2, 3}, 
            {4, 5, 6}, 
            {7, 8, 0}
        };
        doTest(twinSquares);       
    }
}