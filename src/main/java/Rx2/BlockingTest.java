package Rx2;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class BlockingTest {

    public static void main(String[] args) throws InterruptedException {


test1();






    }

    private static void test1() throws InterruptedException {

        Scheduler single = Schedulers.single();
        CountDownLatch latch = new CountDownLatch(1);

//        Observable<Integer> rx1 = Observable.just(1).subscribeOn(single);
        Observable<Integer> rx2 = Observable.just(2).subscribeOn(single);

        Observable<Integer> rx1 = Observable.just(1).subscribeOn(Schedulers.computation());
//        Observable<Integer> rx2 = Observable.just(2).subscribeOn(Schedulers.computation());


        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("in main callable");
                Integer integer1 = rx1.subscribeOn(Schedulers.trampoline()).blockingFirst();
                System.out.println("Result 1: is " + integer1);
                Integer integer2 = rx2.blockingFirst();
                System.out.println("Result 2: is " + integer2);

                return integer1 + integer2;
            }
        }).subscribeOn(single)
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("ONNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        latch.countDown();
                    }
                });


        latch.await();


    }

    private void test2Working() throws InterruptedException {

        Scheduler single = Schedulers.single();
        CountDownLatch latch = new CountDownLatch(1);

        Observable<Integer> rx1 = Observable.just(1).subscribeOn(single);
        Observable<Integer> rx2 = Observable.just(2).subscribeOn(single);

        Observable
                .fromCallable(() -> {
                    System.out.println("in main callable");
                    return 100;

                })
                .doOnNext(integer -> System.out.println("integer is: " + integer))
                .flatMap((Function<Integer, ObservableSource<Integer>>) integer -> rx1)
                .doOnNext(integer -> System.out.println("integer is: " + integer))
                .flatMap((Function<Integer, ObservableSource<Integer>>) integer -> rx2)
                .doOnNext(integer -> System.out.println("integer is: " + integer))
                .subscribeOn(single)
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("ONNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        latch.countDown();
                    }
                });


        latch.await();
    }
}
