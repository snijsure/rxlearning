package Rx2;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.Callable;

/**
 * Created by fkruege on 7/26/17.
 */
public class MaybeNullTest {

    public static void main(String[] args) throws InterruptedException {

        Maybe.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        })
                .subscribe(new MaybeObserver<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Object o) {
                        System.out.println("Maybe success");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Maybe complete");

                    }
                });


    }

}
