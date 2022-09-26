package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
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

    @Override
    public void addFirst(T item) {
        TNode first = new TNode(item, sentinel.next, sentinel);
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        TNode last = new TNode(item, sentinel, sentinel.prev);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        TNode p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.print(p.item);
        System.out.println();
    }

    @Override
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

    @Override
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

    @Override
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

    private T getRecursiveHelper(TNode start, int index) {
        if (index == 0) {
            return start.item;
        }
        return getRecursiveHelper(start.next, index - 1);
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private class LLDIterator implements Iterator<T> {
        private int wizPos;

        public LLDIterator() {
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
    public Iterator<T> iterator() {
        return new LLDIterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LinkedListDeque lld) {
            if (this.size != lld.size) {
                return false;
            }
            for (int i = 0; i < this.size; i += 1) {
                if (!this.get(i).equals(lld.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /*
    public static void main(String[] args) {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(3); lld1.addLast(7); lld1.addLast(9);

        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        lld2.addLast(3); lld2.addLast(7); lld2.addLast(9);

        LinkedListDeque<Integer> lld3 = new LinkedListDeque<>();
        lld3.addLast(3); lld3.addLast(5); lld3.addLast(9);

        System.out.println(lld1.equals(lld2)); System.out.println(lld1.equals(lld3));

        for (int item: lld1) {
            System.out.println(item);
        }

        System.out.println(lld1.getRecursive(2));
    }
    */
}
