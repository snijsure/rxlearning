package Rx2;


import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by fkruege on 7/14/17.
 */
public class RelayTest {

    public static void main(String[] argsA) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(5);
        PublishRelay<Integer> publishRelay = PublishRelay.create();

        publishRelay
                .observeOn(Schedulers.computation())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        int sleep = new Random().nextInt(100);
//                        int sleep = (integer % 5) ;

                        System.out.println("In map: " + Thread.currentThread().getName() + " Integer consumed: " + integer + " sleeping for " + sleep);
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        return integer;
                    }
                })
//                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
//                    @Override
//                    public ObservableSource<?> apply(Integer integer) throws Exception {
//                        return null;
//                    }
//                })
//                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        int sleep = new Random().nextInt(100);
//                        int sleep = (integer % 5) ;

                        System.out.println("OnNext: " + Thread.currentThread().getName() + " Integer consumed: " + integer + " sleeping for " + sleep);
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//
//        publishRelay.map(integer -> {
//
//            System.out.println(Thread.currentThread().getName() + " Integer consumed: " + integer);
//            Thread.sleep(new Random().nextInt(500));
//            latch.countDown();
//            return integer;
//        })
//
//                .subscribeOn(Schedulers.computation())
//                .subscribe();

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("Back to normal: " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    int sleep = new Random().nextInt(100);
//                    createSubscribe(i, sleep);
                    publishRelay.accept(i);

                }
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribe();

        latch.await();

    }

    private static void createSubscribe(int integer, int sleep) {
        Observable.timer(sleep, TimeUnit.MILLISECONDS, Schedulers.computation())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println(Thread.currentThread().getName() + " Integer consumed: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
