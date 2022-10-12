package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    /* Private helper nested class BST. Represents the underlining BST
     * data structure that stores the key-value pairs.
     */
    private static class BST<K, V> {
        K key;
        V value;
        BST left;
        BST right;

        public BST(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private int size = 0;
    private BST T;

    /* Removes all mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        T = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(key, T);
    }

    private boolean containsKeyHelper(K key, BST T) {
        if (T == null) {
            return false;
        }

        if (key.equals(T.key)) {
            return true;
        } else if (key.compareTo((K) T.key) < 0) {
            return containsKeyHelper(key, T.left);
        } else {
            return containsKeyHelper(key, T.right);
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, T);
    }

    private V getHelper(K key, BST T) {
        if (T == null) {
            return null;
        }

        if (key.equals(T.key)) {
            return (V) T.value;
        } else if (key.compareTo((K) T.key) < 0) {
            return getHelper(key, T.left);
        } else {
            return getHelper(key, T.right);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (size() == 0) {
            T = new BST<>(key, value);
        } else {
            putHelper(key, value, T);
        }
        size += 1;
    }

    private BST putHelper(K key, V value, BST T) {
        if (T == null) {
            return new BST<>(key, value);
        }

        if (key.compareTo((K) T.key) < 0) {
            T.left = putHelper(key, value, T.left);
        } else {
            T.right = putHelper(key, value, T.right);
        }
        return T;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
