package Rx2;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class MaybeZipTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Main Thread: " + Thread.currentThread().getName());

        CountDownLatch latch = new CountDownLatch(1);
//        Scheduler main = Schedulers.

        Scheduler schedulerOne = Schedulers.single();
        Scheduler schedulerTwo = Schedulers.newThread();

        Maybe<String> maybeSchedulerOneA = Maybe.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("maybeSchedulerOneA Sleeping: " + Thread.currentThread().getName());
                Thread.sleep(100);
                System.out.println("maybeSchedulerOneA Done Sleeping: " + Thread.currentThread().getName());
                return "MaybeA";
            }
        }).subscribeOn(schedulerOne);


        Maybe<String> maybeSchedulerOneB = Maybe.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("maybeSchedulerOneB Sleeping: " + Thread.currentThread().getName());
                Thread.sleep(100);
                System.out.println("maybeSchedulerOneB Done Sleeping: " + Thread.currentThread().getName());
                return "MaybeB";
            }
        }).subscribeOn(schedulerOne);


        Maybe<String> maybeSchedulerTwoA = Maybe.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("maybeSchedulerTwoA Sleeping: " + Thread.currentThread().getName());
                Thread.sleep(100);
                System.out.println("maybeSchedulerTwoA Done Sleeping: " + Thread.currentThread().getName());
                return "MaybeB";
            }
        }).subscribeOn(schedulerTwo);

        Maybe
                .zip(maybeSchedulerOneA, maybeSchedulerTwoA, maybeSchedulerOneB, new Function3<String, String, String, String>() {
                    @Override
                    public String apply(String s, String s2, String s3) throws Exception {
                        return "Completed zipping";
                    }
                })
//                .observeOn(main)
                .subscribe(new MaybeObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("OnSubscribe");
                    }

                    @Override
                    public void onSuccess(String s) {
                        System.out.println("OnSuccess: " + s + " " + Thread.currentThread().getName());
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.getMessage() + " " + Thread.currentThread().getName());
                        latch.countDown();

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete" + " " + Thread.currentThread().getName());
                        latch.countDown();
                    }
                });

        latch.await();


    }
}
