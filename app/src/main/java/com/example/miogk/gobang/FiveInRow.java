package com.example.miogk.gobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/12/8.
 */

public class FiveInRow extends View {
    //画笔
    private Paint paint;
    //棋子数组
    private int[][] chessArray;
    //当前下棋顺序(默认白棋先下)
    private boolean isWhite = true;
    //游戏是否结束
    private boolean isGameOver = false;
    //bitmap
    private Bitmap whiteChess;
    private Bitmap blackChess;
    //Rect
    private Rect rect;
    //棋盘宽高
    private float len;
    //棋盘格数
    private int GRID_NUMBER = 10;
    //每格之间的距离
    private float preWidth;
    //边距
    private float offset;
    //回调
    private GameCallBack gameCallBack;
    //当前黑白棋胜利次数
    private int whiteChessCount, blackChessCount;

    //白旗
    public static final int WHITE_CHESS = 1;
    //黑旗
    public static final int BLACK_CHESS = 2;
    //白旗赢
    public static final int WHITE_WIN = 101;
    //黑旗赢
    public static final int BLACK_WIN = 102;
    //平局
    public static final int TIE = 103;


    public FiveInRow(Context context) {
        super(context);
        //初始化Paint
        paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        //初始化chessArray
        chessArray = new int[GRID_NUMBER][GRID_NUMBER];
        //初始化棋子图片bitmap
    }

    public FiveInRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}