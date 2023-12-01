import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic linked list implementation.
 */
public class OurLinkedList implements Iterable {


    /**
     * Node class for holding data and references in the list.
     */
    private static class Node {
        Object data;
        Node next;

        /**
         * Node constructor.
         *
         * @param data the data to be stored in the node
         */
        Node(Object data) {
            this.data = data;
        }
    }

    private Node head;
    private int size;

    /**
     * Constructs an empty GenericList.
     */
    public OurLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds an item to the end of the list.
     *
     * @param item the item to add
     */
    public void add(Object item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Checks if the list contains a specific item.
     *
     * @param item the item to check for
     * @return true if the item is found, false otherwise
     */
    public boolean contains(Object item) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Retrieves an item at a specified index.
     *
     * @param index the index of the item to retrieve
     * @return the item at the specified index, or null if the index is out of bounds
     */
    public Object get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Removes the first occurrence of a specified item from the list.
     *
     * @param item the item to remove
     * @return true if the item was removed, false if the item was not found
     */
    public boolean remove(Object item) {
        Node current = head;
        Node prev = null;

        while (current != null) {
            if (current.data.equals(item)) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Removes an item at the specified index.
     *
     * @param index the index of the item to remove
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node current = head;
            Node prev = null;
            for (int i = 0; i < index; i++) {
                prev = current;
                current = current.next;
            }
            prev.next = current.next;
        }
        size--;
    }

    /**
     * Sets the item at the specified index to a new value.
     *
     * @param index the index of the item to set
     * @param item  the new item value
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void set(int index, Object item) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = item;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator
     */
    @Override
    public Iterator iterator() {
        return new Iterator() {
            Node current = head;
            Node prev = null;
            boolean lastReturned = false;

            /**
             * Checks if the iterator has a next element.
             * @return true if there is a next element, false otherwise
             */
            @Override
            public boolean hasNext() {
                return current != null;
            }

            /**
             * Retrieves the next element in the iteration.
             * @return the next element
             * @throws NoSuchElementException if there are no more elements
             */
            @Override
            public Object next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = true;
                Object data = current.data;
                prev = current;
                current = current.next;
                return data;
            }

            /**
             * Removes the last returned element from the list.
             * @throws IllegalStateException if next() has not been called
             */
            @Override
            public void remove() {
                if (!lastReturned) {
                    throw new IllegalStateException("next() has not been called");
                }
                if (prev == null) {
                    head = head.next;
                } else {
                    prev.next = current;
                }
                lastReturned = false;
                size--;
            }
        };
    }
}
