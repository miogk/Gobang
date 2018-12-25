package com.example.miogk.gobang.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miogk.gobang.DataService;
import com.example.miogk.gobang.R;
import com.example.miogk.gobang.domain.ConstellationPicture;
import com.example.miogk.gobang.domain.ConstellationToday;
import com.example.miogk.gobang.domain.ConstellationWeekly;
import com.example.miogk.gobang.domain.ConstellationYear;
import com.example.miogk.gobang.util.MyUtils;
import com.google.gson.Gson;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;

import okhttp3.internal.cache.DiskLruCache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/12/14.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "Main4Activity";
    public static final String URL = "http://web.juhe.cn:8080/constellation/getAll?consName=%E7%8B%AE%E5%AD%90%E5%BA%A7&type=today&key=ad07929110b53faab4f27caa260c1761";
    public static final String month = "http://web.juhe.cn:8080/constellation/getAll?consName=%E7%99%BD%E7%BE%8A%E5%BA%A7&type=month&key=ad07929110b53faab4f27caa260c1761";
    public static final String bing_picture = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
    public static final String bing = "https://cn.bing.com";

    public ImageView every_day_picture;
    private RecyclerView recyclerView;
    private ArrayList<ConstellationPicture> list;
    public DrawerLayout mDrawer;
    private BoomMenuButton bmb;
    private String constellationName = "白羊座";
    private FragmentManager manager;
    private ProgressDialog dialog;
    private FragmentTransaction transaction;
    private View rootView;
    private boolean hasCreateFragment = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main4, container, false);
        init();
        Log.e(TAG, "onCreateView: ");
        rootView = view;
        init2(rootView);
        initFragment(rootView);
        return view;
        // setUserVisiableHint
        // onCreateView
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG, "setUserVisibleHint: ");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        hasCreateFragment = false;
    }

    private void init() {
        manager = getActivity().getSupportFragmentManager();
        list = (ArrayList<ConstellationPicture>) getArguments().get("pictures");

    }

    private void init2(View view) {
        mDrawer = (DrawerLayout) view.findViewById(R.id.drawerlayout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        every_day_picture = (ImageView) view.findViewById(R.id.every_day_picture);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(list, getActivity()));
    }

    private void initFragment(View view) {
        bmb = (BoomMenuButton) view.findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder();
            builder.normalImageRes(R.drawable.jinniuzuo);
            builder.textSize(20);
            builder.rippleEffect(true);
            builder.buttonWidth(builder.getButtonWidth());
            builder.buttonHeight(builder.getButtonHeight());
            builder.shadowEffect(true).shadowOffsetY(20).shadowOffsetX(0).shadowRadius(Util.dp2px(20))
                    .shadowCornerRadius(Util.dp2px(5)).shadowColor(Color.parseColor("#ee000000"));
            builder.textGravity(Gravity.CENTER);
            switch (i) {
                case 0:
                    builder.normalTextRes(R.string.today);
                    break;
                case 1:
                    builder.normalTextRes(R.string.tomorrow);
                    break;
                case 2:
                    builder.normalTextRes(R.string.week);
                    break;
                case 3:
                    builder.normalTextRes(R.string.month);
                    break;
                case 4:
                    builder.normalTextRes(R.string.year);
                    break;
            }
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    switch (index) {
                        case 0:
                            Log.e(TAG, "onBoomButtonClick: " + constellationName);
                            String todayJson = MyUtils.getFromSharedPreferences(getActivity(), constellationName + MyUtils.TODAY);
//                            if (!TextUtils.isEmpty(todayJson)) {
//                                getDataFromLocal(todayJson, index);
//                                Log.e(TAG, "onBoomButtonClick: " + todayJson);
//                            } else {
                            dialog = new ProgressDialog(getActivity());
                            dialog.setTitle("正在加载...");
                            dialog.setMessage("正在加载Message.....");
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                            runWithRetrofit(constellationName, MyUtils.TODAY);
//                            }
                            break;
                        case 1:
                            String tomorrowJson = MyUtils.getFromSharedPreferences(getActivity(), constellationName + MyUtils.TOMORROW);
//                            if (!TextUtils.isEmpty(tomorrowJson)) {
//                                getDataFromLocal(tomorrowJson, index);
//                                Log.e(TAG, "onBoomButtonClick: " + tomorrowJson);
//                            } else {
                            dialog = new ProgressDialog(getActivity());
                            dialog.setTitle("正在加载...");
                            dialog.setMessage("正在加载Message.....");
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                            runWithRetrofit(constellationName, MyUtils.TOMORROW);
//                            }
                            break;
                        case 2:
                            String weeklyJson = MyUtils.getFromSharedPreferences(getActivity(), constellationName + MyUtils.WEEKLY);
//                            if (!TextUtils.isEmpty(weeklyJson)) {
                            //                              getDataFromLocal(weeklyJson, index);
//                                Log.e(TAG, "onBoomButtonClick: " + weeklyJson);
//                            } else {
                            dialog = new ProgressDialog(getActivity());
                            dialog.setTitle("正在加载...");
                            dialog.setMessage("正在加载Message.....");
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                            runWithRetrofit(constellationName, MyUtils.WEEKLY);
//                            }
                            break;
                        case 3:
                            runWithRetrofit(constellationName, MyUtils.MONTH);
                            break;
                        case 4:
                            String yearJson = MyUtils.getFromSharedPreferences(getActivity(), constellationName + MyUtils.YEAR);
//                            if (!TextUtils.isEmpty(yearJson)) {
                            //          getDataFromLocal(yearJson, index);
//                                Log.e(TAG, "onBoomButtonClick: " + yearJson);
//                            } else {
                            dialog = new ProgressDialog(getActivity());
                            dialog.setTitle("正在加载...");
                            dialog.setMessage("正在加载Message.....");
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                            runWithRetrofit(constellationName, MyUtils.YEAR);
//                            }
                            break;
                    }
                }
            });
            bmb.addBuilder(builder);
        }
    }

    private void getDataFromLocal(String json, int index) {
        if (every_day_picture.getVisibility() != View.GONE) {
            every_day_picture.setVisibility(View.GONE);
        }
        Gson gson = new Gson();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (index) {
            case 0:
                ConstellationToday constellationToday = gson.fromJson(json, ConstellationToday.class);
                TodayFragment todayFragment = new TodayFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("constellationToday", constellationToday);
                todayFragment.setArguments(bundle);
                transaction.replace(R.id.contellation_fragment, todayFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                transaction.commit();
                break;
            case 1:
                ConstellationToday constellationToday2 = gson.fromJson(json, ConstellationToday.class);
                TodayFragment todayFragment2 = new TodayFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("constellationToday", constellationToday2);
                todayFragment2.setArguments(bundle2);
                transaction.replace(R.id.contellation_fragment, todayFragment2, MyUtils.DEFAULT);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 2:
                ConstellationWeekly constellationWeekly = gson.fromJson(json, ConstellationWeekly.class);
                WeeklyFragment weeklyFragment = new WeeklyFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("constellationWeekly", constellationWeekly);
                weeklyFragment.setArguments(bundle3);
                transaction.replace(R.id.contellation_fragment, weeklyFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                transaction.commit();
                break;
            case 3:
//                ConstellationToday constellationToday = gson.fromJson(json, ConstellationToday.class);
//                TodayFragment todayFragment = new TodayFragment();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("constellationToday", constellationToday);
//                todayFragment.setArguments(bundle);
//                transaction.replace(R.id.contellation_fragment, todayFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
//                transaction.commit();
                break;
            case 4:
                ConstellationYear constellationYear = gson.fromJson(json, ConstellationYear.class);
                YearFragment yearFragment = new YearFragment();
                Bundle bundle5 = new Bundle();
                bundle5.putSerializable("constellationYear", constellationYear);
                yearFragment.setArguments(bundle5);
                transaction.replace(R.id.contellation_fragment, yearFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                transaction.commit();
                break;

        }
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
        ArrayList<ConstellationPicture> list;
        Context context;

        MyRecyclerViewAdapter(ArrayList<ConstellationPicture> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final ConstellationPicture picture = list.get(position);
            View view = holder.view;
            String constellationLocalName = picture.getName();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ImageView imageView = holder.imageView;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (every_day_picture.getVisibility() != View.VISIBLE) {
                        every_day_picture.setVisibility(View.VISIBLE);
                        if (manager != null) {
                            manager.beginTransaction().hide(manager.findFragmentByTag(MyUtils.DEFAULT)).commit();
                        }

                    }
                    constellationName = picture.getName();
                    Glide.with(context).load(picture.getImageId()).into(every_day_picture);
//                    every_day_picture.setImageResource(picture.getImageId());
                    mDrawer.closeDrawers();
                }
            });
            //原生的ImageView.setImageResource加载图片太卡了，所有用Glide加载图片.
            Glide.with(context).load(picture.getImageId()).into(holder.imageView);
//            holder.imageView.setImageResource(picture.getImageId());
            holder.date.setText(picture.getDate());
            holder.textView.setText(constellationLocalName);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView textView;
            private TextView date;
            private View view;

            public MyViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                imageView = (ImageView) itemView.findViewById(R.id.main_fragment_image_view);
                textView = (TextView) itemView.findViewById(R.id.main_fragment_text_view);
                date = (TextView) itemView.findViewById(R.id.main_fragment_date);
            }
        }
    }

    private void runWithRetrofit(final String xingzuoName, final String day) {
        if (every_day_picture.getVisibility() != View.GONE) {
            every_day_picture.setVisibility(View.GONE);
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://web.juhe.cn:8080/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        DataService dataService = retrofit.create(DataService.class);
        String key = "ad07929110b53faab4f27caa260c1761";
        switch (day) {
            case MyUtils.TODAY:
            case MyUtils.TOMORROW:
                Call<ConstellationToday> constellationToday = dataService.getConstellationToday(xingzuoName, day, key);
                constellationToday.enqueue(new Callback<ConstellationToday>() {
                    @Override
                    public void onResponse(Call<ConstellationToday> call, retrofit2.Response<ConstellationToday> response) {
                        ConstellationToday constellationToday = response.body();
                        //将数据存入到SharedPreferences,下次直接从将数据存入到SharedPreferences中取。
                        Gson gson = new Gson();
                        String json = gson.toJson(constellationToday);
                        if (day.equals(MyUtils.TODAY)) {
                            MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.TODAY, json);
                        }
                        if (day.equals(MyUtils.TOMORROW)) {
                            MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.TOMORROW, json);
                        }
                        FragmentTransaction trasaction = manager.beginTransaction();
                        TodayFragment todayFragment = new TodayFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("constellationToday", constellationToday);
                        todayFragment.setArguments(bundle);
                        trasaction.replace(R.id.contellation_fragment, todayFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                        trasaction.commit();
                    }

                    @Override
                    public void onFailure(Call<ConstellationToday> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
            case MyUtils.WEEKLY:
                Call<ConstellationWeekly> constellationWeekly = dataService.getConstellationWeekly(xingzuoName, day, key);
                constellationWeekly.enqueue(new Callback<ConstellationWeekly>() {
                    @Override
                    public void onResponse(Call<ConstellationWeekly> call, retrofit2.Response<ConstellationWeekly> response) {
                        ConstellationWeekly constellationWeekly = response.body();
                        Gson gson = new Gson();
                        String json = gson.toJson(constellationWeekly);
                        MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.WEEKLY, json);
                        FragmentTransaction trasaction = manager.beginTransaction();
                        WeeklyFragment weeklyFragment = new WeeklyFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("constellationWeekly", constellationWeekly);
                        weeklyFragment.setArguments(bundle);
                        trasaction.replace(R.id.contellation_fragment, weeklyFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                        trasaction.commit();
                    }

                    @Override
                    public void onFailure(Call<ConstellationWeekly> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
            case MyUtils.MONTH:
                break;
            case MyUtils.YEAR:
                Call<ConstellationYear> constellationYear = dataService.getConstellationYear(xingzuoName, day, key);
                constellationYear.enqueue(new Callback<ConstellationYear>() {
                    @Override
                    public void onResponse(Call<ConstellationYear> call, retrofit2.Response<ConstellationYear> response) {
                        ConstellationYear constellationYear = response.body();
                        Gson gson = new Gson();
                        String json = gson.toJson(constellationYear);
                        MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.YEAR, json);
                        FragmentTransaction trasaction = manager.beginTransaction();
                        YearFragment yearFragment = new YearFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("constellationYear", constellationYear);
                        yearFragment.setArguments(bundle);
                        trasaction.replace(R.id.contellation_fragment, yearFragment, MyUtils.DEFAULT);
//                        trasaction.addToBackStack(null);
                        trasaction.commit();
                    }

                    @Override
                    public void onFailure(Call<ConstellationYear> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;

        }
        //关闭progressDialog
        if (dialog != null) {
            dialog.cancel();
        }
    }
}