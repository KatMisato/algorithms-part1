/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
        
    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0).  Treat the slope of a horizontal line segment as positive zero; 
     * treat the slope of a vertical line segment as positive infinity; treat the slope of a degenerate 
     * line segment (between a point and itself) as negative infinity. 
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        
        if (this.y == that.y)
            return +0.0;
        
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;   
        
        return (that.y - this.y) / (double) (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        int diffX = this.x - that.x;
        int diffY = this.y - that.y;
        
        if (diffY == 0 && diffX == 0)
            return 0;
            
        if (diffY < 0 || (diffY == 0 && diffX < 0))
            return -1;

        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     * Formally, the point (x1, y1) is less than the point (x2, y2) 
     * if and only if the slope (y1 ? y0) / (x1 ? x0) is less than the slope (y2 ? y0) / (x2 ? x0). 
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {       
        return new PointComparator();
    }

    private class PointComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slopeP1 = slopeTo(p1);
            double slopeP2 = slopeTo(p2);
            
            if (slopeP1 < slopeP2)
                return -1;
            
            if (slopeP1 > slopeP2)
                return 1;
            
            return 0;
            
        }        
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {       
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        Point testPoint = new Point(130, 132);
        testPoint.draw();
        
        Point testPoint1 = new Point(130, 132);
        testPoint1.draw();
        
        Point testPoint2 = new Point(135, 132);
        testPoint2.draw();
        
        
        Point testPoint3 = new Point(135, 142);
        testPoint3.draw();
        
        Point testPoint4 = new Point(5, 2);
        testPoint4.draw();
        
        StdDraw.show();
        
        System.out.println("testPoint = " + testPoint.toString());
        System.out.println("compare to " + testPoint1.toString() + " = " + testPoint.compareTo(testPoint1));
        System.out.println("compare to " + testPoint2.toString() + " = " + testPoint.compareTo(testPoint2));
        System.out.println("compare to " + testPoint3.toString() + " = " + testPoint.compareTo(testPoint3));
        System.out.println("compare to " + testPoint3.toString() + " = " + testPoint.compareTo(testPoint4));
    }
}