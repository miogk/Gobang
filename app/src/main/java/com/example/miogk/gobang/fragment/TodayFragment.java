package com.example.miogk.gobang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miogk.gobang.R;
import com.example.miogk.gobang.domain.ConstellationToday;

/**
 * Created by Administrator on 2018/12/15.
 */

public class TodayFragment extends Fragment {
    private ConstellationToday constellationToday;
    private static final String TAG = "TodayFragment";
    private TextView name;/*星座名称*/
    private TextView datetime;/*日期*/
    private TextView all;/*综合指数*/
    private TextView color; /*幸运色*/
    private TextView health; /*健康指数*/
    private TextView love;/*爱情指数*/
    private TextView money;/*财运指数*/
    private TextView number;/*幸运数字*/
    private TextView QFriend;/*速配星座*/
    private TextView summary;/*今日概述*/
    private TextView work;/*工作指数*/
    private TextView resultcode;/*返回码*/
    private TextView error_code;/*错误码*/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_fragment_item, container, false);
        constellationToday = (ConstellationToday) getArguments().getSerializable("constellationToday");
        init(view);
        name.setText(constellationToday.getName());
        datetime.setText(constellationToday.getDatetime());
        all.setText(constellationToday.getAll());
        color.setText(constellationToday.getColor());
        health.setText(constellationToday.getHealth());
        love.setText(constellationToday.getLove());
        money.setText(constellationToday.getMoney());
        number.setText(constellationToday.getNumber());
        QFriend.setText(constellationToday.getQFriend());
        summary.setText(constellationToday.getSummary());
        work.setText(constellationToday.getWork());
        return view;
    }

    private void init(View view) {
        name = (TextView) view.findViewById(R.id.name);
        datetime = (TextView) view.findViewById(R.id.datetime);
        all = (TextView) view.findViewById(R.id.all);
        color = (TextView) view.findViewById(R.id.color);
        health = (TextView) view.findViewById(R.id.health);
        love = (TextView) view.findViewById(R.id.love);
        money = (TextView) view.findViewById(R.id.money);
        number = (TextView) view.findViewById(R.id.number);
        QFriend = (TextView) view.findViewById(R.id.QFriend);
        summary = (TextView) view.findViewById(R.id.summary);
        work = (TextView) view.findViewById(R.id.work);
    }
}