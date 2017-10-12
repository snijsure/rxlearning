//import com.jakewharton.rxrelay.BehaviorRelay;
//import rx.Observable;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.functions.Func2;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by fkruege on 4/3/17.
// */
//public class RxRelay {
//
//    BehaviorRelay<Integer> relay;
//
//    Observable<Integer> evenObservable;
//    Observable<Integer> oddObservable;
//
//    public RxRelay() {
//
//        relay = BehaviorRelay.create(0);
//        Observable<Integer> integerObservable = relay.asObservable();
//
//
//        Observable<Integer> range = Observable.range(0, 1000);
//        Observable<Long> interval = Observable.interval(0, 500, TimeUnit.MILLISECONDS);
//
//
//        Observable.zip(range, interval, new Func2<Integer, Long, Integer>() {
//            @Override
//            public Integer call(Integer integer, Long aLong) {
//                return integer;
//            }
//        }).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                relay.call(integer);
//            }
//        });
//
////                        .
////                        map(new Func1<Integer, Observable<Integer>() {
////                    @Override
////                    public Observable<Integer> call(Integer integer) {
////                        return Observable.just(integer);
////                    }
////                })
//
////                .doOnNext(new Action1<Object>() {
////                    @Override
////                    public void call(Object o) {
////
////                    }
////                });
//
//        evenObservable = integerObservable
//                .filter(integer -> integer % 2 == 0);
//
//        oddObservable = integerObservable
//                .filter(integer -> integer % 2 == 1);
//
//        evenObservable.subscribe(integer -> System.out.println("Even: " + integer));
//
//        oddObservable.subscribe(integer -> System.out.println("Odd: " + integer));
//
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        RxRelay relay = new RxRelay();
//        Thread.sleep(100000);
//    }
//
//
//}
