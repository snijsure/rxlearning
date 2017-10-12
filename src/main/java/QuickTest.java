import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fkruege on 9/6/17.
 */
public class QuickTest {


    public static void main(String[] args) throws InterruptedException {
        List<Object> objects = Collections.synchronizedList(new ArrayList<>());

    }

    public static boolean isPalindrome(String str) {
        int first = 0;
        int second = str.length() - 1;
        while (first <= second) {
            char firstChar = str.charAt(first);
            char secondChar = str.charAt(second);
            if (!isAlpha(firstChar)) {
                first++;
                continue;
            } else if (!isAlpha(secondChar)) {
                second--;
                continue;
            }
            if (firstChar != secondChar) {
                return false;
            }
            first++;
            second--;
        }
        return true;
    }

    public static boolean isAlpha(char c) {
        return Character.isAlphabetic(c);
    }


}
