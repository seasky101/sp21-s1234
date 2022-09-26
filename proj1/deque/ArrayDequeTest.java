package deque;

public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(3); ad1.addLast(7); ad1.addLast(9);

        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        ad2.addLast(3); ad2.addLast(5); ad2.addLast(9);

        System.out.println(ad1.equals(ad2));

        for (int i = 0; i < 20; i += 1) {
            ad1.addLast(i);
        }

        for (int i = 0; i < 18; i += 1) {
            ad1.removeLast();
        }
    }
}
