package Rx2;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by fkruege on 8/14/17.
 */
public class CompletableAndThen {

    public static void main(String[] args) throws InterruptedException {

        complete1()
                .andThen(complete2())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                       System.out.println("onsubscribe");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("oncomplete");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public static Completable complete1(){
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("1");
            }
        });
    }


    public static Completable complete2(){
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("2");
            }
        });
    }
}
