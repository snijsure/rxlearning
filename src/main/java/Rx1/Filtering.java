package Rx1;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by fkruege on 3/29/17.
 */
public class Filtering {

    public static void main(String[] args) {

        Integer[] ints = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8};

        for(int i = 0; i <= 10; i++ ){
            System.out.println("i is: " + i);
            Observable<Integer> observable = getObservable(i);
            if(observable == null){
                System.out.println("observable is null");
            }
            observable.subscribe(System.out::println);
        }



    }

    private static Observable<Integer> getObservable(int num){

        return Observable.defer(() ->
                Observable.just(num)
                .filter(integer -> integer % 2 == 0)
        );

    }
}
