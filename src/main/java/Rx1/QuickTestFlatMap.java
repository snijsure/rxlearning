package Rx1;

import javafx.util.Pair;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.concurrent.Callable;

/**
 * Created by fkruege on 6/14/17.
 */
public class QuickTestFlatMap {


    public static void main(String[] args) throws InterruptedException {

        Observable
                .just(1, 2, 3, 4, 5)
                .flatMap(integer -> Observable.fromCallable(() -> isEven(integer))
                        .doOnError(throwable -> System.out.println("Hypothetical write to the log. Error encountered for number: " + integer))
                        .onErrorReturn(throwable -> new Pair<>(integer, false)))
                .subscribe(pair -> System.out.println("Result is: " + pair.getKey() + " " + pair.getValue()));

    }

    private static Pair<Integer, Boolean> isEven(Integer integer) {
        if (integer % 2 == 0) {
            return new Pair<>(integer, true);
        }

        throw new NullPointerException();
    }


}
