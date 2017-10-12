package Rx1;

import rx.Scheduler;
import rx.SingleSubscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.Observable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by fkruege on 6/26/17.
 */
public class FlatMapScheduling {

    public static void main(String[] args) throws InterruptedException {

        Scheduler immediate = Schedulers.immediate();
//        Schedulers.from(Thread.ex)


        CountDownLatch latch = new CountDownLatch(1);

        rx.Observable.just(1)
                .flatMap((Func1<Integer, rx.Observable<Integer>>) integer -> rx.Observable.just(integer * 10)
                                .doOnNext(integer1 -> System.out.println("Running on " + Thread.currentThread().getName() + " " + integer1))
//                        .subscribeOn(Schedulers.computation()))
                )
                .map((Func1<Integer, Integer>) integer -> {
                    System.out.println("Running on " + Thread.currentThread().getName() + " " + (integer + 5));
                    return integer;

                })
                .toSingle()
                .subscribeOn(Schedulers.computation())
                                .doOnSuccess(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Succes Running on " + Thread.currentThread().getName() + " " + (integer));

                    }
                })
                .observeOn(Schedulers.io())

                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        latch.countDown();
                    }
                })
                .subscribe(new SingleSubscriber<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
//                        System.out.println("Succes Running on " + Thread.currentThread().getName() + " " + (integer));
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.out.println("Error : " + Thread.currentThread().getName() + " " + error.getMessage());
                    }
                });


        latch.await();


    }
}
