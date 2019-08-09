/* *****************************************************************************
 *  Name: H. H.
 *  Date: 2/27/19
 *  Description: Double-ended queue made with linked list
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    public Deque() {
        // construct an empty deque
    }

    private class Node {
        private final Item value;
        private Node next;
        private Node prev;

        Node(Item value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public boolean isEmpty() {
        // is the deque empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) throw new IllegalArgumentException();
        Node newNode = new Node(item, first, null);
        if (first != null) first.prev = newNode;
        first = newNode;
        if (last == null) last = newNode;
        size++;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null) throw new IllegalArgumentException();
        Node newNode = new Node(item, null, last);
        if (last != null) last.next = newNode;
        last = newNode;
        if (first == null) first = newNode;
        size++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (size == 0) throw new NoSuchElementException();
        Item temp = first.value;
        first = first.next;
        if (first == null) last = null;
        else first.prev = null; // exception
        size--;
        return temp;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (size == 0) throw new NoSuchElementException();
        Item temp = last.value;
        last = last.prev;
        if (last == null) first = null;
        else last.next = null; // exception
        size--;
        return temp;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // return an iterator over items in order from front to end
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item value = current.value;
            current = current.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // unit testing (optional)
        Deque<Integer> d = new Deque<>();
        System.out.println(d.size() == 0);
        d.addFirst(1);
        d.addLast(2);
        System.out.println(d.removeFirst() == 1);
    }
}
