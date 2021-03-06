/******************************************************************************
 *  Compilation: javac PerlocationStats.java
 *  Execution:  java PerlocationStats
 *  Dependencies: StdStats.java StdRandom.java
 * 
 *  Perform a series of computational experiments for Percolation class
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    
    private final double [] resTrials;
    private double mean = -1;
    private double dev = -1;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        
        resTrials = new double [trials];
        
        for (int i = 0; i < trials; i++) {           
            Percolation testPerlocation = new Percolation(n);
            
            int steps = 0;
            
            while (!testPerlocation.percolates()) {
                
                int row = 1 + (int) (StdRandom.uniform() * ((n - 1) + 1));
                
                int column = 1 + (int) (StdRandom.uniform() * ((n - 1) + 1));
                
                
                if (!testPerlocation.isOpen(row, column)) {                    
                    testPerlocation.open(row, column);                    
                    steps++;                    
                }                
            }

            resTrials[i] = (double) steps / (n*n);            
        }        
    } 
    
    // sample mean of percolation threshold
    public double mean() {
        if (mean == -1)
            mean = StdStats.mean(resTrials);
        return mean;
    }      
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        if (dev == -1)
            dev = StdStats.stddev(resTrials);
        return dev;
    }  
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {    
        return mean() -((1.96*stddev()) / Math.sqrt(resTrials.length)); 
    }  
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96*stddev()) / Math.sqrt(resTrials.length)); 
    }          
    
    // test client, described below    
    public static void main(String[] args) {        
        runAndPrintResults(200, 100);       
        runAndPrintResults(200, 100);       
        runAndPrintResults(2, 10000);       
        runAndPrintResults(2, 100000);       
    }     
    
    // run test and orint results
    private static void runAndPrintResults(int n, int t) {
        PercolationStats ps = new PercolationStats(n, t); 
        
        System.out.print("PercolationStats " + n + " " + t + "\n");  
        System.out.print("mean\t\t\t= " + ps.mean() + "\n");        
        System.out.print("std dev\t\t\t= " + ps.stddev() + "\n");
        System.out.print("95% confidence interval\t= [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]\n");  
    }   
}