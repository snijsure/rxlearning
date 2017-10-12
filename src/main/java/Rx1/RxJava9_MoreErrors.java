package Rx1;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import utilities.Helper;
import utilities.URLSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by fkruege on 3/26/16.
 */
public class RxJava9_MoreErrors {

    public static final String TAG = RxJava9_MoreErrors.class.getSimpleName();

    public static void run() {
        Helper.printHeader(TAG);

        URLSearch urlSearch = new URLSearch();


        // a runtime exception is thrown in one of the observables.
        Helper.printHeader("Example 1");

        Observable<List<String>> listObservable4 = Observable.defer(() -> urlSearch.rxQuery1(""));
        Observable<List<String>> listObservable5 = Observable.defer(() -> urlSearch.rxQueryException2(""));
        Observable<List<String>> listObservable6 = Observable.defer(() -> urlSearch.rxQuery2(""));

        // I try to add a onErrorReturn but it still terminates abnormally
        listObservable5.onErrorReturn(throwable -> {
            System.out.println("rxQueryException1 onErrorReturn. return empty arraylist  ");
            return new ArrayList<>();
        });

        Observable
                .merge(listObservable5, listObservable4, listObservable6)
                .flatMapIterable(urls -> urls)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("doOnError: " + throwable.getMessage());
                    }
                })
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("doOnNext: " + s);
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnTerminate");
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("Subscriber onNext: " + s);
                    }
                });


    }

    public static void main(String args[]) throws InterruptedException {

        System.out.println("Main thread: " + Thread.currentThread().getName());


        Action1<Integer> onNext = new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Thread: " + Thread.currentThread().getName() + "  Number = " + integer);
            }
        };

        Observable.defer(() ->
                Observable.just(2, 4, 6, 8, 10)
        )
                .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                .subscribe(onNext);


        Observable.defer(() ->
                Observable.just(1, 3, 5, 7, 9)
        )
                .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                .subscribe(onNext);




        Thread.sleep(2500);


        //
//        URLSearch urlSearch = new URLSearch();
//
//        Helper.printHeader("Example 1");
//        // Exception handling in subscriber onError
//        Subscription subscribe = urlSearch.rxQuery1("")
//                .repeat(10)
//                .flatMapIterable(urls -> urls)
//                .map(new Func1<String, Integer>() {
//                    @Override
//                    public Integer call(String s) {
//                        // introduce an error here
//                        return Integer.parseInt(s);
//                    }
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void o


    }


}
