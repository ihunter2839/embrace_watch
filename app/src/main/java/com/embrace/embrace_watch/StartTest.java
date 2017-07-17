package com.embrace.embrace_watch;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

/**
 * Created by ihunt on 7/14/2017.
 */

public class StartTest extends Activity {

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        setContentView(R.layout.activity_start_test);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.start_test_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub){
                //do things here
            }
        });
    }

}
