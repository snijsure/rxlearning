package Rx1;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by fkruege on 4/9/17.
 */
public class RxDelay {

    public static void main(String[] args) throws InterruptedException {
        createDelay(1000, "Hello");
        createDelay(1500, "How Are you");
        createDelay(2500, "What is your name");

        Thread.sleep(5000);

//        ConcurrentHashMap.KeySetView<Object, Boolean> objects = ConcurrentHashMap.newKeySet(250);
        Set<String> mySet = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());



    }

    public static void createDelay(long delay, String print){

        Observable.timer(delay, TimeUnit.MILLISECONDS)
                .doOnNext(aLong -> doCheck(aLong, print))
                .subscribe();

    }

    public static void doCheck(long delay, String print){
        System.out.println("Thread: " + Thread.currentThread().getName() + "   Delay: " + delay + " Print this: " + print);

    }

}
