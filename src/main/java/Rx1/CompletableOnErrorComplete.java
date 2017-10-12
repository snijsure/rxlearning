package Rx1;

import rx.Completable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;

/**
 * Created by fkruege on 7/6/17.
 */
public class CompletableOnErrorComplete {
    public static void main(String[] args) throws InterruptedException {
//
//        Completable.fromAction(new Action0() {
//            @Override
//            public void call() {
////                System.out.println("NO errors");
//                throw new NullPointerException();
//            }
//        }).doOnCompleted(new Action0() {
//            @Override
//            public void call() {
//                System.out.println("Do on completed");
//            }
//        })
//                .doOnError(new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        System.out.println("Do onError");
//
//                    }
//                })
//                .onErrorComplete(new Func1<Throwable, Boolean>() {
//                    @Override
//                    public Boolean call(Throwable throwable) {
//
//                        System.out.println("OnErrorCOmplete");
//                        return true;
//                    }
//                })
//                .andThen(Completable.fromAction(new Action0() {
//                    @Override
//                    public void call() {
//                        System.out.println("2nd completable");
//                    }
//                })).subscribe();
//

        CountDownLatch latch = new CountDownLatch(1);
//
//        Completable.fromAction(new Action0() {
//            @Override
//            public void call() {
//                System.out.println("NO errors");
////                throw new NullPointerException();
//            }
//        }).subscribeOn(Schedulers.io())
//                .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        System.out.println("1st running on: " + Thread.currentThread().getName());
//                    }
//                })
//                .subscribeOn(Schedulers.computation())
//                 .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        System.out.println("2nd running on: " + Thread.currentThread().getName());
//                    }
//                })               .subscribe(new Action0() {
//            @Override
//            public void call() {
//                System.out.println("subscribe running on: " + Thread.currentThread().getName());
//                latch.countDown();
//
//            }
//        });
//        latch.await();


//
//                vjjjtoSingleDefault(true)
//                .onErrorReturn(new Func1<Throwable, Boolean>() {
//                    @Override
//                    public Boolean call(Throwable throwable) {
//                        System.out.println("onErrorReturn: " + throwable);
//                        return false;
//                    }
//                }).subscribe(new SingleSubscriber<Boolean>() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
//                System.out.println("onSuccess: " + aBoolean);
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                System.out.println("onError " + error);
//            }
//        });
//
//        Single<Boolean> just = Single.just(true);

        Single<String> single = Single.just("Hello World")
                .doOnSuccess(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("Single: " + s + " " + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.computation());


        single.flatMapCompletable(s -> createCompletable(s))
                .observeOn(Schedulers.newThread())
                .subscribe(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("final" + " " + Thread.currentThread().getName());
                        latch.countDown();
                    }
                });


        latch.await();

    }

    private static Completable createCompletable(String s) {

        return Completable.fromAction(new Action0() {
            @Override
            public void call() {
                try {
                    Thread.sleep(250);
                    System.out.println("Completable: " + s + " " + Thread.currentThread().getName());
                    Thread.sleep(250);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }).subscribeOn(Schedulers.newThread());
//        Completable testCompl = Completable.fromAction(
//                () -> {
//
//                    )
//                }
//        ).subscribeOn(Schedulers.newThread());
//        return testCompl;
    }
}
