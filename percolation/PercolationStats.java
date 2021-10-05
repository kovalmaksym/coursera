package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

//    private final int n;
    private static final double X = 1.96;
    private final int t;
    private final double[] grids;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n & t should be greater than 0");
//        this.n = n;
        this.t = trials;
        grids = new double[t];

        for (int i = 0; i < t; i++) {

            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                openRandomPoint(percolation, n);
            }
            grids[i] = (double) percolation.numberOfOpenSites() / (n*n);


        }


    }

    private void openRandomPoint(Percolation percolation, int n) {

        int row = StdRandom.uniform(1, n+1);
        int col = StdRandom.uniform(1, n+1);
        percolation.open(row, col);

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(grids);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(grids);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return  mean() + ((X * stddev()) / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() - ((X * stddev()) / Math.sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);

        System.out.printf("mean() = %f\n", stats.mean());
        System.out.printf("stddev() = %f\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]", stats.confidenceLo(), stats.confidenceHi());
    }

}
