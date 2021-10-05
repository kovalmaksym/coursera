
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final boolean[] pointsMap;
    private int openPoints;
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF full;
    private final int top;
    private final int bottom;


//    public static void main(String[] args) {
//
//
//        N = 20;
//
//        percolation.Percolation percolation = new percolation.Percolation(N);
//
//        while (true) {
//            percolation.open(StdRandom.uniform(1,N+1),StdRandom.uniform(1,N+1));
//            if (percolation.percolates()) break;
//        }
//
//        StdOut.println("percolation: " + percolation.numberOfOpenSites()/(N*N));
//        percolation.printGrid(N);
//
//    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException("N must be greater then 0");
        this.n = n;
        pointsMap = new boolean[n*n];
        openPoints = 0;
        top = calculateIndex(this.n, this.n) + 1;
        bottom = calculateIndex(this.n, this.n) + 2;
        grid = new WeightedQuickUnionUF(n*n+2);  // +2 (top and bottom virtual points)
        full = new WeightedQuickUnionUF(n*n+1);  // +2 (top and bottom virtual points)

    }

//    private void printGrid(int n) {
//
//
//        for (int i = 0; i < n; i++) {
//
//            for (int j = 0; j < n; j++) {
//
//                StdOut.print(grid[i*n+j]+" ");
//
//            }
//            StdOut.print("\n");
//
//        }
//
//        StdOut.println("openPoints: " + openPoints);
//
//    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (isOpen(row, col)) return;
        int index = calculateIndex(row, col);

        pointsMap[index] = true;
        openPoints++;
//        StdOut.println("open: " + (row) + " " + (col));


        if (row == 1) {

//            if (!wu.connected(index,top)) {
                grid.union(index, top); // connect to the top
                full.union(index, top);
//                StdOut.println("connected [virtual top]: " + (row) +  " " + (col) + " | top");
//            }

        }


        if (row == n) {

//            if (!wu.connected(index,bottom)) {
            grid.union(index, bottom); // connect to the bot
//                StdOut.println("connected [virtual bot]: " + (row) + " " + (col) + " | bot");
//            }

        }

        if (isOnGrid(row-1, col) && isOpen(row-1, col)) {

//            if (!wu.connected(index,calculateIndex(row,col-1))) {
            grid.union(index, calculateIndex(row-1, col)); // connect to the point on top
            full.union(index, calculateIndex(row-1, col)); // connect to the point on top
//                StdOut.println("connected [up]: " + (row) + " " + (col) + " | " + (row) + " " + (col - 1));
//            }

        }

        if (isOnGrid(row+1, col) && isOpen(row+1, col)) {

//            if (!wu.connected(index,calculateIndex(row,col+1))) {
                grid.union(index, calculateIndex(row+1, col)); // connect to the point on bot
                full.union(index, calculateIndex(row+1, col)); // connect to the point on bot
//                StdOut.println("connected [down]: " + (row) + " " + (col) + " | " + (row) + " " + (col + 1));
//            }

        }

        if (isOnGrid(row, col-1) && isOpen(row, col-1)) {

//            if (!wu.connected(index,calculateIndex(row-1,col))) {
                grid.union(index, calculateIndex(row, col-1)); // connect to the point on left
                full.union(index, calculateIndex(row, col-1)); // connect to the point on left
//                StdOut.println("connected [left]: " + (row) + " " + (col) + " | " + (row - 1) + " " + (col));
//            }

        }

        if (isOnGrid(row, col+1) && isOpen(row, col+1)) {
//            if (!wu.connected(index,calculateIndex(row+1,col))) {
                grid.union(index, calculateIndex(row, col+1)); // connect to the point on right
                full.union(index, calculateIndex(row, col+1)); // connect to the point on right
//                StdOut.println("connected [right]: " + (row) + " " + (col) + " | " + (row + 1) + " " + (col));
//            }

        }


    }

    private int calculateIndex(int row, int col) {
        return (this.n * (row-1) + col) - 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArgs(row, col);
        int index = calculateIndex(row, col);
        return (pointsMap[index]);
    }

    private void checkArgs(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IllegalArgumentException("illegal args");
        }
    }

    private boolean isOnGrid(int row, int col) {
        return row > 0 && row <= this.n && col > 0 && col <= this.n;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArgs(row, col);
        int index = calculateIndex(row, col);
        return (full.find(index) == full.find(top));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openPoints;
    }

    // does the system percolate?
    public boolean percolates() {
//        StdOut.println("find: " + (wu.find(top)) + " " + (wu.find(bottom)));
        return grid.find(top) == grid.find(bottom);
    }

}
