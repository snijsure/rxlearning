package Rx2;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by fkruege on 8/18/17.
 */
public class SingleFlatMapScheduer {


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        getSingleInt()
                .flatMapCompletable(Integer -> getCompletable())
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                       System.out.println("Onsubscribe: " + Thread.currentThread().getName()) ;
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete: " + Thread.currentThread().getName()) ;
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


        latch.await();

    }

    private static Single<Integer> getSingleInt() {
       return Single
                .fromCallable(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        System.out.println("Single: " + Thread.currentThread().getName());
                        return 100;
                    }

                });
    }

    private static Completable getCompletable() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("Completable beore: " + Thread.currentThread().getName());
            }
        })
//                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .andThen(Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("Completable after: " + Thread.currentThread().getName());
                    }
                }));

    }

}
