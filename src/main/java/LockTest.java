import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fkruege on 8/28/17.
 */
public class LockTest {

    private static Lock loginLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {


    }
    public static void tryInitiation() {
        if (loginLock.tryLock()) {
            try{
//                initiate();
            } finally{
                loginLock.unlock();
            }
        }
    }
}
