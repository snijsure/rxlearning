package Rx1;

import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by fkruege on 7/6/17.
 */
public class SingleDoOnSuccessTest {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        CurrentThreadExecutor currentThreadExecutor = new CurrentThreadExecutor();
        Scheduler mainThread = Schedulers.from(currentThreadExecutor);
        Scheduler immediate = Schedulers.immediate();

        Single.just(5)
                .doOnSuccess(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer + " " + Thread.currentThread().getName());
                    }
                }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        System.out.println(integer + " subscribe on success " + Thread.currentThread().getName());
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });

        latch.await();


    }

    public static class CurrentThreadExecutor implements Executor {
        public void execute(Runnable r) {
            r.run();
        }
    }
}
