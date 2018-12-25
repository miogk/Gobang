package com.example.miogk.gobang;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.miogk.gobang.domain.ConstellationPicture;
import com.example.miogk.gobang.fragment.CardFragment;
import com.example.miogk.gobang.fragment.MainFragment;
import com.example.miogk.gobang.fragment.MonthFragment;
import com.example.miogk.gobang.fragment.SettingFragment;
import com.example.miogk.gobang.fragment.VideoFragment;
import com.example.miogk.gobang.util.MyUtils;

import java.util.ArrayList;

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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Bundle bundle = msg.getData();
                String result = (String) bundle.get("result");
            }
        }
    };
    private RadioButton radio_button_home;
    private RadioButton radio_button_video;
    private RadioButton radio_button_setting;
    private RadioGroup radioGroup;
    private MyViewPager viewPager;
    private int currentId;
    private FragmentManager manager;
    private ArrayList<Fragment> list;
    private ArrayList<ConstellationPicture> cp;


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
        mainFragment.setArguments(bundle);
        cardFragment.setArguments(bundle);
        monthFragment.setArguments(bundle);
        videoFragment.setArguments(bundle);
        list.add(mainFragment);
        list.add(cardFragment);
        list.add(videoFragment);
        list.add(settingFragment);
        viewPager = (MyViewPager) findViewById(R.id.total_fragment_view_pager);
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
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radio_button_home = (RadioButton) findViewById(R.id.radio_button_home);
        radio_button_video = (RadioButton) findViewById(R.id.radio_button_baike);
        radio_button_setting = (RadioButton) findViewById(R.id.radio_button_setting);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
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
                }
                //取消ViewPager切换动画
                viewPager.setCurrentItem(currentId, false);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_fragment);
        init();
        initViews();
//        changeStatusBarTextColor(true);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //状态栏设置白色背景色
        MyUtils.statusBarBackgroundColor(getWindow(), this, R.color.white);
        //小米的官方黑色字体设置
        MyUtils.MIUISetStatusBarLightMode(this, true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.swich);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewPager.getCurrentItem() == 0) {
                        MainFragment mainFragment = (MainFragment) list.get(0);
                        DrawerLayout drawerLayout = mainFragment.mDrawer;
                        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            drawerLayout.closeDrawers();
                        } else
                            drawerLayout.openDrawer(Gravity.LEFT);
                    }
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.close_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                if (((MainFragment) list.get(0)).every_day_picture.getVisibility() != View.VISIBLE) {
                    ((MainFragment) list.get(0)).every_day_picture.setVisibility(View.VISIBLE);
                    manager.beginTransaction().hide(manager.findFragmentByTag(MyUtils.DEFAULT)).commit();
                }
//                mDrawer.openMenu();
                break;
        }
        return true;
    }

//    private void getEveryDayPicture() {
//        final String url = "https://cn.bing.com/";
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create()).build();
//        DataService dataService = retrofit.create(DataService.class);
//        String format = "js";
//        String idx = "0";
//        String n = "1";
//        Call<Picture> picture = dataService.getPicture(format, idx, n);
//        picture.enqueue(new Callback<Picture>() {
//            @Override
//            public void onResponse(Call<Picture> call, retrofit2.Response<Picture> response) {
//                String s = url + response.body().getImages()[0].url;
//                Glide.with(Main4Activity.this).load(s).into(every_day_picture);
//            }
//
//            @Override
//            public void onFailure(Call<Picture> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (mDrawer != null) {
//            mDrawer.closeMenu();
//        }
//    }

//    private void run() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url(month).build();
//                try {
//                    Response response = client.newCall(request).execute();
//                    String result = response.body().string();
//                    Message message = new Message();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("result", result);
//                    message.what = 0x123;
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}