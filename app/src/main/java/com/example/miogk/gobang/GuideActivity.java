package com.example.miogk.gobang;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GuideActivity extends AppCompatActivity {
    private TextView countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        countDown = (TextView) findViewById(R.id.count_down);
        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDown.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(GuideActivity.this, Main4Activity.class);
                startActivity(intent);
                finish();
            }
        }.start();

    }
}