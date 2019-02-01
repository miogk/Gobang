package com.example.miogk.gobang.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.miogk.gobang.R;
import com.example.miogk.gobang.util.MyImageView;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_PICTURES;

public class MyDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private Button album;
    private Button take_picture;
    private static final String TAG = "MyDialogFragment";
    private AlertDialog alertDialog;
    private Uri uritempFile;
    private MyImageView imageView;
    private Uri photoUri;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_item, null);
        album = view.findViewById(R.id.dialog_fragment_album);
        take_picture = view.findViewById(R.id.dialog_fragment_take_picture);
        album.setOnClickListener(this);
        take_picture.setOnClickListener(this);
        builder.setView(view);
        alertDialog = builder.create();

        return alertDialog;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1://选择图片回调
                    Log.e(TAG, "onActivityResult: 选择图片成功");
                    Uri uri = data.getData();
                    //com.android.camera.action.CROP
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");
                    // 设置裁剪
//                    intent.putExtra("crop", "true");
//                    intent.putExtra("scale", true);
//                    // aspectX aspectY 是宽高的比例
//                    intent.putExtra("aspectX", 1);
//                    intent.putExtra("aspectY", 1);
//                    // outputX outputY 是裁剪图片宽高
//                    intent.putExtra("outputX", 400);
//                    intent.putExtra("outputY", 400);
//                    intent.putExtra("return-data", false);
//                    String path = Environment.getExternalStoragePublicDirectory("").getAbsolutePath() + File.separator + "small.jpg";
//                    uritempFile = Uri.parse("file://" + File.separator + Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath() + File.separator + "small.jpeg");
                    uritempFile = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath() + File.separator + "small.jpeg"));
                    Log.e(TAG, "onActivityResult: " + uritempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
//                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//                    startActivity(intent);
                    startActivityForResult(intent, 2);
                    break;
                case 2://裁剪回调
                    Log.e(TAG, "onActivityResult: 裁剪图片成功" + data.getData());
                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.cancel();
                    }
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        imageView = (MyImageView) bundle.getSerializable("image_view");
                    }
                    Glide.with(getContext()).load(uritempFile).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
                    break;

                case 3://拍照回调
                    Log.e(TAG, "onActivityResult: 拍照回调");
                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.cancel();
                    }
                    Bundle bundle2 = getArguments();
                    if (bundle2 != null) {
                        imageView = (MyImageView) bundle2.getSerializable("image_view");
                    }
                    Glide.with(getContext()).load(photoUri).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
                    break;
                default:
                    break;
            }
        }
    }

    //Intent { dat=content://com.miui.gallery.open/raw//storage/emulated/0/DCIM/Camera/snapshot_1545835673240.png typ=image/png flg=0x1 }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_fragment_album:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.dialog_fragment_take_picture:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String photoPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath() + File.separator + "small.jpeg";
                photoUri = Uri.fromFile(new File(photoPath));
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent2, 3);
                break;
        }
    }
}