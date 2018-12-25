package com.example.miogk.gobang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.miogk.gobang.R;

/**
 * Created by Administrator on 2018/12/15.
 */

public class DefaultFragment extends Fragment {
    private static final String TAG = "DefaultFragment";
    public ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_fragment, container, false);
        imageView = (ImageView) view.findViewById(R.id.every_day_picture);
        return view;
    }
}
