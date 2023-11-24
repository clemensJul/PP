import java.util.Iterator;
import java.util.NoSuchElementException;

// todo soll ma die anderen methoden löschen die nicht gebraucht werden?
public class GenericList<T> implements Iterable<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private int size;

    public GenericList() {
        this.head = null;
        this.size = 0;
    }

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

    public void removeAt(int index) {
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

    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = item;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node<T> current = head;
            Node<T> prev = null;
            boolean lastReturned = false;

            @Override
            public boolean hasNext() {
                return current != null;
            }

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
