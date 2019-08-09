/* *****************************************************************************
 *  Name: H.H.
 *  Date: 3/19/19
 *  Description: From a given file of coordinates, how many line segments
 *  made of at least 4 points are present? Only include maximal line segments.
 *  For example pointA -> pointE should be included, but not pointA -> pointD.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            // check for duplicates and null arguments
            if (points[i] == null) throw new IllegalArgumentException();
        }

        int length = points.length;
        Point[] pointsSorted = new Point[length]; // sorted array to use as reference
        System.arraycopy(points, 0, pointsSorted, 0, length);
        Arrays.sort(pointsSorted);
        Point[] pointsCopy = new Point[length]; // array to sort by slopeOrder
        System.arraycopy(pointsSorted, 0, pointsCopy, 0, length);
        List<Object[]> foundSegments = new ArrayList<>();
        lineSegments = new ArrayList<>();

        for (int i = 1; i < pointsSorted.length; i++) {
            if (pointsSorted[i].compareTo(pointsSorted[i - 1]) == 0)
                throw new IllegalArgumentException(); // check for duplicate points
        }

        if (points.length > 3) {
            for (int i = 0; i < length; i++) {
                Arrays.sort(pointsCopy, pointsSorted[i].slopeOrder());
                int sharedSlopeCount = 2;
                Point smallestPoint = pointsCopy[0];
                Point largestPoint = pointsCopy[0];
                double prevSlope = pointsCopy[0].slopeTo(pointsCopy[1]);
                double sharedSlope = Double.NEGATIVE_INFINITY;
                for (int j = 2; j < length; j++) {
                    sharedSlope = pointsCopy[0].slopeTo(pointsCopy[j]);
                    if (Double.compare(sharedSlope, prevSlope) == 0
                            && Double.compare(sharedSlope, Double.NEGATIVE_INFINITY) != 0) {
                        sharedSlopeCount++;
                        if (pointsCopy[j - 1].compareTo(smallestPoint) < 0)
                            smallestPoint = pointsCopy[j - 1];
                        else if (pointsCopy[j].compareTo(smallestPoint) < 0)
                            smallestPoint = pointsCopy[j];
                        else if (pointsCopy[j].compareTo(largestPoint) > 0)
                            largestPoint = pointsCopy[j];
                        else if (pointsCopy[j - 1].compareTo(largestPoint) > 0)
                            largestPoint = pointsCopy[j - 1];
                    }
                    else {
                        if (sharedSlopeCount > 3) {
                            foundSegments
                                    .add(new Object[] { prevSlope, smallestPoint, largestPoint });
                        }
                        sharedSlopeCount = 2;
                        prevSlope = pointsCopy[0].slopeTo(pointsCopy[j]);
                        smallestPoint = pointsCopy[0];
                        largestPoint = pointsCopy[0];
                    }
                }
                if (Double.compare(sharedSlope, prevSlope) == 0 && sharedSlopeCount > 3) {
                    // accounting for if the last cluster of points are all sharing slopes
                    foundSegments.add(new Object[] { prevSlope, smallestPoint, largestPoint });
                }
                Arrays.sort(pointsCopy);
            }
            Collections.sort(foundSegments, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] o1, Object[] o2) {
                    return Double.compare((Double) o1[0], (Double) o2[0]);
                }
            });
            Collections.sort(foundSegments, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] o1, Object[] o2) {
                    return ((Point) o1[1]).compareTo((Point) o2[1]);
                }
            });
            Point prevPoint1 = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
            Point prevPoint2 = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
            for (Object[] pointArr : foundSegments) {
                Point p1 = (Point) pointArr[1];
                Point p2 = (Point) pointArr[2];
                if (p1.compareTo(prevPoint1) != 0 ||
                        (p2.compareTo(prevPoint2) != 0)) {
                    lineSegments.add(new LineSegment(p1, p2));
                    prevPoint1 = p1;
                    prevPoint2 = p2;
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        // the maximal line segments
        LineSegment[] linesSegs = new LineSegment[lineSegments.size()];
        int i = 0;
        for (LineSegment line : lineSegments) {
            linesSegs[i++] = line;
        }
        return linesSegs;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
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
