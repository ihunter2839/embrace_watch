package com.embrace.embrace_watch;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class TestTimer extends Activity {

    private Chronometer timer;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_timer);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.test_timer_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                timer = (Chronometer) findViewById(R.id.test_timer_timer);
                startButton = (Button) findViewById(R.id.test_timer_start_button);
                stopButton = (Button) findViewById(R.id.test_timer_stop_button);
            }
        });
    }

    public void startTimer(View view){
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
    }

    public void stopTimer(View view){
        timer.stop();
    }
}
