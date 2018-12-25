package com.example.miogk.gobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import java.io.BufferedReader;

/**
 * Created by Administrator on 2018/12/10.
 */

public class MyView extends View {
    private Bitmap bitmap;
    private Matrix matrix = new Matrix();
    private float sx = 0.0f;
    private int width, height;
    private float scale = 1.0f;
    private boolean isScale = false;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.a1)).getBitmap();
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        this.setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.reset();
        if (!isScale) {
            matrix.setSkew(sx, 0);
        } else {
            matrix.setScale(scale, scale);
        }
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        canvas.drawBitmap(bitmap1, matrix, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_A:
                sx += 0.1;
                invalidate();
                break;
            case KeyEvent.KEYCODE_D:
                isScale = false;
                sx -= 0.1;
                invalidate();
                break;
            case KeyEvent.KEYCODE_W:
                isScale = true;
                if (scale < 2.0) {
                    scale += 0.1;
                    invalidate();
                }
                break;
            case KeyEvent.KEYCODE_S:
                isScale = true;
                if (scale > 0.5) {
                    scale -= 0.1;
                    invalidate();
                }
                break;
        }
        return true;
    }
}