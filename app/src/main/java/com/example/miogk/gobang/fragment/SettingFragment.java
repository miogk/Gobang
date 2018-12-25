package com.example.miogk.gobang.fragment;

import android.Manifest;
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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.miogk.gobang.MyCamera;
import com.example.miogk.gobang.R;
import com.example.miogk.gobang.service.MyStartService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/12/16.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
    private Button open;
    private Button goto_my_camera;
    private LinearLayout linearLayout;
    private ImageView imageView;
    private String path;
    private RecyclerView recyclerView;
    private static final String TAG = "SettingFragment";
    private View rootView;
    private ServiceConnection connection;
    private MyStartService myStartService;

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
        rootView = view;
        open = (Button) rootView.findViewById(R.id.open);
        goto_my_camera = (Button) rootView.findViewById(R.id.goto_my_camera);
        imageView = (ImageView) rootView.findViewById(R.id.image_view);
        linearLayout = (LinearLayout) rootView;
        path = Environment.getExternalStorageDirectory().getPath() + "/miogk.png";
        File f = new File(path);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof Button) {
                linearLayout.getChildAt(i).setOnClickListener(this);
            }
        }
        init();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void init() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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