package com.example.miogk.gobang;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.miogk.gobang.util.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCamera extends AppCompatActivity {
    public Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);
        MyUtils.statusBarBackgroundColor(getWindow(), this, R.color.white);
        //小米的官方黑色字体设置
        MyUtils.MIUISetStatusBarLightMode(this, true);
    }

    public void ssssss() {
        MyUtils.Toast(this, "ssssssssssstart");
    }

    private void capture(View view) {

    }

    private Camera getCamera() {
        Camera camera = Camera.open();
        return null;
    }

    private void setStartPreview() {

    }
}