package deque;

import java.util.Comparator;

public class IntegerComparator<T> implements Comparator<T> {
    @Override
    public int compare(Object o1, Object o2) {
        return (int) o1 - (int) o2;
    }
}
