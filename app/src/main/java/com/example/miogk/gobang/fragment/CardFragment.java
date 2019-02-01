package com.example.miogk.gobang.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.miogk.gobang.ConstellationDetail;
import com.example.miogk.gobang.R;
import com.example.miogk.gobang.domain.ConstellationPicture;
import com.example.miogk.gobang.util.MyImageView;

import java.io.File;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStorageDirectory;

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
    private MyImageView baike_fragment_image_view;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG, "setUserVisibleHint: ");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baike_fragment, container, false);
        Log.e(TAG, "onCreateView: ");
        init();
        rootView = view;
        baike_fragment_image_view = rootView.findViewById(R.id.baike_fragment_image_view);

//        Environment.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            File[] files = getActivity().getExternalFilesDirs(Environment.MEDIA_MOUNTED);
//            for (File file : files) {
//                Log.e(TAG, "onCreateView: " + file.getPath());
//            }
//        }
//        String path = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath() + File.separator + "small.jpeg";
        Uri uri = Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + "/small.jpeg");
        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "small.jpeg");

        if (file.exists()) {
            Log.e(TAG, "onCreateView: file exists ------ " + file.getPath());
//            Bitmap bitmap = BitmapFactory.decodeFile(path);
//            baike_fragment_image_view.setImageBitmap(bitmap);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Glide.with(getContext()).load(file).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(baike_fragment_image_view);
        }
        baike_fragment_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment dialogFragment = new MyDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("image_view", baike_fragment_image_view);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), "qwieoquweqwio");
            }
        });
        recyclerView = rootView.findViewById(R.id.view_fragment_recycler_view);
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
                imageView = itemView.findViewById(R.id.card_view_image_view);
                name = itemView.findViewById(R.id.card_view_image_name);
                date = itemView.findViewById(R.id.card_view_image_date);
            }
        }
    }
}