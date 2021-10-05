package queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        if (k < 0) throw new IllegalArgumentException("illegal k");
        RandomizedQueue<String> strings = new RandomizedQueue<String>();
        int numberOfStrings = 0;
        while (!StdIn.isEmpty() && numberOfStrings < k) {

            numberOfStrings++;
            String s = StdIn.readString();
            strings.enqueue(s);

        }

        while (k > 0) {
            StdOut.println(strings.dequeue());
            k--;
        }

    }

}
