import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {

    private final int[][] tiles;
    private final int dimension;
    private int iCoordOfZero;
    private int jCoordOfZero;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        if (tiles == null) throw new IllegalArgumentException();
        dimension = tiles.length;
        this.tiles = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    iCoordOfZero = i;
                    jCoordOfZero = j;
                }
            }
        }
    }

    private void findBlank() {

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {
                    iCoordOfZero = i;
                    jCoordOfZero = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                result.append(" ").append(tiles[i][j]);
            }
            result.append("\n");
        }
        result.deleteCharAt(result.lastIndexOf("\n"));
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {

        int outOfPlace = 0;
        int numberOfTile = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++, numberOfTile++) {
                if (tiles[i][j] != numberOfTile) outOfPlace++;
            }
        }
        return outOfPlace-1; }      // -1 because of blank square

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int manhattan = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != (i*dimension+j)+1 && tiles[i][j] != 0) {
                    manhattan += Math.abs(i-(tiles[i][j]-1) / dimension) + Math.abs(j-(tiles[i][j]-1) % dimension);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() { return (this.hamming() == 0); }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {

        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        final Board that = (Board) y;
        if (that.dimension() != this.dimension) return false;
        if (that.hamming() != this.hamming()) return false;
        return (Arrays.deepEquals(that.tiles, this.tiles));
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        LinkedList<Board> neighbours = new LinkedList<>();
        //        twin.swapTiles(iCoord, jCoord, newICoord, newJCoord);
        if (iCoordOfZero < dimension-1) neighbours.add(new Board(tiles).swapTiles(iCoordOfZero, jCoordOfZero, iCoordOfZero + 1, jCoordOfZero));
        if (jCoordOfZero < dimension-1) neighbours.add(new Board(tiles).swapTiles(iCoordOfZero, jCoordOfZero, iCoordOfZero, jCoordOfZero + 1));
        if (jCoordOfZero > 0) neighbours.add(new Board(tiles).swapTiles(iCoordOfZero, jCoordOfZero, iCoordOfZero, jCoordOfZero - 1));
        if (iCoordOfZero > 0) neighbours.add(new Board(tiles).swapTiles(iCoordOfZero, jCoordOfZero, iCoordOfZero - 1, jCoordOfZero));
        return neighbours;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(tiles);
        int col = 0, row = 0;

        if (tiles[col][row] == 0 || tiles[col][row+1] == 0) col++;
        twin.swapTiles(col, row, col, row+1);

        return twin;
    }

    private Board swapTiles(int iCoord, int jCoord, int newICoord, int newJCoord) {

        int tmpTile = tiles[newICoord][newJCoord];
        tiles[newICoord][newJCoord] = tiles[iCoord][jCoord];
        tiles[iCoord][jCoord] = tmpTile;
        findBlank();

        return this;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

//        int[][] grid = { {1, 2, 11, 4}, {5, 14, 7, 9}, {8, 10, 3, 12}, {13, 6, 15, 0} };
//         int[][] grid = { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} };
//         Board brd = new Board(grid);
//        Board twin = brd.twin();
//         Iterable<Board> itr = brd.neighbors();
//         StdOut.println("Original: " + brd);

//        StdOut.println(brd.dimension());
//         StdOut.println(brd.hamming());
//        StdOut.println(brd.isGoal());
//         StdOut.println(brd.manhattan());
//         for (Board x : itr) {
//             StdOut.println(x.toString());
//             StdOut.println(x.hamming());
//             StdOut.println(x.manhattan());
// 
        }
//        StdOut.println(brd.isGoal());
//        StdOut.println(brd.neighbors());
//        StdOut.println(twin.toString());
//        StdOut.println(brd.twin().equals(brd));
//        StdOut.println(twin == brd);

    }
}
