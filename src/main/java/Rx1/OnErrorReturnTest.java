package Rx1;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executor;

/**
 * Created by fkruege on 6/30/17.
 */
public class OnErrorReturnTest {

    public static void main(String[] args) {

        Observable<Integer> defer = Observable.defer(() -> Observable.just(1, 2, 0 ));

        defer
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        if(integer == 0){
                            return integer /0;
                        }

                        return integer;
                    }
                })
                .onErrorReturn(new Func1<Throwable, Integer>() {
                    @Override
                    public Integer call(Throwable throwable) {
                        return 1000;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });


    }

    public class CurrentThreadExecutor implements Executor {
        public void execute(Runnable r) {
            r.run();
        }
    }
}
