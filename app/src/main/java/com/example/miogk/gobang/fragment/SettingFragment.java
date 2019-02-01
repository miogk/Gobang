package com.example.miogk.gobang.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.miogk.gobang.MyCamera;
import com.example.miogk.gobang.R;
import com.example.miogk.gobang.service.MyStartService;
import com.example.miogk.gobang.util.MyUtils;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/12/16.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
    private Button open;
    private Button goto_my_camera;
    private ScrollView scrollView;
    private ImageView imageView;
    private String path;
    private static final String TAG = "SettingFragment";
    private View rootView;
    private ServiceConnection connection;
    private MyStartService myStartService;
    private DiskLruCache diskLruCache;
    private Button change;
    @BindView(R.id.retrofit)
    public Button retrofit;
    @BindView((R.id.rxJava))
    public Button rxjava;
    @BindView(R.id.butterknife)
    public Button butterknife;
    private Unbinder unbinder;

    private void request() {
        int permission = ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
            );
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG, "setUserVisibleHint: ");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        request();
        Log.e(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        retrofit.setOnClickListener(this);
        rxjava.setOnClickListener(this);
        butterknife.setOnClickListener(this);
        rootView = view;
        open = rootView.findViewById(R.id.open);
        Button remove_disk_cache = rootView.findViewById(R.id.remove_disk_cache);
        final TextView disk_cache_size = rootView.findViewById(R.id.disk_cache_size);
        File cacheDir = MyUtils.getDiskCacheDir(getContext(), "cacheDir");
        try {
            diskLruCache = DiskLruCache.open(cacheDir, 1, 1, 1024 * 1024 * 10);
            if (diskLruCache != null) {
                disk_cache_size.setText(diskLruCache.size() / 1024 + "kb");
            }
            remove_disk_cache.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (diskLruCache != null) {
                        try {
                            diskLruCache.delete();
                            disk_cache_size.setText("0kb");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        goto_my_camera = rootView.findViewById(R.id.goto_my_camera);
        imageView = rootView.findViewById(R.id.image_view);
        change = rootView.findViewById(R.id.change);
        change.setOnClickListener(this);
        scrollView = (ScrollView) rootView;
        path = Environment.getExternalStorageDirectory().getPath() + "/miogk.png";
        File f = new File(path);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            if (scrollView.getChildAt(i) instanceof Button) {
                scrollView.getChildAt(i).setOnClickListener(this);
            }
        }
        init();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void init() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change:
                ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f).setDuration(1000).start();
                ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f).setDuration(1000).start();
                ObjectAnimator.ofFloat(imageView, "translationY", 0f, 200f).setDuration(1000).start();
                break;
            case R.id.open:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri u = Uri.fromFile(new File(path));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                startActivityForResult(intent, 1);
                break;
            case R.id.goto_my_camera:
                Intent goto_my_camera = new Intent(this.getActivity(), MyCamera.class);
                startActivity(goto_my_camera);
                break;
            case R.id.start:
                Intent intent1 = new Intent(getActivity(), MyStartService.class);
                connection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        MyStartService.MyIBinder myIBinder = (MyStartService.MyIBinder) service;
                        myStartService = myIBinder.getMyStartService();
                        Log.e(TAG, "onServiceConnected: ");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        Log.e(TAG, "onServiceDisconnected: ");
                    }
                };
                getActivity().bindService(intent1, connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.stop:
                getActivity().unbindService(connection);
                Log.e(TAG, "onClick: " + connection);
                break;
            case R.id.play:
                myStartService.play();
                break;
            default:
                Log.e(TAG, "onClick: " + v.getId());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                FileInputStream in = null;
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data");
                try {
                    in = new FileInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}