import rx.Observable;
import rx.functions.Action1;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaHooks;
import rx.plugins.RxJavaPlugins;

import java.util.concurrent.Callable;

/**
 * Created by fkruege on 8/29/17.
 */
public class RxJavaOnErrorTesting {

    public static void main(String[] args) throws InterruptedException {
        RxJavaHooks.setOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("Default error handler: " + throwable);
            }
        });

        RxJavaHooks.enableAssemblyTracking();

        Action1<Throwable> onError = RxJavaHooks.getOnError();


//        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
//            @Override
//            public void handleError(Throwable e) {
//                super.handleError(e);
//                System.out.println("RxJavaPlugins error handler: " + e);
//            }
//        });


        Observable
                .fromCallable(() -> {
                    throw new NullPointerException();
//                    return 10 / 0;
                })
                .subscribe();

    }
}
