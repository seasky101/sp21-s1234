package deque;

public class LinkedListDeque<T> {
    private class TNode {
        public T item;
        public TNode next;
        public TNode prev;

        public TNode(T i, TNode n, TNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private TNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        TNode first = new TNode(item, sentinel.next, sentinel);
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    public void addLast(T item) {
        TNode last = new TNode(item, sentinel, sentinel.prev);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        TNode p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.print(p.item);
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T i = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return i;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T i = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return i;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }

        int i = 0;
        TNode p = sentinel.next;
        while (i < index) {
            p = p.next;
            i += 1;
        }
        return p.item;
    }
}
