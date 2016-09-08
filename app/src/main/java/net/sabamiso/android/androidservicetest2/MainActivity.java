package net.sabamiso.android.androidservicetest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;
import rx.functions.Func2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxView.clicks(findViewById(R.id.buttonStart))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(MainActivity.this, TestService.class);
                        startService(intent);
                    }
                });

        RxView.clicks(findViewById(R.id.buttonStop))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(MainActivity.this, TestService.class);
                        stopService(intent);
                    }
                });

        RxView.clicks(findViewById(R.id.buttonMainActivity2))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }
                });
    }
}
