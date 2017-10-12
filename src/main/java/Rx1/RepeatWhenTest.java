//import rx.Observable;
//import rx.Subscription;
//import rx.functions.Func1;
//import rx.schedulers.Schedulers;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by fkruege on 4/10/17.
// */
//public class RepeatWhenTest {
//
//
//    private static Subscription subscribe;
//
//    public static void main(String[] args) throws InterruptedException {
//
//        Observable<Integer> listObservable1 = Observable
//                .fromCallable(() -> getIntegers(0))
//                .flatMap(Observable::from)
//                .doOnNext(integer -> System.out.print(integer + ", "));
//
//        Observable testUnsubscribed = Observable.defer(() -> {
//            if (subscribe != null) {
//                if (subscribe.isUnsubscribed()) {
//                    System.out.println("Unsubscribed");
//                } else {
//                    System.out.println("Not Unsubscribed");
//                }
//            }
//
//            return Observable.delay(5, TimeUnit.SECONDS);
//        });
//
//        subscribe = listObservable1
//                .repeatWhen(completed ->
//                        testUnsubscribed
//                )
//                .subscribeOn(Schedulers.computation())
//                .subscribe();
//
//        Thread.sleep(10000);
//
//
//    }
//
//    private static boolean getFlag() {
//        return true;
//    }
//
//    private static List<Integer> getIntegers(int start) {
//        List<Integer> integers = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            integers.add(start + i);
//        }
//
//        return integers;
//
//    }
//}
