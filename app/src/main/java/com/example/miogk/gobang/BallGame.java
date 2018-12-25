package com.example.miogk.gobang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/12/9.
 */

public class BallGame extends View {
    private static final String TAG = "BallGame";
    public static final int offset = 20;
    private Paint paint;
    private boolean gameOver = false;
    private float rackeyX = 200;
    private float rackeyY = 1000;
    private float racketWidth = 100;
    private float racketHeight = 20;
    private float maxWidth;
    private float maxHeight;
    private float circleX = 400;
    private float circleY = 400;
    private final int circleSize = 20;
    private int offsetCircleX = 10;
    private int offsetCircleY = 10;
    private Handler handler;
    private Timer timer;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public float getRackeyX() {
        return rackeyX;
    }

    public void setRackeyX(float rackeyX) {
        this.rackeyX = rackeyX;
    }

    public BallGame(Context context) {
        super(context);
        init();
    }

    public BallGame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setTextSize(50);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        maxWidth = windowManager.getDefaultDisplay().getWidth();
        maxHeight = windowManager.getDefaultDisplay().getHeight();
        rackeyY = maxHeight - maxHeight / 10;
        rackeyX = (maxWidth - racketWidth) / 2;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                circleX -= offsetCircleX;
                circleY += offsetCircleY;
                if ((circleX - circleSize) <= 0 || (circleX + circleSize) >= maxWidth) {
                    offsetCircleX = -offsetCircleX;
                }
                if (circleY >= rackeyY && ((circleX <= rackeyX) || (circleX > rackeyX + racketWidth))) {
                    timer.cancel();
                    gameOver = true;
                } else if (((circleY + circleSize) >= rackeyY && (circleX >= rackeyX && circleX <= rackeyX + racketWidth)) || (circleY - circleSize) <= 0) {
                    offsetCircleY = -offsetCircleY;
                }
                handler.sendEmptyMessage(0x123);
            }
        }, 1000, 100);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!gameOver) {
            if (rackeyX <= 0) {
                rackeyX = 0;
            } else if ((rackeyX + racketWidth) >= maxWidth) {
                rackeyX = maxWidth - racketWidth;
            }
            canvas.drawCircle(circleX, circleY, circleSize, paint);
            canvas.drawRect(rackeyX, rackeyY, rackeyX + racketWidth, rackeyY + racketHeight, paint);
        } else {
            canvas.drawText("游戏结束", maxWidth / 2, maxHeight / 2, paint);
        }
    }
}