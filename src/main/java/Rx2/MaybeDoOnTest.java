package Rx2;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.maybe.MaybeLift;

import java.util.concurrent.Callable;

public class MaybeDoOnTest {

    public static void main(String[] args) throws InterruptedException {

        Maybe.just(1)
                .flatMap(new Function<Integer, MaybeSource<Integer>>() {
                    @Override
                    public MaybeSource<Integer> apply(Integer integer) throws Exception {
                        System.out.println("emit empty");
                        return Maybe.empty();

                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("OnComplete earlier");
                    }
                })
                .switchIfEmpty(Maybe.just(12345))
                .doOnSuccess(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("Accept: " + integer);
                    }
                })
                .subscribe();

//                .doOnSuccess(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        System.out.println("Accept: " + integer);
//                    }
//                })
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
////                        System.out.println("doOnComplete");
//                        throw new NullPointerException("throw in oncomplete");
//                    }
//                })
//                .subscribe(new MaybeObserver<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(Integer integer) {
//                        System.out.println("OnSuccess: " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("OnError: " + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        System.out.println("OnComplete");
//                    }
//                });

    }
}
