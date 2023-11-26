/**
 * A generic map implementation that associates keys with values.
 *
 * @param <K> The type of keys in the map.
 * @param <V> The type of values in the map.
 */
public class GenericMap<K, V> {
    private final GenericList<Entry<K, V>> buckets;

    /**
     * Constructs a new GenericMap.
     */
    public GenericMap() {
        this.buckets = new GenericList<>();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     */
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

    /**
     * Returns the value to which the specified key is mapped, or a default value if the key is not present in the map.
     *
     * @param key           The key whose associated value is to be returned.
     * @param defaultReturn The default value to return if the key is not found.
     * @return The value to which the specified key is mapped, or the default value if the key is not present.
     */
    public V getOrDefault(K key, V defaultReturn) {
        V value = get(key);
        return value == null ? defaultReturn : value;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if the key is not present in the map.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value to which the specified key is mapped, or null if the key is not present.
     */
    public V get(K key) {
        for (Entry<K, V> entry : buckets) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key The key whose mapping is to be removed from the map.
     */
    public void remove(K key) {
        // find item in map
        for (Entry<K, V> entry : buckets) {
            if (entry.key.equals(key)) {
                buckets.remove(entry);
            }
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return The number of key-value mappings in this map.
     */
    public int size() {
        return buckets.size();
    }

    /**
     * Returns a list of keys present in the map.
     *
     * @return A list containing all keys present in the map.
     */
    public GenericList<K> keys() {
        GenericList<K> keys = new GenericList<>();
        for (Entry<K, V> entry : buckets) {
            keys.add(entry.getKey());
        }

        return keys;
    }

    /**
     * Returns a list of values present in the map.
     *
     * @return A list containing all values present in the map.
     */
    public GenericList<V> values() {
        GenericList<V> values = new GenericList<>();
        for (Entry<K, V> entry : buckets) {
            values.add(entry.getValue());
        }

        return values;
    }

    /**
     * Returns the index in the buckets for the specified key.
     *
     * @param key The key whose index is to be determined.
     * @return The index of the specified key in the buckets.
     */
    private int getIndex(K key) {
        int i = 0;
        for (K k : keys()) {
            if (key.equals(k)) {
                return i;
            }
            i++;
        }

        return i;
    }

    /**
     * Represents an entry in the GenericMap holding a key-value pair.
     *
     * @param <K> The type of keys in the entry.
     * @param <V> The type of values in the entry.
     */
    public static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        /**
         * Constructs a new entry with the specified key and value.
         *
         * @param key   The key of the entry.
         * @param value The value of the entry.
         */
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key of this entry.
         *
         * @return The key of this entry.
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value of this entry.
         *
         * @return The value of this entry.
         */
        public V getValue() {
            return value;
        }
    }
}
