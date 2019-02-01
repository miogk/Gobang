package com.example.miogk.gobang.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.miogk.gobang.R;

public class GestureFragment extends Fragment {
    private static final String TAG = "GestureFragment";
    private ScaleGestureDetector scaleGestureDetector;
    private ImageView imageView;
    private Matrix matrix;
    private float scale = 1;
    private float screenWidth;
    private float screenHeight;
    private float picWidth;
    private float picHeight;
    private float preScale;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gesture_item, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.image_view);
        getScreenSize();
        initScaleGestureDetector();
        return rootView;
    }

    private void initScaleGestureDetector() {
        matrix = new Matrix();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
        picWidth = bitmap.getWidth();
        preScale = screenWidth / picWidth;//图片全屏时的缩放比例
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new MySimpleOnScaleGestureListener());
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return scaleGestureDetector.onTouchEvent(event);
            }
        });

    }

    private void getScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    class MySimpleOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            float factor = detector.getScaleFactor();
            matrix.setScale(factor * preScale, factor * preScale,
                    imageView.getWidth() / 2, imageView.getHeight() / 2);
            imageView.setImageMatrix(matrix);
            return false;
        }
    }
}