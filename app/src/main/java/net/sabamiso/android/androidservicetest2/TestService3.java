package net.sabamiso.android.androidservicetest2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestService3 extends Service {
    static final String TAG = "TestService3";

    Observable<Long> observable;
    Subscription subscription;

    public TestService3() {
        observable = Observable.interval(1000, TimeUnit.MILLISECONDS);
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // binder
    //
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    ///////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
    }

    public void startTimer() {
        stopTimer();

        subscription =  observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long val) {
                        Log.d(TAG, "call() : val=" + val);
                    }
                });
    }

    public void stopTimer() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

}
