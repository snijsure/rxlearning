package Rx2;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.io.IOException;

public class CompletebleErrors {
    public static void main(String[] args) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("in main part, throw");
//                throw new IOException("");
                throw new IllegalStateException();
            }
        }).onErrorResumeNext(new Function<Throwable, CompletableSource>() {
            @Override
            public CompletableSource apply(Throwable throwable) throws Exception {
                if (throwable instanceof IOException) {
                    System.out.println("IOException: Please continue");
                    return Completable.complete();
                }
                return Completable.error(throwable);
            }
        })
                .andThen(Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("andThen fired");
                    }
                }))
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("doOnComplete fired");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("doOnError fired");
                    }
                })
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Subscribe onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Subscribe onError " + e);

                    }
                });

    }
}
