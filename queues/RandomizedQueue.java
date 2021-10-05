package queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private final Deque<Item> randDeque;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randDeque = new Deque<>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return randDeque.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size() {
        return randDeque.size();
    }

    // add the item
    public void enqueue(Item item) {
        randDeque.addFirst(item);
    }

    // remove and return a random item
    public Item dequeue() {

//        int size = randDeque.size();
//
//        int i = StdRandom.uniform(0,size);
//
//        for (Item item : randDeque) {
//
//            i--;
//            if (i == 0) {
//                return randDeque.
//            }
//
//        }

        int choose = StdRandom.uniform(0, 2);
        if (choose == 0) {
            return randDeque.removeFirst();
        } else {
            return randDeque.removeLast();
        }

    }

    // return a random item (but do not remove it)
    public Item sample() {

        Item result;
        int choose = StdRandom.uniform(0, 2);
        if (choose == 0) {
            result = randDeque.removeFirst();
            randDeque.addFirst(result);
            return result;

        } else {
            result = randDeque.removeLast();
            randDeque.addLast(result);
            return result;
        }

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return randDeque.iterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        //TODO
    }

}