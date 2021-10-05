//package queues;// import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node
    {
        Item item;
        Node next;
        Node previous;

    }

    private int size;
    private Node first;
    private Node last;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) throw new IllegalArgumentException("you're adding null item");

        Node newOne = new Node();

        newOne.item = item;
        newOne.next = null;
        newOne.previous = null;

        if (isEmpty()) {
            first = newOne;
            last = newOne;
        } else {
            first.previous = newOne;
            newOne.next = first;
            first = newOne;
        }
        this.size++;
//
////        Node oldFirst = current;
////        current = new Node();
////        current.item = item;
////        current.next = oldFirst;
////        first = current;

    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) throw new IllegalArgumentException("you're adding null item");
        Node newOne = new Node();

        newOne.item = item;
        newOne.next = null;
        newOne.previous = null;

        if (isEmpty()) {
            first = newOne;
            last = newOne;
        } else {
            last.next = newOne;
            newOne.previous = last;
            last = newOne;
        }

        this.size++;

//        Node oldLast = last;
//        last = new Node();
//        last.item = item;
//        last.next = null;
//        if (isEmpty()) {
//            first = last;
//        } else {
//            oldLast.next = last;
//        }
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) throw new NoSuchElementException("no such element in deque");
        Node result = first;
        if (first.next != null) {
            first = first.next;
        }
        this.size--;


        first.previous = null;
//        result.next = null;
//        result.previous = null;

        return result.item;
    }

    // remove and return the item from the back
    public Item removeLast() {

        if (isEmpty()) throw new NoSuchElementException("no such element in deque");
        Node result = last;
        if (last.previous != null) {
            last = last.previous;
        }
        this.size--;

        last.next = null;
//        result.next = null;
//        result.previous = null;

        return result.item;
    }

    private class NiceIterator implements Iterator<Item> {
        Node current = first;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (isEmpty()) throw new NoSuchElementException("end of iterator");
            Item item = current.item;
            if (item == null) throw new NoSuchElementException("end of iterator");
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("unsupported operation :(");
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new NiceIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
//        queues.Deque<Integer> deque = new queues.Deque<Integer>();

//        deque.isEmpty();



    }
}
