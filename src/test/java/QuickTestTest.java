import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fkruege on 9/6/17.
 */
public class QuickTestTest {
    @Test
    public void isPalindrome() throws Exception {
        assertTrue(QuickTest.isPalindrome("race!car"));
        assertTrue(QuickTest.isPalindrome("racecar"));
        assertFalse(QuickTest.isPalindrome("cat!"));
//        assertTrue(QuickTest.isPalindrome("Kayak"));
        assertTrue(QuickTest.isPalindrome("civic"));
        assertTrue(QuickTest.isPalindrome("ciic"));
    }

}