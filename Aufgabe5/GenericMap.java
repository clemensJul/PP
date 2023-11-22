public class GenericMap<K, V> {
    private final GenericList<Entry<K, V>> buckets;
    private int size;

    public GenericMap() {
        this.buckets = new GenericList<>();
        this.size = 0;
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> entry = buckets.get(index);

        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }

        Entry<K, V> newNode = new Entry<>(key, value);
        newNode.next = buckets.get(index);
        buckets.set(index, newNode);
        size++;
    }

    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = buckets.get(index);

        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }

        return null;
    }

    public void remove(K key) {
        int index = getIndex(key);
        Entry<K, V> prev = null;
        Entry<K, V> entry = buckets.get(index);

        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prev == null) {
                    buckets.set(index, entry.next);
                } else {
                    prev.next = entry.next;
                }
                size--;
                return;
            }
            prev = entry;
            entry = entry.next;
        }
    }

    public int size() {
        return size;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.size();
    }

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
