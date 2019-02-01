package com.example.miogk.gobang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;

import com.example.miogk.gobang.domain.ConstellationMonth;
import com.example.miogk.gobang.domain.ConstellationToday;
import com.example.miogk.gobang.domain.ConstellationWeekly;
import com.example.miogk.gobang.domain.ConstellationYear;
import com.example.miogk.gobang.fragment.MonthFragment;
import com.example.miogk.gobang.fragment.TodayFragment;
import com.example.miogk.gobang.fragment.WeeklyFragment;
import com.example.miogk.gobang.fragment.YearFragment;
import com.example.miogk.gobang.util.MyUtils;

public class ShowConstellationActivity extends AppCompatActivity {
    private FragmentManager manager;
    private LruCache lruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_show_constellation);
        MyUtils.statusBarBackgroundColor(getWindow(), this, R.color.white);
        //小米的官方黑色字体设置
        MyUtils.MIUISetStatusBarLightMode(this, true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.back);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        manager = getSupportFragmentManager();
        String day = intent.getStringExtra("day");
        switch (day) {
            case MyUtils.TODAY:
            case MyUtils.TOMORROW:
                getWindow().setEnterTransition(new Explode());
                ConstellationToday constellationToday = (ConstellationToday) intent.getSerializableExtra("constellationToday");
                FragmentTransaction trasaction = manager.beginTransaction();
                TodayFragment todayFragment = new TodayFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("constellationToday", constellationToday);
                todayFragment.setArguments(bundle);
                trasaction.replace(R.id.show_constellation_frame_layout, todayFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                trasaction.commit();
                break;
            case MyUtils.WEEKLY:
                getWindow().setEnterTransition(new Slide());
                ConstellationWeekly constellationWeekly = (ConstellationWeekly) intent.getSerializableExtra("constellationWeekly");
                FragmentTransaction trasaction2 = manager.beginTransaction();
                WeeklyFragment weeklyFragment = new WeeklyFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("constellationWeekly", constellationWeekly);
                weeklyFragment.setArguments(bundle2);
                trasaction2.replace(R.id.show_constellation_frame_layout, weeklyFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                trasaction2.commit();
                break;
            case MyUtils.MONTH:
                getWindow().setEnterTransition(new Fade());
                ConstellationMonth constellationMonth = (ConstellationMonth) intent.getSerializableExtra("constellationMonth");
                FragmentTransaction trasaction3 = manager.beginTransaction();
                MonthFragment monthFragment = new MonthFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("constellationMonth", constellationMonth);
                monthFragment.setArguments(bundle3);
                trasaction3.replace(R.id.show_constellation_frame_layout, monthFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                trasaction3.commit();
                break;
            case MyUtils.YEAR:
                ConstellationYear constellationYear = (ConstellationYear) intent.getSerializableExtra("constellationYear");
                FragmentTransaction trasaction4 = manager.beginTransaction();
                YearFragment yearFragment = new YearFragment();
                Bundle bundle4 = new Bundle();
                bundle4.putSerializable("constellationYear", constellationYear);
                yearFragment.setArguments(bundle4);
                trasaction4.replace(R.id.show_constellation_frame_layout, yearFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                trasaction4.commit();
                break;
        }
    }
}