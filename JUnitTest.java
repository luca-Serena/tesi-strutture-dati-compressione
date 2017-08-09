
/**
 *
 * @author utente
 */

import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;
import org.junit.*;

public class JUnitTest {

    String s = "pipistrello";
    Node root;// = new Wavelet_Tree(s);
    ArrayList <Node> alph = new ArrayList();

    @Before
    public void setUp() {
        root = new Node();   
        alph = root.createAlphabet(s.toCharArray());
        root.createTree(s,root,alph);
    }

    @Test
    public void test1() {
        int first =-1, second =-1, third=-1, fourth = -1;
        if (root != null) {                                                      //count all 11 nodes of the tree
            first = root.rank(root, 5, 'i', alph);
            second = root.rank(root, 1, 's', alph);
            third = root.rank (root, s.length(), 'p', alph);
            fourth = root.rank (root, 8, 'u', alph);
        }
        assertEquals(2, first);
        assertEquals (0, second);
        assertEquals (-1, third);
        assertEquals (-1, fourth);
    }

    @Test
    public void test2() {
        int first =-1, second = -1, third =-1;
        if (root != null) {
            first = root.select (root, 'i', 3, alph);
            second = root.select(root, 'i', 2, alph);
            third = root.select(root, 'o', 1, alph );
        }
        assertEquals(first, 0);
        assertEquals(second,4);
        assertEquals (third, 11);
    } 
}
