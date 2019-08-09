/* *****************************************************************************
 *  Name: H. H.
 *  Date: 3/1/19
 *  Description: Randomized Queue class with iterator and made with resizing
 *  arrays
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;
    private int last; // index of next available slot

    public RandomizedQueue() {
        // construct an empty randomized queue
        // with an ugly but necessary cast
        queue = (Item[]) new Object[4];
        size = 0;
        last = 0;
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return size;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) throw new IllegalArgumentException();
        queue[last] = item;
        last++;
        size++;
        if (size == queue.length) resize(queue.length * 2);
    }

    public Item dequeue() {
        // remove and return a random item
        if (size == 0) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(0, last);
        Item temp = queue[randIndex];
        queue[randIndex] = queue[last - 1];
        queue[last - 1] = null;
        last--;
        size--;
        if (size <= queue.length / 4) resize(queue.length / 2);
        return temp;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
        last = size;
    }

    public Item sample() {
        // return a random item (but do not remove it)
        if (size == 0) throw new NoSuchElementException();
        return queue[StdRandom.uniform(0, last)];
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private int i;
        private final Item[] iterQueue;

        public RandomArrayIterator() {
            iterQueue = (Item[]) new Object[last];
            for (int j = 0; j < last; j++) {
                iterQueue[j] = queue[j];
            }
            StdRandom.shuffle(iterQueue, 0, last);
        }

        public boolean hasNext() {
            return i < last;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return iterQueue[i++];
        }
    }

    public static void main(String[] args) {
        // unit testing (optional)
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        Iterator<Integer> q1 = q.iterator();
        StdOut.println("q1");
        while (q1.hasNext()) {
            StdOut.println(q1.next());
        }
        Iterator<Integer> q2 = q.iterator();
        StdOut.println("q2");
        while (q2.hasNext()) {
            StdOut.println(q2.next());
        }
    }

}
