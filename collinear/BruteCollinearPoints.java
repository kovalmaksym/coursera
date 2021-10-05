
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {

    private int collinearSegments = 0;
    private final ArrayList<LineSegment> segments = new ArrayList<>(0);

    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) if (p == null) throw new IllegalArgumentException();

        ArrayList<Point> pts = new ArrayList<Point>(List.of(points));
        Collections.sort(pts);
        findDuplicates(pts);

        for (int i = 0; i < pts.size()-3; i++) {

            Point p1 = pts.get(i);

            for (int j = i+1; j < pts.size()-2; j++) {

                Point p2 = pts.get(j);
                double slope = p1.slopeTo(p2);

                for (int k = j+1; k < pts.size()-1; k++) {

                    if (slope == p1.slopeTo(pts.get(k))) {

                        for (int m = k+1; m < pts.size(); m++) {

                            if (slope == p1.slopeTo(pts.get(m))) {

                                segments.add(new LineSegment(p1, pts.get(m)));
                                collinearSegments++;

                            }

                        }

                    }

                }

            }


        }


    }   // finds all line segments containing 4 points

    private void findDuplicates(ArrayList<Point> pts) {

        for (int n = 0; n < pts.size()-1; n++) {
            for (int h = n+1; h < pts.size(); h++) {
                if ((pts.get(n).compareTo(pts.get(h))) == 0) throw new IllegalArgumentException();
            }
        }

    }

    public int numberOfSegments() {
        return collinearSegments;
    }       // the number of line segments

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }               // the line segments

}
