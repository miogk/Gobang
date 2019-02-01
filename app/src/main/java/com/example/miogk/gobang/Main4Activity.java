package com.example.miogk.gobang;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.miogk.gobang.domain.ConstellationPicture;
import com.example.miogk.gobang.fragment.CardFragment;
import com.example.miogk.gobang.fragment.GestureFragment;
import com.example.miogk.gobang.fragment.MainFragment;
import com.example.miogk.gobang.fragment.MonthFragment;
import com.example.miogk.gobang.fragment.MyRxJavaFragment;
import com.example.miogk.gobang.fragment.SettingFragment;
import com.example.miogk.gobang.fragment.VideoFragment;
import com.example.miogk.gobang.util.MyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * @author miogk
 * @since 2018/12/14
 */


public class Main4Activity extends AppCompatActivity {
    private final int[] pictures = {R.drawable.baiyangzuo, R.drawable.jinniuzuo, R.drawable.shuangzizuo, R.drawable.juxiezuo
            , R.drawable.shizizuo, R.drawable.chunvzuo, R.drawable.tianchengzuo, R.drawable.tianxiezuo, R.drawable.sheshouzuo,
            R.drawable.mojiezuo, R.drawable.shuipingzuo, R.drawable.shuangyuzuo};
    private final String[] constellationNames = {"白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座",};
    private final String[] date = {"3.21-4.19", "4.20-5.20", "5.21-6.21", "6.22-7.22", "7.23-8.22",
            "8.23-9.22", "9.23-10.23", "10.24-11.22", "11.23-12.21", "12.22-1.19", "1.20-2.18", "2.19-3.20"};
    private final String[] urls =
            {"https://m.baidu.com/sf_bk/item/%E7%99%BD%E7%BE%8A%E5%BA%A7/23547?fr=aladdin&ms=1&rid=6873788608569536174&tab=lemma",
                    "https://baike.baidu.com/item/%E9%87%91%E7%89%9B%E5%BA%A7/23548?fr=aladdin"
                    , "https://baike.baidu.com/item/%E5%8F%8C%E5%AD%90%E5%BA%A7/23549?fr=aladdin"
                    , "https://baike.baidu.com/item/%E5%B7%A8%E8%9F%B9%E5%BA%A7/2490814?fr=aladdin"
                    , "https://baike.baidu.com/item/%E7%8B%AE%E5%AD%90%E5%BA%A7/8162?fr=aladdin"
                    , "https://baike.baidu.com/item/%E5%A4%84%E5%A5%B3%E5%BA%A7/2859614?fr=aladdin"
                    , "https://baike.baidu.com/item/%E5%A4%A9%E7%A7%A4%E5%BA%A7/2609891?fr=aladdin"
                    , "https://baike.baidu.com/item/%E5%A4%A9%E8%9D%8E%E5%BA%A7/2543199?fr=aladdin"
                    , "https://baike.baidu.com/item/%E5%B0%84%E6%89%8B%E5%BA%A7/126373?fr=aladdin"
                    , "https://baike.baidu.com/item/%E6%91%A9%E7%BE%AF%E5%BA%A7/2546779?fr=aladdin"
                    , "https://baike.baidu.com/item/%E6%B0%B4%E7%93%B6%E5%BA%A7/128102?fr=aladdin"
                    , "https://baike.baidu.com/item/%E5%8F%8C%E9%B1%BC%E5%BA%A7/23550?fr=aladdin"};
    private RadioButton radio_button_home;
    private RadioButton radio_button_video;
    private RadioButton radio_button_setting;
    private RadioGroup radioGroup;
    private MyViewPager viewPager;
    private int currentId;
    private FragmentManager manager;
    private ArrayList<Fragment> list;
    private ArrayList<ConstellationPicture> cp;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    @BindView(R.id.radio_button_rxjava)
    public RadioButton radio_button_rxjava;

    private void init() {
        cp = new ArrayList<>();
        for (int i = 0; i < pictures.length; i++) {
            ConstellationPicture picture = new ConstellationPicture();
            picture.setImageId(pictures[i]);
            picture.setName(constellationNames[i]);
            picture.setDate(date[i]);
            picture.setUrl(urls[i]);
            cp.add(picture);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    private void initViews() {
        manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pictures", cp);
        list = new ArrayList<>();
        MainFragment mainFragment = new MainFragment();
        CardFragment cardFragment = new CardFragment();
        MonthFragment monthFragment = new MonthFragment();
        VideoFragment videoFragment = new VideoFragment();
        SettingFragment settingFragment = new SettingFragment();
        GestureFragment gestureFragment = new GestureFragment();
        MyRxJavaFragment myRxJavaFragment = new MyRxJavaFragment();
        mainFragment.setArguments(bundle);
        cardFragment.setArguments(bundle);
        monthFragment.setArguments(bundle);
        videoFragment.setArguments(bundle);

        list.add(mainFragment);
        list.add(cardFragment);
        list.add(videoFragment);
        list.add(settingFragment);
        list.add(gestureFragment);
        list.add(myRxJavaFragment);
        viewPager = findViewById(R.id.total_fragment_view_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        radioGroup = findViewById(R.id.radio_group);
        radio_button_home = findViewById(R.id.radio_button_home);
        radio_button_video = findViewById(R.id.radio_button_baike);
        radio_button_setting = findViewById(R.id.radio_button_setting);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb != radio_button_home && radio_button_home.isChecked()) {
                    radio_button_home.setChecked(false);
                }
                switch (checkedId) {
                    case R.id.radio_button_home:
                        currentId = 0;
                        break;
                    case R.id.radio_button_baike:
                        currentId = 1;
                        break;
                    case R.id.radio_button_video:
                        currentId = 2;
                        break;
                    case R.id.radio_button_setting:
                        currentId = 3;
                        break;
                    case R.id.radio_button_gesture:
                        currentId = 4;
                        break;
                    case R.id.radio_button_rxjava:
                        currentId = 5;
                        break;
                }
                //取消ViewPager切换动画
                viewPager.setCurrentItem(currentId, false);
            }
        });
    }

    /**
     * 状态栏处理：解决全屏切换非全屏页面被压缩问题
     */
    public void initStatusBar(int barColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            // 获取状态栏高度
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            View rectView = new View(this);
            // 绘制一个和状态栏一样高的矩形，并添加到视图中
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            //设置状态栏颜色
            rectView.setBackgroundColor(getResources().getColor(barColor));
            // 添加矩形View到布局中
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(rectView);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.total_fragment);
        ButterKnife.bind(this);
        initStatusBar(R.color.white);
        init();
        initViews();
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
//        getSupportActionBar().hide();
        //状态栏设置白色背景色
        MyUtils.statusBarBackgroundColor(getWindow(), this, R.color.white);
        //小米的官方黑色字体设置
        MyUtils.MIUISetStatusBarLightMode(this, true);
        MyUtils.statusBarLightModeInAndroid(this);
//        setHalfTransparent();
//        setStatusBarFullTransparent();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    protected void setHalfTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
//             getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}