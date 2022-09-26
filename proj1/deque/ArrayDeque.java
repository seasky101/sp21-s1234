package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int first;
    private int last;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = 1;
        last = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i += 1) {
            a[i] = get(i);
        }
        items = a;
        first = 0;
        last = size - 1;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(2 * size);
        }

        if (first == 0) {
            first = items.length - 1;
        } else {
            first -= 1;
        }
        items[first] = item;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(2 * size);
        }

        if (last == items.length - 1) {
            last = 0;
        } else {
            last += 1;
        }
        items[last] = item;
        size += 1;
    }

    @Override
    public int size() { return size; }

    @Override
    public void printDeque() {
        for (int i = 0; i < size(); i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && size - 1 < items.length * 0.25) {
            resize(size / 2);
        }

        T returnItem = items[first];
        items[first] = null;
        size -= 1;
        if (first == items.length - 1) {
            first = 0;
        } else {
            first += 1;
        }
        return returnItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (items.length >= 16 && size - 1 < items.length * 0.25) {
            resize(size / 2);
        }

        T returnItem = items[last];
        items[last] = null;
        size -= 1;
        if (last == 0) {
            last = items.length - 1;
        } else {
            last -= 1;
        }
        return returnItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(first + index) % items.length];
    }

    private class ADIterator implements Iterator<T> {
        private int wizPos;

        ADIterator() {
            wizPos = 0;
        }

        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        @Override
        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() { return new ADIterator(); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        /*
        if (obj instanceof Deque d) {
            if (this.size != d.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i += 1) {
                if (!this.get(i).equals(d.get(i))) {
                    return false;
                }
            }
            return true;
        }
         */
        if (obj instanceof Deque) {
            if (this.size != ((Deque) obj).size()) {
                return false;
            }
            for (int i = 0; i < this.size; i += 1) {
                if (!this.get(i).equals(((Deque) obj).get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /*
    public static void main(String[] args) {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(3); lld.addLast(7); lld.addLast(9);

        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(3); ad1.addLast(7); ad1.addLast(9);

        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        ad2.addLast(3); ad2.addLast(5); ad2.addLast(9);

        System.out.println(ad1.equals(lld)); System.out.println(ad1.equals(ad2));

        for (int item: ad1) {
            System.out.println(item);
        }

        for (int i = 0; i < 10; i += 1) {
            ad1.addLast(i);
        }
    }
     */
}
