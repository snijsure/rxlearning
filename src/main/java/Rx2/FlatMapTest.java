package Rx2;

import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by fkruege on 9/8/17.
 */
public class FlatMapTest {

    public static void main(String[] args) throws InterruptedException {

        int[] ints = new int[9];

        Observable<Integer> just = Observable.just(1);

        final long[] counter = {0};

        PublishRelay<String> relay1 = PublishRelay.create();
        PublishRelay<String> relay2 = PublishRelay.create();

        PublishRelay<Integer> stateChanged = PublishRelay.create();

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        relay1.accept("Relay1");
                    }
                });

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        relay1.accept("Relay2");
                    }
                });


        Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        long value = counter[0];
                        value++;
                        stateChanged.accept((int) value);
                        counter[0] = value;
                    }
                });

        stateChanged
                .flatMap(integer -> {
                    if (integer % 2 == 0) {
                        return relay1.toFlowable(BackpressureStrategy.LATEST).toObservable();
                    } else {
                        return relay2.toFlowable(BackpressureStrategy.LATEST).toObservable();

                    }
                })
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Thread.sleep(60000);



    }

}
