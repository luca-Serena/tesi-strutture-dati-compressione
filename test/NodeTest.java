
import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;
import org.junit.*;
import wavelet.*;

public class NodeTest {

    String s = "pipistrello";
    Node root;
    ArrayList<Character> alph = new ArrayList();

    @Before
    public void setUp() {
        alph = Node.createAlphabet(s.toCharArray());
        root = new Node(s, alph, alph.get(alph.size() / 2));
    }

    @Test
    public void test1() {
        int first = -1, second = -1, third = -1, fourth = -1;
        if (root != null) {                                                  
            first = root.rank(5, 'i');
            second = root.rank(1, 's');
            third = root.rank(s.length(), 'p');
            fourth = root.rank( 8, 'u');
        }
        assertEquals(2, first);
        assertEquals(0, second);
        assertEquals(-1, third);
        assertEquals(-1, fourth);
    }

    @Test
    public void test2() {
        int first = -1, second = -1, third = -1, fourth = -1;
        if (root != null) {
            first = root.select('i', 3);
            second = root.select('l', 2);
            third = root.select('o', 1);
            fourth = root.select('w', 1);
        }
        assertEquals(first, -1);
        assertEquals(second, 10);
        assertEquals(third, 11);
        assertEquals (fourth, -1);
    }

    @Test
    public void test3() {
        s = "roccococomero";
        alph = Node.createAlphabet(s.toCharArray());
        root = new Node(s, alph, alph.get(alph.size() / 2));
        int first = -1, second = -1;
        if (root != null) {
            first = root.rank(6, 'c');
            second = root.select('c', 3);
        }
        assertEquals(first, 3);
        assertEquals(second, 6);
    }

}
