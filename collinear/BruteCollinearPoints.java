/* *****************************************************************************
 *  Name: H.H.
 *  Date: 3/12/19
 *  Description: Brute force solution for collinear points.
 *  problem with max and min and comparing slopes
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>();
    private int numberOfFoundSegments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
        int length = points.length;
        Point[] pointsCopy = new Point[length];
        System.arraycopy(points, 0, pointsCopy, 0, length);
        Arrays.sort(pointsCopy);
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (pointsCopy[i].compareTo(pointsCopy[j]) == 0)
                    throw new IllegalArgumentException();
                for (int k = j + 1; k < length; k++) {
                    if (pointsCopy[i].compareTo(pointsCopy[k]) == 0 ||
                            pointsCopy[j].compareTo(pointsCopy[k]) == 0) {
                        throw new IllegalArgumentException();
                    }
                    for (int m = k + 1; m < length; m++) {
                        if (pointsCopy[i].compareTo(pointsCopy[m]) == 0 ||
                                pointsCopy[j].compareTo(pointsCopy[m]) == 0 ||
                                pointsCopy[k].compareTo(pointsCopy[m]) == 0) {
                            throw new IllegalArgumentException();
                        }
                        if (Double.compare(pointsCopy[i].slopeTo(pointsCopy[j]), pointsCopy[i]
                                .slopeTo(pointsCopy[k])) == 0 &&
                                Double.compare(pointsCopy[i].slopeTo(pointsCopy[k]), pointsCopy[i]
                                        .slopeTo(pointsCopy[m])) == 0) {
                            foundSegments.add(new LineSegment(pointsCopy[i], pointsCopy[m]));
                            numberOfFoundSegments++;
                        }
                    }
                }
            }
        }
    }


    public int numberOfSegments() {
        // the number of line segments
        return numberOfFoundSegments;
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] lines = new LineSegment[numberOfFoundSegments];
        for (int i = 0; i < numberOfFoundSegments; i++) {
            lines[i] = foundSegments.get(i);
        }
        return lines;
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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

// Test 13: throws an exception if either the constructor argument is null
//         or any entry in array is null
//         * argument is null
//         * Point[] of length 10, number of null entries = 1
//         - constructor throws wrong exception
//         - constructor throws a java.lang.NullPointerException
//         - constructor should throw a java.lang.IllegalArgumentException
//         10
//         10050 15167
//         20328  4704
//         31941 31601
//         5688 19957
//         15255  4413
//         12315 10911
//         null
//         4783  2573
//         21562 21018
//         29494  4222
//
//         * Point[] of length 10, number of null entries = 10
//         - constructor throws wrong exception
//         - constructor throws a java.lang.NullPointerException
//         - constructor should throw a java.lang.IllegalArgumentException
//         10
//         null
//         null
//         null
//         null
//         null
//         null
//         null
//         null
//         null
//         null
//
//         * Point[] of length 4, number of null entries = 1
//         - constructor throws wrong exception
//         - constructor throws a java.lang.NullPointerException
//         - constructor should throw a java.lang.IllegalArgumentException
//         4
//         null
//         6791  2893
//         13783 24225
//         22735  8239
//
//         * Point[] of length 3, number of null entries = 1
//         - constructor throws wrong exception
//         - constructor throws a java.lang.NullPointerException
//         - constructor should throw a java.lang.IllegalArgumentException
//         3
//         2777 11026
//         30947 10324
//         null
//
//         * Point[] of length 2, number of null entries = 1
//         - constructor throws wrong exception
//         - constructor throws a java.lang.NullPointerException
//         - constructor should throw a java.lang.IllegalArgumentException
//         2
//         22354 29088
//         null
//
//         * Point[] of length 1, number of null entries = 1
//         ==> FAILED
