package Rx2;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by fkruege on 10/11/17.
 */
public class ConcatTest {


    public static void main(String[] args) {

        try{
            Integer integer = Observable.concat(getEmptyObservable(), getOneObservable())
                    .first(0)
                    .blockingGet();

            System.out.println(integer);
        }catch(Exception ex){
            System.out.println("Exception caught: " + ex);
        }



    }

    private static Observable<Integer> getEmptyObservable() {
//        return Observable.just(-10000);
        return Observable.empty();
    }


    private static Observable<Integer> getOneObservable() {
        return Observable.just(null);
    }

    private static Observable<Integer> getTwoObservable() {
        return Observable.just(2);
    }


}
