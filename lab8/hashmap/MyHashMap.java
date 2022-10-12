package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.

 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Haitian Li
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size = 0;
    private double loadFactor = 0.75;

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[16];
        buckets = createTable(16);
    }

    public MyHashMap(int initialSize) {
        buckets = new Collection[initialSize];
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = new Collection[initialSize];
        buckets = createTable(initialSize);
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket

     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)

     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.

     * Override this method to use different data structures as
     * the underlying bucket type

     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects

     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        for (int i = 0; i < tableSize; i += 1) {
            buckets[i] = createBucket();
        }
        return buckets;
    }

    // TODO: Implement the methods of the Map61B Interface below

    @Override
    public void clear() {
        size = 0;
        buckets = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (size() == 0) {
            return false;
        }
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node item: buckets[index]) {
            if (item.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (size() == 0) {
            return null;
        }
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node item: buckets[index]) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        if (containsKey(key)) {
            for (Node item: buckets[index]) {
                if (item.key.equals(key)) {
                    item.value = value;
                    return;
                }
            }
        }

        if ((double) (size()+1) / (double) buckets.length > loadFactor) {
            resize(2 * buckets.length);
        }

        Node item = createNode(key, value);
        index = Math.floorMod(key.hashCode(), buckets.length);
        buckets[index].add(item);
        size += 1;
    }

    private void resize(int capacity) {
        Collection<Node>[] b = new Collection[capacity];
        for (int i = 0; i < capacity; i += 1) {
            b[i] = createBucket();
        }

        for (K key: keySet()) {
            int index = Math.floorMod(key.hashCode(), b.length);
            Node item = createNode(key, get(key));
            b[index].add(item);
        }
        buckets = b;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket: buckets) {
            for (Node item: bucket) {
                keySet.add(item.key);
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
