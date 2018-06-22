package Rx2;


import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.concurrent.Callable;

public class BlockingGetExceptionTest {


    public static void main(String[] args) throws InterruptedException {

        Integer test1 = Single.just(1).blockingGet();

        Throwable throwable1 = Completable.error(new NullPointerException("")).blockingGet();

        Throwable throwable2 = Completable.complete().blockingGet();

        Object objectSingle = Single.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                throw new NullPointerException("");
            }
        }).blockingGet();

        System.out.println("");


    }
}
