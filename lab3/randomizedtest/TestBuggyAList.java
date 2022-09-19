package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        correct.addLast(4); correct.addLast(5); correct.addLast(6);
        broken.addLast(4); broken.addLast(5); broken.addLast(6);

        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> Lb = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                Lb.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                assertEquals(L.size(), Lb.size());
                int size = L.size();
                System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                // removeLast
                if (L.size() > 0) {
                    int item = L.removeLast();
                    int itemb = Lb.removeLast();
                    assertEquals(item, itemb);
                    System.out.println("removeLast(" + item + ")");
                }
            } else if (operationNumber == 3) {
                // getLast
                if (L.size() > 0) {
                    assertEquals(L.getLast(), Lb.getLast());
                    int item = L.getLast();
                    System.out.println("last: " + item);
                }
            }
        }
    }
}
