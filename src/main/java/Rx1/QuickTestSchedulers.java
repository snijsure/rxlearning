package Rx1;

import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by fkruege on 6/6/17.
 */
public class QuickTestSchedulers {


    static CountDownLatch doneSignal = new CountDownLatch(1);


    public static void main(String[] args) throws InterruptedException {

        Observable<Object> rx1 = Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(10);
                System.out.println(Thread.currentThread().getName() + "sleep 10");
                return null;
            }
        });

        Observable<Object> rx2 = Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(20);
                System.out.println(Thread.currentThread().getName() + "sleep 20");
                return null;
            }
        }).subscribeOn(Schedulers.io());


        Observable<Object> rx3 = Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(30);
                System.out.println(Thread.currentThread().getName() + "sleep 30");
                return null;
            }
        });

        Observable.concat(rx1, rx2, rx3)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.immediate())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        doneSignal.countDown();
                    }
                })
                .subscribe();

        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        Single.just("ehllo WOrld")
//                .doAfterTerminate(new Action0() {
//                    @Override
//                    public void call() {
//                        System.out.println(Thread.currentThread().getName() + " thread terminate 1");
//                        doneSignal.countDown();
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//
//
//                .observeOn(Schedulers.newThread())
//                .doOnSuccess(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(Thread.currentThread().getName() + " thread success");
//                    }
//                })
//                .doAfterTerminate(new Action0() {
//                    @Override
//                    public void call() {
//                        System.out.println(Thread.currentThread().getName() + " thread terminate 2");
//                        doneSignal.countDown();
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(Thread.currentThread().getName() + " subscribe success");
//                    }
//                });

        doneSignal.await();

    }
}
