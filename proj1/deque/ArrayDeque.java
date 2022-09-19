package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int first;
    private int last;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = 1;
        last = 7;
    }

    public void addFirst(T item) {

        if (first == 0) {
            first = items.length - 1;
        } else {
            first -= 1;
        }
        items[first] = item;
        size += 1;
    }

    public void addLast(T item) {

        if (last == items.length - 1) {
            last = 0;
        } else {
            last += 1;
        }
        items[last] = item;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int i = first;
        if (first > last) {
            while (i < items.length) {
                System.out.print(items[i] + " ");
                i += 1;
            }
            i = 0;
            while (i <= last) {
                System.out.print(items[i] + " ");
                i += 1;
            }
        } else {
            while (i <= last) {
                System.out.print(items[i] + " ");
                i += 1;
            }
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
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

    public T removeLast() {
        if (isEmpty()) {
            return null;
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

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(first + index) % items.length];
    }
}
