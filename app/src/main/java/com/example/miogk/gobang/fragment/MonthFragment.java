package com.example.miogk.gobang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miogk.gobang.R;
import com.example.miogk.gobang.domain.ConstellationMonth;

public class MonthFragment extends Fragment {
    private TextView date;
    private TextView name;
    private TextView month;
    private TextView all;
    private TextView healty;
    private TextView love;
    private TextView money;
    private TextView work;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.month_fragment_item, container, false);
        init(view);
        ConstellationMonth constellationMonth = (ConstellationMonth) getArguments().get("constellationMonth");
        date.setText(constellationMonth.getDate());
        name.setText(constellationMonth.getName());
        month.setText(constellationMonth.getMonth());
        all.setText(constellationMonth.getAll());
        healty.setText(constellationMonth.getHealty());
        love.setText(constellationMonth.getLove());
        money.setText(constellationMonth.getMoney());
        work.setText(constellationMonth.getWork());
        return view;
    }

    private void init(View view) {
        date = (TextView) view.findViewById(R.id.date);
        name = (TextView) view.findViewById(R.id.name);
        month = (TextView) view.findViewById(R.id.month);
        all = (TextView) view.findViewById(R.id.all);
        healty = (TextView) view.findViewById(R.id.health);
        love = (TextView) view.findViewById(R.id.love);
        money = (TextView) view.findViewById(R.id.money);
        work = (TextView) view.findViewById(R.id.work);

    }
}
