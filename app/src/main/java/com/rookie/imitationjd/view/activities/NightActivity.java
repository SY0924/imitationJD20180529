package com.rookie.imitationjd.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rookie.imitationjd.R;
import com.rookie.imitationjd.utils.ThemeChangeUtil;

public class NightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChangeUtil.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);

        Button mChangeBtn = (Button) findViewById(R.id.btn_setting);
        mChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeChangeUtil.isChange) {
                    ThemeChangeUtil.isChange = false;
                } else {
                    ThemeChangeUtil.isChange = true;
                }
                NightActivity.this.recreate();//重新创建当前Activity实例
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        finish();
    }
}
