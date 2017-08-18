/******************************************************************************
 *  Compilation: javac SocialNetworkConnectivity.java
 *  Execution:  java SocialNetworkConnectivity
 *  Dependencies: StdOut.java StdIn.java WeightedQuickUnionUF.java
 * 
 *  Perform a series of computational experiments for Percolation class
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class SocialNetworkConnectivity {
    
    public static void main(String[] args) {        
        int userCount = StdIn.readInt();
        
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(userCount);
        String timestamp;
        
        // read timestamps from log file in this format: <user1> <user2> <timestamp>
        while (!StdIn.isEmpty()) {
            
            int userIndex1 = StdIn.readInt();
            int userIndex2 = StdIn.readInt();
            timestamp = StdIn.readString();           
            
            uf.union(userIndex1, userIndex2);
            
            if(uf.count() == 1) {
                StdOut.println("All members were connected at: " + timestamp);
                break;
            }            
        }
        
        if(uf.count() > 1) 
            StdOut.println("All members not connected!");
    }
}