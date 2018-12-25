package com.example.miogk.gobang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miogk.gobang.R;
import com.example.miogk.gobang.domain.ConstellationToday;
import com.example.miogk.gobang.domain.ConstellationWeekly;

import static com.example.miogk.gobang.R.id.datetime;
import static com.example.miogk.gobang.R.id.name;

/**
 * Created by Administrator on 2018/12/15.
 */

public class WeeklyFragment extends Fragment {
    private ConstellationWeekly constellationWeekly;
    private TextView name;//星座名字
    private TextView date;//日期
    private TextView weekth;//周数
    private TextView health;//健康指数
    private TextView job;//求职指数
    private TextView love;//爱情指数
    private TextView money;//金钱指数
    private TextView work;//工作指数
    private TextView resultcode;//返回码
    private TextView error_code;//错误码


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weekly_fragment_item, container, false);
        constellationWeekly = (ConstellationWeekly) getArguments().getSerializable("constellationWeekly");
        init(view);
        name.setText(constellationWeekly.getName());
        date.setText(constellationWeekly.getDate());
        weekth.setText(constellationWeekly.getWeekth());
//        health.setText(constellationWeekly.getHealth());
//        job.setText(constellationWeekly.getJob());
        love.setText(constellationWeekly.getLove());
        work.setText(constellationWeekly.getWork());
        money.setText(constellationWeekly.getMoney());
        return view;
    }

    private void init(View view) {
        name = (TextView) view.findViewById(R.id.name);
        date = (TextView) view.findViewById(R.id.date);
        weekth = (TextView) view.findViewById(R.id.weekth);
//        health = (TextView) view.findViewById(R.id.health);
//        job = (TextView) view.findViewById(R.id.job);
        love = (TextView) view.findViewById(R.id.love);
        money = (TextView) view.findViewById(R.id.money);
        work = (TextView) view.findViewById(R.id.work);
    }
}