package com.example.miogk.gobang;

/**
 * Created by Administrator on 2018/12/8.
 */

public interface GameCallBack {
    //游戏结束回调
    void GameOver(int winner);

    //游戏更换执子回调
    void changeGamer(boolean isWhie);
}
