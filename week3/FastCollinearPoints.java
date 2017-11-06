import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class FastCollinearPoints {
    private LineSegment[] arraySegments;
    private Object[] segmentObjects;
    private int numberOfSegments;
    
    // finds all line segments containing 4 points
    
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        
        int arrayLength = points.length;        
        for (int i = 0; i < arrayLength; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        
        if (isExistsDuplicates(points))
            throw new IllegalArgumentException();      
        
        int n = points.length;
        
        Point[] pointsCopy = new Point[n];        
        System.arraycopy(points, 0, pointsCopy, 0, n);
        
        Arrays.sort(pointsCopy);
        
        numberOfSegments = 0;      

        segmentObjects = new LineSegment[n];               
        
        for (int i = 0; i < n - 3; i++) {            
            Point p = pointsCopy[i];            
            Point[] sortedPoints = new Point[n];  // content length = n - 1, last = null
            
            System.arraycopy(pointsCopy, 0, sortedPoints, 0, i);            
            System.arraycopy(pointsCopy, i + 1, sortedPoints, i, n - i - 1);            
            Arrays.sort(sortedPoints, 0, n - 1, p.slopeOrder());
            
            int currentStartIndex = 0;            
            for (int j = 1; j < n; j++) {                
                Point currentStartPoint = sortedPoints[currentStartIndex];                
                Point currentPoint = sortedPoints[j];                
                if (currentPoint == null || currentPoint.slopeTo(p) != currentStartPoint.slopeTo(p)) {                    
                    int len = j - currentStartIndex;
                    
                    /* critical - if current starting point of this segment is smaller than p,
                     *
                     then this line segment is already in the collection */
                    
                    if (len >= 3 && currentStartPoint.compareTo(p) > 0) {                        
                        segmentObjects[numberOfSegments++] = new LineSegment(p, sortedPoints[j - 1]);      
                        if (numberOfSegments == segmentObjects.length)                          
                            resize(numberOfSegments * 2);
                    }                    
                    currentStartIndex = j;                    
                }                
            }            
        }
        
        arraySegments = new LineSegment[numberOfSegments];        
        for (int i = 0; i < numberOfSegments; i++)        
            arraySegments[i] = (LineSegment) segmentObjects[i];
    }   
    
    
    // the number of line segments    
    public int numberOfSegments() {        
        return arraySegments.length;        
    }
    
    
    // the line segments    
    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[numberOfSegments()];        
        System.arraycopy(arraySegments, 0, ret, 0, numberOfSegments());
        return ret;        
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
        if (capacity > numberOfSegments) {
            Object[] temp = new Object[capacity];
            System.arraycopy(segmentObjects, 0, temp, 0, numberOfSegments);
            segmentObjects = temp;
        }
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
        
        FastCollinearPoints collinear = new FastCollinearPoints(points);        
        for (LineSegment segment : collinear.segments()) {            
            StdOut.println(segment);            
            segment.draw();            
        } 
        StdDraw.show();
    }
}