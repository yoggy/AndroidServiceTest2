package net.sabamiso.android.androidservicetest2;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestService extends Service {
    static final String TAG = "TestService";

    Observable<Long> observable;
    Subscription subscription;

    TestService2 testService2;

    public TestService() {
        observable = Observable.interval(1000, TimeUnit.MILLISECONDS);
    }

    ///////////////////////////////////////////////////////////////////////
    //
    // binder
    //
    private final IBinder mBinder = new TestServiceBinder();

    public class TestServiceBinder extends Binder {
        TestService getService() {
            return TestService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    ///////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        startTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand() : flags=" + flags + ", startId=" + startId + ", intent=" + intent);
        Toast.makeText(getApplicationContext(), "TestService.onStart()", Toast.LENGTH_LONG).show();

        if (intent != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // OSの起動が完了したとき
            Toast.makeText(getApplicationContext(), "TestService.onStart() : ACTION_BOOT_COMPLETED", Toast.LENGTH_LONG).show();
        }

        if (testService2 == null) {
            bindService(new Intent(this, TestService2.class), mConnection, Context.BIND_AUTO_CREATE);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        unbindService(mConnection);
        stopTimer();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "onServiceConnected() : className=" + className + ", service=" + service);
            testService2 = ((TestService2.TestService2Binder)service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "onServiceDisconnected() : className=" + className);
            testService2.stopTimer();
        }
    };

    ///////////////////////////////////////////////////////////////////////

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

    public void startTimer2() {
        if (testService2 != null) {
            testService2.startTimer();
        }
    }

    public void stopTimer2() {
        if (testService2 != null) {
            testService2.stopTimer();
        }
    }
}