package com.example.miogk.gobang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miogk.gobang.R;
import com.example.miogk.gobang.domain.ConstellationWeekly;
import com.example.miogk.gobang.domain.ConstellationYear;

/**
 * Created by Administrator on 2018/12/15.
 */

public class YearFragment extends Fragment {
    private ConstellationYear constellationYear;
    private TextView name;
    private TextView date;
    private TextView year;
    private ConstellationYear.Mima mima;
    private TextView career;
    private TextView info;//年度密码
    private TextView text;//年度说明
    private TextView love;
    private TextView health;
    private TextView finance;
    private TextView luckeyStone;
    private TextView future;
    private TextView resultcode;
    private TextView error_code;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.year_fragment_item, container, false);
        constellationYear = (ConstellationYear) getArguments().getSerializable("constellationYear");
        init(view);
        name.setText(constellationYear.getName());
        date.setText(constellationYear.getDate());
        career.setText(constellationYear.getCareer()[0]);
        info.setText(constellationYear.getMima().getInfo());
        text.setText(constellationYear.getMima().getText()[0]);
        health.setText(constellationYear.getHealth()[0]);
        love.setText(constellationYear.getLove()[0]);
        finance.setText(constellationYear.getFinance()[0]);
        luckeyStone.setText(constellationYear.getLuckeyStone());
        return view;
    }

    private void init(View view) {
        name = (TextView) view.findViewById(R.id.name);
        date = (TextView) view.findViewById(R.id.date);
        career = (TextView) view.findViewById(R.id.caree);
        info = (TextView) view.findViewById(R.id.info);
        text = (TextView) view.findViewById(R.id.text);
        health = (TextView) view.findViewById(R.id.health);
        love = (TextView) view.findViewById(R.id.love);
        finance = (TextView) view.findViewById(R.id.finance);
        luckeyStone = (TextView) view.findViewById(R.id.luckeyStone);
    }
}