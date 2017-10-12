package Rx2;


import hu.akarnokd.rxjava2.schedulers.BlockingScheduler;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;

/**
 * Created by fkruege on 7/29/17.
 */
public class BlockingSchedulerTest {


    public static void main(String[] args) throws InterruptedException {
        BlockingScheduler scheduler = new BlockingScheduler();

        CountDownLatch latch = new CountDownLatch(1);


        Single.just(1)
                .doOnSuccess(integer -> System.out.println(Thread.currentThread().getName() + " doOnSuccess: " + integer))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doAfterTerminate(() -> {
                    System.out.println(Thread.currentThread().getName() + " doOnDispose: ");
                    latch.countDown();
                })
                .subscribe(integer -> System.out.println(Thread.currentThread().getName() + " subscribe: " + integer));

        latch.await();

    }


}
