package deque;

public interface Deque<T> {
    void addFirst(T item);

    void addLast(T item);

    default boolean isEmpty() {
        return size() == 0;
    }

    int size();

    default void printDeque() {
        for (int i = 0; i < size(); i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    T removeFirst();

    T removeLast();

    T get(int index);
}
