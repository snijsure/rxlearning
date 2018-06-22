package Rx2;


import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import java.util.concurrent.Callable;

public class SingleFilterTest {

    public static void main(String[] args) {

        Single.fromCallable(() -> "hello")
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.length() > 0;
                    }
                })
                .flatMapCompletable(new Function<String, CompletableSource>() {
                    @Override
                    public CompletableSource apply(String s) throws Exception {
                        return getFlatMapCompletable();
                    }
                })
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("In onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("In onComplete");

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("In onError");

                    }
                });
    }

    private static Completable getFlatMapCompletable() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("In flatmapCompletable");
            }
        });
    }
}
