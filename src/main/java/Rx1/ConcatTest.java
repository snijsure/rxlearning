package Rx1;

import io.reactivex.subscribers.TestSubscriber;
import rx.*;

/**
 * Created by fkruege on 7/12/17.
 */
public class ConcatTest {

    public static void main(String[] args){

        Observable<Integer> observable1 = Observable.just(1);
        Observable<Integer> observable2 = Observable.just(2);
        Observable<Integer> observable3 = Observable.just(3);

        Observable.concat(observable1, observable2, observable3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("observable Completed") ;
                    }

                    @Override
                    public void onError(Throwable e) {

                        System.out.println("observable errror") ;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("observable Onnext: " + integer) ;

                    }
                });


        Completable just1 = Observable.just(1).toCompletable();
        Completable just2 = Observable.just(2).toCompletable();
        Completable just3 = Observable.just(3).toCompletable();

        Completable.concat(just1, just2,just3)
                .subscribe(new CompletableSubscriber() {
                    @Override
                    public void onCompleted() {
                       System.out.println("completable Completed") ;
                    }

                    @Override
                    public void onError(Throwable e) {

                        System.out.println("completable errror") ;
                    }

                    @Override
                    public void onSubscribe(Subscription d) {
                        System.out.println("completable subscribe") ;
                    }

                });

    }
}
