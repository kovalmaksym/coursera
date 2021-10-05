import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class FastCollinearPoints {

    private int collinearSegments = 0;
    private final LinkedList<LineSegment> segments = new LinkedList<LineSegment>();

    public FastCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) if (p == null) throw new IllegalArgumentException();

        LinkedList<Point> pts = new LinkedList<Point>(List.of(points));
        Collections.sort(pts);
        findDuplicates(pts);
        findCollinear(pts);

    }    // finds all line segments containing 4 or more points

    private void findCollinear(LinkedList<Point> pts) {

        LinkedList<LinkedList<Point>> lines = new LinkedList<LinkedList<Point>>();

        for (int i = 0; i < pts.size(); i++) {

            Point p = pts.get(i);                                                // current point "p"
            LinkedList<Point> ptsBySlopesWithP = new LinkedList<Point>(pts);
            ptsBySlopesWithP.remove(i);                                          // delete current point
            ptsBySlopesWithP.sort(p.slopeOrder());                               // sort by slope to current point

            Point q;
            double prevSlope = 0.0;
            double currentSlope = 0.0;
            LinkedList<Point> line = new LinkedList<Point>();

            for (int j = 0; j < ptsBySlopesWithP.size(); j++) {

                q = ptsBySlopesWithP.get(j);
                currentSlope = p.slopeTo(q);
                if (j == 0) {

                    line.add(p);
                    line.add(q);
                    prevSlope = currentSlope;

                } else if (currentSlope == prevSlope) {

                    line.add(q);

                } else {

                    if (line.size() >= 4) {
                        lines.add(line);
                    }

                    line = new LinkedList<Point>();
                    line.add(p);
                    line.add(q);
                    prevSlope = currentSlope;

                }


            }


        }


        findSubsegments(lines);
        createLineSegments(lines);
//        for (int i = 0; i < lines.size(); i++) {
//            StdOut.println(lines.get(i).toString());
//        }

    }

    private void createLineSegments(LinkedList<LinkedList<Point>> lines) {

        Point start;
        Point end;

        for (LinkedList<Point> line : lines) {
            start = line.getFirst();
            end = line.getLast();
            segments.add(new LineSegment(start, end));
        }

    }

    private void findSubsegments(LinkedList<LinkedList<Point>> lines) {

        Point start;
        Point end;
        Point prevEnd = null;

        double currentSlope = 0.0;
        double prevSlope = 0.0;

        for (int i = 0; i < lines.size(); i++) {

            start = lines.get(i).getFirst();
            end = lines.get(i).getLast();
            currentSlope = start.slopeTo(end);

            if (i == 0) {

//                prevSlope = start.slopeTo(end);

            } else if (currentSlope == prevSlope) {

                if (hasSamePoint(lines.get(i), prevEnd)) {
                    lines.remove(i);
                    i = i - 1;
                    continue;
                }

            }

            prevSlope = currentSlope;
            prevEnd = end;

        }


    }

    private boolean hasSamePoint(LinkedList<Point> line, Point p) {

        for (Point x : line) {
            if (x.compareTo(p) == 0) {
                return true;
            }
        }
        return false;

    }

    private void findDuplicates(LinkedList<Point> pts) {

        for (int n = 0; n < pts.size()-1; n++) {
            for (int h = n+1; h < pts.size(); h++) {
                if ((pts.get(n).compareTo(pts.get(h))) == 0) throw new IllegalArgumentException();
            }
        }

    }

    public int numberOfSegments() { return collinearSegments; }      // the number of line segments
    public LineSegment[] segments() { return segments.toArray(new LineSegment[segments.size()]); }               // the line segments

    public static void main(String[] args) {

        // read the n points from a file
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
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.GRAY);

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        StdOut.println(Double.POSITIVE_INFINITY==Double.POSITIVE_INFINITY);
    }

}
