package net.sabamiso.android.androidservicetest2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

public class MainActivity2 extends AppCompatActivity {
    static final String TAG = "MainActivity2";

    TestService2 testService2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bindService(new Intent(this, TestService2.class), mConnection, Context.BIND_AUTO_CREATE);

        RxView.clicks(findViewById(R.id.buttonStart))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (testService2 != null) testService2.startTimer();
                    }
                });
        RxView.clicks(findViewById(R.id.buttonStop))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (testService2 != null) testService2.stopTimer();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (mConnection != null) {
            unbindService(mConnection);
        }
        super.onDestroy();
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
}
