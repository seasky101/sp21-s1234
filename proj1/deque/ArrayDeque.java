package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int first;
    private int last;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = 0;
        last = 0;
    }

    public void addFirst(T item) {

        items[first] = item;
        size += 1;
        if (first == 0) {
            first = items.length - 1;
            last += 1;
        } else {
            first -= 1;
        }
    }

    public void addLast(T item) {

        items[last] = item;
        size += 1;
        if (last == items.length - 1) {
            last = 0;
        } else {
            last += 1;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int i = first;
        if (first + size > items.length) {
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
        return items[index];
    }
}
