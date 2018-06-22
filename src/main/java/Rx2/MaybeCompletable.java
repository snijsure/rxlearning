package Rx2;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MaybeCompletable {


    public static void main(String[] args) throws InterruptedException {

        Maybe.just(1)
                .flatMap(new Function<Integer, MaybeSource<Object>>() {
                    @Override
                    public MaybeSource<Object> apply(Integer integer) throws Exception {

//                        return Completable.complete().toSingleDefault(voidTest).toMaybe();
                        return Completable.complete().toMaybe();
//                        return Completable.complete().toSingleDefault(new Object()).toMaybe();
                    }
                })
                .subscribe(new MaybeObserver<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onsubscribe");

                    }

                    @Override
                    public void onSuccess(Object aVoid) {
                        System.out.println("onsuccess");
                    }

                    @Override
                    public void onError(Throwable e) {

                        System.out.println("onerror: " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");

                    }
                });


    }

}
