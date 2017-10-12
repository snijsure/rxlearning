package Rx1;

import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;

/**
 * Created by fkruege on 7/28/17.
 */
public class SingleSchedulers {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Single.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSuccess(integer -> System.out.println(Thread.currentThread().getName() + " dOnSuccess : " + integer))
                .subscribe(new SingleSubscriber<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        System.out.println(Thread.currentThread().getName() + " Subscribe onSuccess : " + integer);
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable error) {
//                        System.out.println(Thread.currentThread().getName() + " onError");

                    }
                });

        latch.await();

    }


}
