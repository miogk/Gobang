package com.example.miogk.gobang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.miogk.gobang.util.MyUtils;


public class ConstellationDetail extends AppCompatActivity {
    private WebView webView;
    private String constellationUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constellation_detail);
        Intent intent = getIntent();
        MyUtils.statusBarBackgroundColor(getWindow(), this, R.color.white);
        MyUtils.MIUISetStatusBarLightMode(this, true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar2);
        setSupportActionBar(toolbar);
        constellationUrl = intent.getStringExtra("constellationUrl");
        webView();
    }

    private void webView() {
        String videoUrl = "http://v.sports.qq.com/#/cover/bhvpa0srtx36e4t/c0029hbh7qx";
        String webUrl = "https://m.baidu.com/sf_bk/item/%E7%99%BD%E7%BE%8A%E5%BA%A7/23547?fr=aladdin&ms=1&rid=9442842173691758113";
        String douyu = "https://www.douyu.com/2295446";
        webView = (WebView) findViewById(R.id.constellationDetail_web_view);
        //更换背景图片
        // 第一次进入程序时，加载URL显示加载提示框
//        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);    //这句话必须保留。。不解释
        webView.setWebChromeClient(new WebChromeClient());//重写一下。有的时候可能会出现问题
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(constellationUrl);
    }
}

