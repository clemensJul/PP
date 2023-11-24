public class GenericMap<K, V> {
    private final GenericList<Entry<K, V>> buckets;

    // todo soll ma die anderen methoden löschen die nicht gebraucht werden?
    public GenericMap() {
        this.buckets = new GenericList<>();
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
        buckets.add(newNode);
    }

    public V getOrDefault(K key, V defaultReturn) {
        V value = get(key);
        return value == null ? defaultReturn : value;
    }

    public V get(K key) {
        for (Entry<K, V> entry : buckets) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        // find item in map
        for (Entry<K, V> entry : buckets) {
            if (entry.key.equals(key)) {
                buckets.remove(entry);
            }
        }
    }

    public int size() {
        return buckets.size();
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
        int i = 0;
        for (K k : keys()) {
            if(key.equals(k)) {
                return i;
            }
            i++;
        }

        return i;
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
