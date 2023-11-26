import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic linked list implementation.
 *
 * @param <T> the type of elements in the list
 */
public class GenericList<T> implements Iterable<T> {

    /**
     * Node class for holding data and references in the list.
     *
     * @param <T> the type of elements in the node
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        /**
         * Node constructor.
         *
         * @param data the data to be stored in the node
         */
        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private int size;

    /**
     * Constructs an empty GenericList.
     */
    public GenericList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds an item to the end of the list.
     *
     * @param item the item to add
     */
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
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
    public boolean contains(T item) {
        Node<T> current = head;
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
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node<T> current = head;
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
    public boolean remove(T item) {
        Node<T> current = head;
        Node<T> prev = null;

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
            Node<T> current = head;
            Node<T> prev = null;
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
    public void set(int index, T item) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = head;
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
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node<T> current = head;
            Node<T> prev = null;
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
            public T next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = true;
                T data = current.data;
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
