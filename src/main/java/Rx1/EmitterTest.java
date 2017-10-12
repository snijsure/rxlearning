package Rx1;

import rx.Observable;
import rx.functions.*;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by fkruege on 4/21/17.
 */
public class EmitterTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("starting up");

        EmitterTest test = new EmitterTest();
        test
                .getChallengeValue()
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.immediate())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("In main: " + s);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("Throwable: " + throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("on completed");
                    }
                });

        try {
            Thread.sleep(60000);

        } catch (Exception ex) {

        }


    }

    PublishSubject<String> publishSubject = PublishSubject.create();

    public Observable<String> getChallengeValue() throws InterruptedException {

//        Observable<Boolean> requestSigning =
//                Observable.fromCallable(this::requestChallengeFromSXE);
//

        Observable<Boolean> requestSigning =
                Observable.fromCallable(this::requestChallengeFromSXE)
                        .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Boolean aBoolean) {
                                if (!aBoolean) {
                                  return  Observable.error(new Exception("bad news bears"));
                                }
                                return Observable.just(aBoolean);
                            }
                        });


        Observable<String> signedChallenge = getSignedChallengeString();
        signedChallenge.subscribeOn(Schedulers.newThread()).subscribe();


        Observable<String> challenge =
                Observable.zip(requestSigning, publishSubject.asObservable().timeout(500, TimeUnit.MILLISECONDS), (aBoolean, s) -> {
                    return s;
                })
                        .retryWhen(errors ->

                                getRetrying(errors)
                        );

        return challenge;


    }

    private Observable<Object> getRetrying(Observable<? extends Throwable> errors) {
        return errors
                .zipWith(Observable.range(1, 10), (Func2<Throwable, Integer, Throwable>) (throwable, integer) -> throwable)
                .flatMap(error -> {
                    if (error instanceof TimeoutException) {
                        System.out.println("Retrying");
                        return Observable.just(null);
                    }

                    return Observable.error((Throwable) error);
                });
    }


    public Observable<String> getChallengeValue2() {

        Observable<Boolean> requestSigning = Observable.fromCallable(this::requestChallengeFromSXE);
        Observable<String> signedChallenge = getSignedChallengeString();
        signedChallenge.subscribeOn(Schedulers.newThread()).subscribe();

        Observable<String> challenge =
                requestSigning
                        .flatMap(new Func1<Boolean, Observable<String>>() {
                            @Override
                            public Observable<String> call(Boolean aBoolean) {
                                return publishSubject.asObservable().timeout(500, TimeUnit.MILLISECONDS);
                            }
                        })
                        .retryWhen(errors ->
                                errors.flatMap(error -> {
                                    if (error instanceof TimeoutException) {
                                        System.out.println("Retrying");
                                        return Observable.just(null);
                                    }

                                    return Observable.error((Throwable) error);
                                })
                        );


        return challenge;


    }

    private boolean requestChallengeFromSXE() {
        System.out.println("Requesting");
//        return true;
        return false;
    }

    public Observable<String> getSignedChallengeString() {


        return Observable
                .timer(3000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        publishSubject.onNext("I have your signed challenge finally");
//                        publishSubject.onNext(null);
                        return "";
//                        return "I have your signed challenge finally";
                    }
                });


    }

}
