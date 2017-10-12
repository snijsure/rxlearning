package Rx2;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * Created by fkruege on 7/26/17.
 */
public class MaybeTest {

    public static void main(String[] args) throws InterruptedException {
        testMaybe(1);
        testMaybe(2);

         Maybe
                .fromCompletable(Completable.complete())
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

    private static void testMaybe(Integer input) {

        Maybe.just(input)
                .filter(integer -> (integer % 2) == 0)
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        System.out.println("OnSuccess: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

}
