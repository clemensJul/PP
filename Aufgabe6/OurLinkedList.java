import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic linked list implementation.
 */
@CodedBy("Raphael")
@SignatureAndAssertions(

)
public class OurLinkedList implements Iterable {
    @CodedBy("Raphael")
    private static class Node {
        Object data;
        Node next;

        @CodedBy("Raphael")
        @SignatureAndAssertions(
                postconditions = "Initializes a new Node object"
        ) Node(Object data) {
            this.data = data;
        }
    }

    private Node head;
    private int size;

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Initializes a new OurLinkedList object"
    )
    public OurLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Adds an item to the end of the list"
    )
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

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns true, if the item is in the list."
    )
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

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "the item at the specified index, or null if the index is out of bounds"
    )
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

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "the item at the specified index, or null if the index is out of bounds"
    )
    public Object get(Object item) {
        Node current = head;
        for (int i = 0; i < this.size; i++) {
            if (current.data.equals(item)) return current.data;
            current = current.next;
        }
        return null;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Removes the first occurrence of a specified item from the lis. Returns true if the item was removed, false if the item was not found"
    )
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

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            preconditions = "index must be smaller than the list size",
            postconditions = "removes item at specified index - throws IndexOutOfBoundsException"
    )
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

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            preconditions = "index must be smaller than the list size",
            postconditions = "sets object into list - throws IndexOutOfBoundsException"
    )
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

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns the size of the list."
    )
    public int size() {
        return size;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "returns an iterator over the elements in the list."
    )
    @Override
    public Iterator iterator() {
        return new Iterator() {
            Node current = head;
            Node prev = null;
            boolean lastReturned = false;

            @CodedBy("Raphael")
            @SignatureAndAssertions(
                    postconditions = "Checks if the iterator has a next element"
            )
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @CodedBy("Raphael")
            @SignatureAndAssertions(
                    postconditions = "retrieves the next element n the iteration - throws new NoSuchElementException"
            )
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

            @CodedBy("Raphael")
            @SignatureAndAssertions(
                    postconditions = "removes last returned element from the list - throws new illegalStateException"
            )
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
