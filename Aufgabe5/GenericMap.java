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

    public V getOrDefault(K key, V defaultReturn) {
        V value = get(key);
        return value == null ? defaultReturn : value;
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

    public GenericList<K> keys() {
        GenericList<K> keys = new GenericList<>();
        for (Entry<K, V> entry : buckets) {
            keys.add(entry.getKey());
        }

        return keys;
    }

    public GenericList<V> values() {
        GenericList<V> values = new GenericList<>();
        for (Entry<K, V> entry : buckets) {
            values.add(entry.getValue());
        }

        return values;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.size();
    }

    public static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
