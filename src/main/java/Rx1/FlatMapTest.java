package Rx1;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fkruege on 4/10/17.
 */
public class FlatMapTest {

    public static void main(String[] args) {

        Observable<Integer> listObservable1 = Observable
                .fromCallable(() -> getIntegers(0))
                .flatMap(Observable::from)
                .doOnNext(integer -> System.out.println(integer));



        Observable<Integer> listObservable2 = Observable
                .fromCallable(() -> getIntegers(101))
                .flatMap(Observable::from)
                .doOnNext(integer -> System.out.println(integer));



        Observable
                .merge(listObservable1, listObservable2)
                .subscribe();
//
//        Observable
//                .fromCallable(() -> getFlag())
//                .filter(ipAvailable -> ipAvailable)
//                .map(aBoolean -> getIntegers())
//                .flatMap(Observable::from)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });

    }

    private static boolean getFlag() {
        return true;
    }

    private static List<Integer> getIntegers(int start) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(start + i);
        }

        return integers;

    }
}
