package Rx1;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by fkruege on 4/10/17.
 */
public class RetryTest {

    public static void main(String[] args) throws InterruptedException {

        String msg = "Hello world";

        Observable<String> just = Observable.just(msg);
        Observable<Long> timer = Observable.timer(2500, TimeUnit.MILLISECONDS);
        final List<Boolean> flags = new ArrayList<>();

        Observable
                .zip(just, timer, new Func2<String, Long, String>() {
                    @Override
                    public String call(String s, Long aLong) {
                        return s;
                    }
                })
//                .doOnNext(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        if(flags.size() == 0){
//                            flags.add(true);
//                            throw new NullPointerException();
//                        }
//                    }
//                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("doONError Throwable.  Msg is:  " + msg);
                    }
                })
                .retry()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        if(flags.size() == 0){
                            flags.add(true);
                            throw new NullPointerException();
                        }
                        System.out.println("String s: " + s);

                    }
                });


        Thread.sleep(8000);


    }


}
