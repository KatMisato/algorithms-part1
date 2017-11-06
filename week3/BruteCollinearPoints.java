import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

public class BruteCollinearPoints {        
    private Object[] segmentObjects;
    private final LineSegment[] arraySegments;
    private int numberOfSegments;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        
        int arrayLength = points.length;        
        for (int i = 0; i < arrayLength; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        
        if (isExistsDuplicates(points))
            throw new IllegalArgumentException();        
        
        segmentObjects = new Object[arrayLength];    
        numberOfSegments = 0; 
        
        for (int i = 0; i < arrayLength; i++) { 
            for (int j = i + 1; j < arrayLength; j++) {                
                for (int k = j + 1; k < arrayLength; k++) {
                    for (int m = k + 1; m < arrayLength; m++) {                        
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];
                        
                        double slopePQ = p.slopeTo(q);
                        double slopePR = p.slopeTo(r);
                        double slopePS = p.slopeTo(s);
                        
                        if (slopePQ == slopePR && slopePR == slopePS) {
                            Point[] tuple = new Point[] {p, q, r, s};
                            Arrays.sort(tuple);
                            segmentObjects[numberOfSegments++] = new LineSegment(tuple[0], tuple[3]);
                            if (numberOfSegments == segmentObjects.length) {
                                resize(numberOfSegments * 2);
                            }
                        }
                    }
                }
            }
        }
        arraySegments = new LineSegment[numberOfSegments];        
        for (int i = 0; i < numberOfSegments; i++)        
            arraySegments[i] = (LineSegment) segmentObjects[i];
    }
    
    private boolean isExistsDuplicates(Point[] points) {       
        if (points.length <= 0)
            return true;
        
        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        Arrays.sort(pointsCopy);
        
        Point currentPoint = pointsCopy[0];
        for (int i = 1; i < pointsCopy.length; i++) {
            if (pointsCopy[i].compareTo(currentPoint) == 0)
                return true;
            currentPoint = pointsCopy[i];
        }
        
        return false;        
    }
    
    private void resize(int capacity) {
        assert capacity >= numberOfSegments;        
        Object[] temp = new Object[capacity];
        System.arraycopy(segmentObjects, 0, temp, 0, numberOfSegments);
        segmentObjects = temp;
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;        
    }
    
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[numberOfSegments()];        
        System.arraycopy(arraySegments, 0, ret, 0, numberOfSegments());
        return ret;        
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        
        int arrayLength = in.readInt();
        
        Point[] points = new Point[arrayLength];
        
        for (int i = 0; i < arrayLength; i++) {
            
            int x = in.readInt();
            
            int y = in.readInt();
            
            points[i] = new Point(x, y);
            
        }
        
        for (int i = 0; i < arrayLength - 1; i++)
            System.out.print(points[i].toString() + " ");
        System.out.println(points[arrayLength - 1].toString() + " ");
        
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        for (Point p : points) {            
            p.draw();            
        }
        StdDraw.show();
        
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);        
        for (LineSegment segment : collinear.segments()) {            
            StdOut.println(segment);            
            segment.draw();            
        }     
        StdDraw.show();
    }
}
