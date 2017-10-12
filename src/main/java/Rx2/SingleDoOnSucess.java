package Rx2;

import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;

/**
 * Created by fkruege on 7/25/17.
 */
public class SingleDoOnSucess {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Single.just("hello world")
                .doOnSuccess(s -> System.out.println(Thread.currentThread().getName() + "doOnSuccess"))
                .doAfterSuccess(s -> System.out.println(Thread.currentThread().getName() + "doAfterSuccess"))
                .subscribeOn(Schedulers.newThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println(Thread.currentThread().getName() + "doFinally");
                        latch.countDown();
                    }
                })
//                .doOnDispose(() -> {
//                    System.out.println(Thread.currentThread().getName() + "doOnDispose");
//                    latch.countDown();
//                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println(Thread.currentThread().getName() + "doAfterTerminate");
                        latch.countDown();
                    }
                })
                .subscribe();
        latch.await();
    }
}
