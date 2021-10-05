import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final Node solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();
        MinPQ<Node> pq = new MinPQ<>(Node.BY_PRIORITY);
        MinPQ<Node> pqTwin = new MinPQ<>(Node.BY_PRIORITY);

        Node searchNode = new Node(initial, 0, null);
        Node searchNodeTwin = new Node(initial.twin(), 0, null);

        pq.insert(searchNode);
        pqTwin.insert(searchNodeTwin);


        while (!searchNode.board.isGoal() && !searchNodeTwin.board.isGoal()) {

            searchNode = pq.delMin();
            for (Board b : searchNode.board.neighbors()) {
                if (searchNode.previous != null && b.equals(searchNode.previous.board)) continue;
                pq.insert(new Node(b, searchNode.moves+1, searchNode));
            }

            searchNodeTwin = pqTwin.delMin();
            for (Board b : searchNodeTwin.board.neighbors()) {
                if (searchNodeTwin.previous != null && b.equals(searchNodeTwin.previous.board)) continue;
                pqTwin.insert(new Node(b, searchNodeTwin.moves+1, searchNodeTwin));
            }


        }
//        solution = searchNode;
        if (searchNode.board.isGoal()) solution = searchNode;
        else solution = null;

    }

    private static class Node {

        private final Board board;
        private final int manhattan;
        private final int moves;
        private final Node previous;
        private static final Comparator<Node> BY_PRIORITY = new OrderPriority();


        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.manhattan = this.board.manhattan();
            this.moves = moves;
            this.previous = previous;
        }
        private static class OrderPriority implements Comparator<Node> {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.manhattan+o1.moves, o2.manhattan+o2.moves);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {

        if (isSolvable()) return solution.moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (!isSolvable()) return null;

        Stack<Board> solutionStack = new Stack<>();
        Node tmp = solution;
        while (tmp != null) {
            solutionStack.push(tmp.board);
            tmp = tmp.previous;
        }
        return solutionStack;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        int[][] tiles = { {1, 2, 3}, {4, 5, 0}, {7, 8, 6} };
//        In in = new In("/home/user/IdeaProjects/coursera_tasks/src/input.txt");
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}