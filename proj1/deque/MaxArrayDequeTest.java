package deque;

public class MaxArrayDequeTest {
    public static void main(String[] args) {
        IntegerComparator<Integer> c1 = new IntegerComparator<>();
        MaxArrayDeque<Integer> d1 = new MaxArrayDeque<>(c1);
        d1.addLast(3); d1.addLast(7); d1.addLast(9); d1.addLast(2);
        System.out.println(d1.max());
        System.out.println(d1.max(c1));
    }
}
