package com.example.miogk.gobang.fragment;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miogk.gobang.DataService;
import com.example.miogk.gobang.Main4Activity;
import com.example.miogk.gobang.R;
import com.example.miogk.gobang.ShowConstellationActivity;
import com.example.miogk.gobang.domain.ConstellationBase;
import com.example.miogk.gobang.domain.ConstellationMonth;
import com.example.miogk.gobang.domain.ConstellationPicture;
import com.example.miogk.gobang.domain.ConstellationToday;
import com.example.miogk.gobang.domain.ConstellationWeekly;
import com.example.miogk.gobang.domain.ConstellationYear;
import com.example.miogk.gobang.util.MyUtils;
import com.google.gson.Gson;
import com.jakewharton.disklrucache.DiskLruCache;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

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
    private View rootView;
    private boolean hasCreateFragment = false;
    private Toolbar toolbar;
    private static LruCache<String, ConstellationBase> lruCache;
    private static DiskLruCache diskLruCache;
    private int day_of_year;
    private int week_of_year;
    private int month_of_year;
    private int year_of_year;
    private static Calendar calendar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: ");
        if (calendar == null) {
            synchronized (this) {
                calendar = Calendar.getInstance();
                getDataFromCalender();
            }
        }
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        if (lruCache == null) {
            synchronized (this) {
                if (lruCache == null) {
                    lruCache = new LruCache<String, ConstellationBase>(cacheSize);
                }
            }
        }
        if (diskLruCache == null) {
            synchronized (this) {
                if (diskLruCache == null) {
                    try {
                        File cacheDir = MyUtils.getDiskCacheDir(getContext(), "cacheDir");
                        if (!cacheDir.exists()) {
                            cacheDir.mkdirs();
                        }
//                        Log.e(TAG, "onAttach:" + cacheDir.getAbsolutePath() + " --- " + cacheDir.getPath() + " --- " + cacheDir.exists());
                        diskLruCache = DiskLruCache.open(cacheDir, 1, 1, 1024 * 1024 * 10);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach: ");
        if (diskLruCache != null) {
            try {
                diskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public MainFragment() {
        Log.e(TAG, "MainFragment: ");
    }


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
    }

    private void getDataFromCalender() {
        day_of_year = calendar.get(Calendar.DAY_OF_YEAR);
        week_of_year = calendar.get(week_of_year);
        month_of_year = calendar.get(Calendar.MONTH);
        year_of_year = calendar.get(Calendar.YEAR);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG, "setUserVisibleHint: ");
        }
    }

    private void init() {
        manager = getActivity().getSupportFragmentManager();
        list = (ArrayList<ConstellationPicture>) getArguments().get("pictures");

    }

    private void init2(View view) {
        toolbar = view.findViewById(R.id.tool_bar);
//        toolbar.setNavigationIcon(R.drawable.swich);
        toolbar.setTitle("");
        Main4Activity activity = (Main4Activity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });
        mDrawer = view.findViewById(R.id.drawerlayout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        every_day_picture = view.findViewById(R.id.every_day_picture);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(list, getActivity()));
    }

    private void initFragment(View view) {
        bmb = view.findViewById(R.id.bmb);
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
//                            String todayJson = MyUtils.getFromSharedPreferences(getActivity(), constellationName + MyUtils.TODAY);
                            //先从内存中去取数据
                            if (getdatefromMemory(MyUtils.TODAY, constellationName)) {
                                return;
                            }
                            if (getDataFromDiskLruCache(MyUtils.hashKeyForDisk(constellationName + day_of_year + year_of_year), MyUtils.TODAY)) {
                                return;
                            }
                            //最后再从网络下载数据
                            runWithRetrofit(constellationName, MyUtils.TODAY);
                            break;
                        case 1:
//                            String tomorrowJson = MyUtils.getFromSharedPreferences(getActivity(), constellationName + MyUtils.TOMORROW);
                            if (getdatefromMemory(MyUtils.TOMORROW, constellationName)) {
                                return;
                            }
                            if (getDataFromDiskLruCache(MyUtils.hashKeyForDisk(constellationName + (day_of_year + 1) + year_of_year), MyUtils.TOMORROW)) {
                                return;
                            }
                            runWithRetrofit(constellationName, MyUtils.TOMORROW);
                            break;
                        case 2:
//                            String weeklyJson = MyUtils.getFromSharedPreferences(getActivity(), constellationName + MyUtils.WEEKLY);
                            if (getdatefromMemory(MyUtils.WEEKLY, constellationName)) {
                                return;
                            }
                            if (getDataFromDiskLruCache(MyUtils.hashKeyForDisk(constellationName + week_of_year + year_of_year), MyUtils.WEEKLY)) {
                                return;
                            }
                            runWithRetrofit(constellationName, MyUtils.WEEKLY);
                            break;
                        case 3:
                            if (getdatefromMemory(MyUtils.MONTH, constellationName)) {
                                return;
                            }
                            if (getDataFromDiskLruCache(MyUtils.hashKeyForDisk(constellationName + (month_of_year + 1) + year_of_year), MyUtils.MONTH)) {
                                return;
                            }
                            runWithRetrofit(constellationName, MyUtils.MONTH);
                            break;
                        case 4:
//                            String yearJson = MyUtils.getFromSharedPreferences(getActivity(), constellationName + MyUtils.YEAR);
                            if (getdatefromMemory(MyUtils.YEAR, constellationName)) {
                                return;
                            }
                            if (getDataFromDiskLruCache(MyUtils.hashKeyForDisk(constellationName + year_of_year), MyUtils.YEAR)) {
                                return;
                            }
                            runWithRetrofit(constellationName, MyUtils.YEAR);
                            break;
                    }
                }
            });
            bmb.addBuilder(builder);
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
                imageView = itemView.findViewById(R.id.main_fragment_image_view);
                textView = itemView.findViewById(R.id.main_fragment_text_view);
                date = itemView.findViewById(R.id.main_fragment_date);
            }
        }
    }

    /**
     * 从内存中取得数据
     *
     * @param day
     */
    private boolean getdatefromMemory(String day, String constellationName) {
        switch (day) {
            case MyUtils.TODAY:
            case MyUtils.TOMORROW:
                ConstellationToday constellationToday = (ConstellationToday) lruCache.get(constellationName + day);
                if (constellationToday != null) {
                    intentToShowConstellationActivity(day, "constellationToday", constellationToday);
                    Log.e(TAG, "getdatefrommemory: " + lruCache);
                    return true;
                }
                break;
            case MyUtils.WEEKLY:
                ConstellationWeekly constellationWeekly = (ConstellationWeekly) lruCache.get(constellationName + day);
                if (constellationWeekly != null) {
                    intentToShowConstellationActivity(day, "constellationWeekly", constellationWeekly);
                    Log.e(TAG, "getdatefrommemory: " + lruCache);
                    return true;
                }
                break;
            case MyUtils.MONTH:
                ConstellationMonth constellationMonth = (ConstellationMonth) lruCache.get(constellationName + day);
                if (constellationMonth != null) {
                    intentToShowConstellationActivity(day, "constellationMonth", constellationMonth);
                    Log.e(TAG, "getdatefrommemory: " + lruCache);
                    return true;
                }
                break;
            case MyUtils.YEAR:
                ConstellationYear constellationYear = (ConstellationYear) lruCache.get(constellationName + day);
                if (constellationYear != null) {
                    intentToShowConstellationActivity(day, "constellationYear", constellationYear);
                    Log.e(TAG, "getdatefrommemory: " + lruCache);
                    return true;
                }
                break;
        }
        return false;
    }

    private void putDataToDiskLruCache(String key, ConstellationBase constellationBase) {
        ObjectOutputStream objectOutputStream = null;
        try {
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            OutputStream outputStream = editor.newOutputStream(0);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(constellationBase);
            editor.commit();
            Log.e(TAG, "putDataToDiskLruCache: ");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean getDataFromDiskLruCache(String key, String day) {
        Log.e(TAG, "getDataFromDiskLruCache: " + diskLruCache.size());
        try {
            switch (day) {
                case MyUtils.TODAY:
                case MyUtils.TOMORROW:
                    DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                    if (snapshot != null) {
                        InputStream inputStream = snapshot.getInputStream(0);
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        ConstellationBase constellationBase = (ConstellationBase) objectInputStream.readObject();
                        intentToShowConstellationActivity(day, "constellationToday", constellationBase);
                        Log.e(TAG, "getDataFromDiskLruCache: ");
                        objectInputStream.close();
                        inputStream.close();
                        snapshot.close();
                        return true;
                    }
                    break;
                case MyUtils.WEEKLY:
                    DiskLruCache.Snapshot snapshot2 = diskLruCache.get(key);
                    if (snapshot2 != null) {
                        InputStream inputStream = snapshot2.getInputStream(0);
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        ConstellationBase constellationBase = (ConstellationBase) objectInputStream.readObject();
                        intentToShowConstellationActivity(day, "constellationWeekly", constellationBase);
                        Log.e(TAG, "getDataFromDiskLruCache: ");
                        objectInputStream.close();
                        inputStream.close();
                        snapshot2.close();
                        return true;
                    }
                    break;
                case MyUtils.MONTH:
                    DiskLruCache.Snapshot snapshot3 = diskLruCache.get(key);
                    if (snapshot3 != null) {
                        InputStream inputStream = snapshot3.getInputStream(0);
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        ConstellationBase constellationBase = (ConstellationBase) objectInputStream.readObject();
                        intentToShowConstellationActivity(day, "constellationMonth", constellationBase);
                        Log.e(TAG, "getDataFromDiskLruCache: ");
                        objectInputStream.close();
                        inputStream.close();
                        snapshot3.close();
                        return true;
                    }
                    break;
                case MyUtils.YEAR:
                    DiskLruCache.Snapshot snapshot4 = diskLruCache.get(key);
                    if (snapshot4 != null) {
                        InputStream inputStream = snapshot4.getInputStream(0);
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        ConstellationBase constellationBase = (ConstellationBase) objectInputStream.readObject();
                        intentToShowConstellationActivity(day, "constellationYear", constellationBase);
                        Log.e(TAG, "getDataFromDiskLruCache: ");
                        objectInputStream.close();
                        inputStream.close();
                        snapshot4.close();
                        return true;
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void intentToShowConstellationActivity(String day, String constellationDay, ConstellationBase base) {
        Intent intent = new Intent(getContext(), ShowConstellationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("day", day);
        bundle.putSerializable(constellationDay, base);
        intent.putExtras(bundle);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }


    private void runWithRetrofit(final String xingzuoName, final String day) {
//        if (every_day_picture.getVisibility() != View.GONE) {
//            every_day_picture.setVisibility(View.GONE);
//        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://web.juhe.cn:8080/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        final DataService dataService = retrofit.create(DataService.class);
        final String key = "ad07929110b53faab4f27caa260c1761";
        switch (day) {
            case MyUtils.TODAY:
            case MyUtils.TOMORROW:
                Call<ConstellationToday> constellationToday = dataService.getConstellationToday(xingzuoName, day, key);
                constellationToday.enqueue(new Callback<ConstellationToday>() {
                    @Override
                    public void onResponse(Call<ConstellationToday> call, retrofit2.Response<ConstellationToday> response) {
                        ConstellationToday constellationToday = response.body();
                        //将内容存入内存缓存起来，下次访问先到内存中取。
                        lruCache.put(xingzuoName + day, constellationToday);
                        if (day.equals(MyUtils.TODAY)) {
                            putDataToDiskLruCache(MyUtils.hashKeyForDisk(xingzuoName + day_of_year + year_of_year + day), constellationToday);
                        }
                        if (day.equals(MyUtils.TOMORROW)) {
                            putDataToDiskLruCache(MyUtils.hashKeyForDisk(xingzuoName + (day_of_year + 1) + year_of_year + day), constellationToday);
                        }
                        //将数据存入到SharedPreferences,下次直接从将数据存入到SharedPreferences中取。
//                        Gson gson = new Gson();
//                        String json = gson.toJson(constellationToday);
//                        if (day.equals(MyUtils.TODAY)) {
//                            MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.TODAY, json);
//                        }
//                        if (day.equals(MyUtils.TOMORROW)) {
//                            MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.TOMORROW, json);
//                        }
                        Intent intent = new Intent(getActivity(), ShowConstellationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("day", day);
                        bundle.putSerializable("constellationToday", constellationToday);
                        intent.putExtras(bundle);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                        Log.e(TAG, "runWithRetrofit: ");
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
                        lruCache.put(xingzuoName + day, constellationWeekly);
                        putDataToDiskLruCache(MyUtils.hashKeyForDisk(xingzuoName + week_of_year + year_of_year + day), constellationWeekly);
                        Gson gson = new Gson();
//                        String json = gson.toJson(constellationWeekly);
//                        MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.WEEKLY, json);
                        Intent intent = new Intent(getContext(), ShowConstellationActivity.class);
                        intent.putExtra("day", day);
                        intent.putExtra("constellationWeekly", constellationWeekly);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    }

                    @Override
                    public void onFailure(Call<ConstellationWeekly> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
            case MyUtils.MONTH:
                Call<ConstellationMonth> constellationMonth = dataService.getConstellationMonth(xingzuoName, day, key);
                constellationMonth.enqueue(new Callback<ConstellationMonth>() {
                    @Override
                    public void onResponse(Call<ConstellationMonth> call, retrofit2.Response<ConstellationMonth> response) {
                        ConstellationMonth constellationMonth = response.body();
                        lruCache.put(xingzuoName + day, constellationMonth);
                        putDataToDiskLruCache(MyUtils.hashKeyForDisk(xingzuoName + (month_of_year + 1) + year_of_year + day), constellationMonth);
//                        Gson gson = new Gson();
//                        String json = gson.toJson(constellationMonth);
//                        MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.MONTH, json);
                        Intent intent = new Intent(getContext(), ShowConstellationActivity.class);
                        intent.putExtra("day", day);
                        intent.putExtra("constellationMonth", constellationMonth);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    }

                    @Override
                    public void onFailure(Call<ConstellationMonth> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
            case MyUtils.YEAR:
                Call<ConstellationYear> constellationYear = dataService.getConstellationYear(xingzuoName, day, key);
                constellationYear.enqueue(new Callback<ConstellationYear>() {
                    @Override
                    public void onResponse(Call<ConstellationYear> call, retrofit2.Response<ConstellationYear> response) {
                        ConstellationYear constellationYear = response.body();
                        lruCache.put(xingzuoName + day, constellationYear);
                        putDataToDiskLruCache(MyUtils.hashKeyForDisk(xingzuoName + year_of_year + day), constellationYear);
//                        Gson gson = new Gson();
//                        String json = gson.toJson(constellationYear);
//                        MyUtils.putInSharedPreferences(getActivity(), xingzuoName + MyUtils.YEAR, json);
                        Intent intent = new Intent(getContext(), ShowConstellationActivity.class);
                        intent.putExtra("day", day);
                        intent.putExtra("constellationYear", constellationYear);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    }

                    @Override
                    public void onFailure(Call<ConstellationYear> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
        }
    }
}