package com.xiangxue.alvin.livedatabus;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Activity2 extends AppCompatActivity {

    private static final String TAG = "Activity2";
    private Button btn_a6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        btn_a6 = findViewById(R.id.btn_a6);
        btn_a6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataBus2.get().with("Acitivty1").setValue("Hello Av");
                LiveDataBus3.get().with("key_test").setValue("Hello Av333");
            }
        });
        initLiveDataBus();
    }

    public void initLiveDataBus() {
//        LiveDataBus.get().getChannel("Activity2", String.class).
//                observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(@Nullable String s) {
//                        Log.d(TAG, s);
//                    }
//                });
        LiveDataBus2.get().with("Activity2",String.class).
                observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d(TAG, "onChanged: " + s);
                    }
                });
    }
}
