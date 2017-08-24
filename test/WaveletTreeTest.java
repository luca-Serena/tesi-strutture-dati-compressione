import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;
import org.junit.*;
import wavelet.*;

public class WaveletTreeTest {

    String s = "pipistrello";
    WaveletTree root;
    ArrayList<Character> alph = new ArrayList();

    @Before
    public void setUp() {
        root = new WaveletTree(s,BitVector.class);
    }

    @Test
    public void test1() throws IndexOutOfBoundsException, CharacterNotFoundException {
        int first = -1, second = -1, third = -1, fourth = -1;
        if (root != null) {                                                  
            first = root.rank(5, 'i');
            second = root.rank(1, 's');
       //   third = root.rank(s.length(), 'p');
       //   fourth = root.rank( 8, 'u');
        }
        assertEquals(2, first);
        assertEquals(0, second);
       //  assertEquals(-1, third);
       // assertEquals(-1, fourth);
    }

    @Test
    public void test2() throws IndexOutOfBoundsException, CharacterNotFoundException{
        int first = -1, second = -1, third = -1, fourth = -1;
        if (root != null) {
           // first = root.select('i', 3);
            second = root.select('l', 2);
            third = root.select('o', 1);
           // fourth = root.select('w', 1);
        }
        //assertEquals(first, -1);
        assertEquals(second, 10);
        assertEquals(third, 11);
        assertEquals (fourth, -1);
    }

    @Test
    public void test3() throws IndexOutOfBoundsException, CharacterNotFoundException{
        s = "roccococomero";
        root = new WaveletTree(s, BitVector.class);
        int first = -1, second = -1, third =-1, fourth = -1;
        if (root != null) {
            first = root.rank(6, 'c');
            second = root.select('c', 3);
            third = root.select ('o',2);
            fourth = root.rank ( 3, 'm');
        }
        assertEquals(first, 3);
        assertEquals(second, 6);
        assertEquals(third, 5);
        assertEquals (fourth, 0);
    }

}
