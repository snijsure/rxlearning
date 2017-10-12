package Rx2;

import io.reactivex.Observable;

/**
 * Created by fkruege on 7/26/17.
 */
public class NullTest {


    public static void main(String[] args) throws InterruptedException {

        Observable.fromCallable(() -> null)
                .subscribe(System.out::println, Throwable::printStackTrace);
    }
}
