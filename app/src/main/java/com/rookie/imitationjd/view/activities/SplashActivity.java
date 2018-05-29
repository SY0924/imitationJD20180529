package com.rookie.imitationjd.view.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rookie.imitationjd.R;

public class SplashActivity extends AppCompatActivity {
    private View view;
    private TextView splash_time;
    private int time=3;
    private MyHandler myHandler= new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_time = (TextView) findViewById(R.id.splash_time);

        //默认值
        splash_time.setText(time+"s");
        myHandler.sendEmptyMessageDelayed(0, 1000);

    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    time--;
                    splash_time.setText(time + "s");
                    if (time == 0) {
                        myHandler.removeCallbacksAndMessages(null);
                        //跳转
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        myHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
            }
        }
    }
}
