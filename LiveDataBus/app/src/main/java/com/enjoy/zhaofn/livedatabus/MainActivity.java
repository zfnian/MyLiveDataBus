package com.xiangxue.alvin.livedatabus;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView textView;
    Button btn1;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_name);
        btn1 = findViewById(R.id.btn_a1);
        btn2 = findViewById(R.id.btn_a2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity2.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LiveDataBus.get().getChannel("Activity2").setValue("Hello xiangxue Activity2");
                LiveDataBus2.get().with("Activity2").setValue("Hello Xiangxue activity2");
            }
        });
        initLiveDataBus();
    }

    public void initLiveDataBus() {
//        LiveDataBus.get().getChannel("Acitivty1", String.class).
//                observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(@Nullable String s) {
//                        textView.setText(s);
//                    }
//                });
        LiveDataBus2.get().with("Acitivty1",String.class).observe(
                this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d(TAG, "onChanged: ");
                        textView.setText(s);
                    }
                }
        );

        LiveDataBus3.get()
                .with("key_test", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d(TAG, "onChanged: 333");
                    }
                });

        LiveDataBus2.get().with("Acitivty1",String.class).observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged: 3332222");
            }
        });
    }

}

