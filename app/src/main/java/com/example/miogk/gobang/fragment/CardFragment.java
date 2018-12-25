package com.example.miogk.gobang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miogk.gobang.ConstellationDetail;
import com.example.miogk.gobang.R;
import com.example.miogk.gobang.domain.ConstellationPicture;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/16.
 */

public class CardFragment extends Fragment {
    private CardView cardView;
    private RecyclerView recyclerView;
    private WebView webView;
    private static final String TAG = "CardFragment";
    //    private String url = "https://www.mgtv.com/s/4233217.html";
    private String url = "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear2/prog_index.m3u8";
    private ArrayList<ConstellationPicture> pictures;
    private View rootView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG, "setUserVisibleHint: ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baike_fragment, container, false);
        Log.e(TAG, "onCreateView: ");
        init();
        rootView = view;
        recyclerView = (RecyclerView) rootView.findViewById(R.id.view_fragment_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(pictures));
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void init() {
        pictures = (ArrayList<ConstellationPicture>) getArguments().get("pictures");
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
        ArrayList<ConstellationPicture> pictures;

        MyRecyclerViewAdapter(ArrayList<ConstellationPicture> pictures) {
            this.pictures = pictures;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final ConstellationPicture picture = pictures.get(position);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ConstellationDetail.class);
                    intent.putExtra("constellationUrl", picture.getUrl());
                    startActivity(intent);
                }
            });
            Glide.with(getActivity()).load(picture.getImageId()).into(holder.imageView);
            holder.name.setText(picture.getName());
            holder.date.setText(picture.getDate());
        }

        @Override
        public int getItemCount() {
            return pictures.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView name;
            TextView date;

            public MyViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView;
                imageView = (ImageView) itemView.findViewById(R.id.card_view_image_view);
                name = (TextView) itemView.findViewById(R.id.card_view_image_name);
                date = (TextView) itemView.findViewById(R.id.card_view_image_date);
            }
        }
    }
}