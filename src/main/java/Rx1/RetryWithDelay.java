package Rx1;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

/**
 * http://stackoverflow.com/questions/22066481/rxjava-can-i-use-retry-but-with-delay/25292833#25292833
 */
public class RetryWithDelay implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private Scheduler scheduler;
    private int retryCount;

    public RetryWithDelay(final int maxRetries, final int retryDelayMillis, Scheduler scheduler) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.scheduler = scheduler;
        this.retryCount = 0;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts.flatMap(
                new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (++retryCount < maxRetries) {
                            // When this Observable calls onNext, the original
                            // Observable will be retried (i.e. re-subscribed).
                            return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS, scheduler);
                        }

                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }
}
