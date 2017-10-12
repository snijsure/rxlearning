package Rx2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

import java.util.Collections;
import java.util.List;

/**
 * Created by fkruege on 7/15/17.
 */
public class ActionTest {

    public static void main(String[] args) {

        List<String>  list = Collections.emptyList();
        list.add("hello");

        Action action = getRunAction();

        Observable.just(1, 2, 3)
                .subscribe(getObserver(action));

    }

    private static Action getRunAction() {
        return new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("Action executed");
            }
        };
    }

    private static Observer<Integer> getObserver(Action action) {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Wrapper
                        wrapper = new Wrapper(action);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private static class Wrapper {
        Action action;

        public Wrapper(Action action) {
            this.action = action;
        }
    }


}


