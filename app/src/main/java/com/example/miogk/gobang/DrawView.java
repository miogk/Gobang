package com.example.miogk.gobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/12/8.
 */

public class DrawView extends View {
    private Path path;
    private Paint paint;
    //底层的Bitmap
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;

    //从代码添加view调用此构造器
    public DrawView(Context context) {
        super(context);
        init();
    }

    //view添加到xml会调用此构造器
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //供Activity调用更换画笔颜色
    public void changeColor(int color) {
        paint.setColor(color);
    }

    private void init() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                //绘制到底层的Bitmap，结束path的绘制
                cacheCanvas.drawPath(path, paint);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        //将底层Bitmap绘制到view的canvas,实现保存功能.
        canvas.drawBitmap(cacheBitmap, 0, 0, p);
        //同时也绘制正在进行的path.
        canvas.drawPath(path, paint);
    }
}