package com.example.miogk.gobang;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.miogk.gobang.domain.WeatherEntity;
import com.example.miogk.gobang.util.MyUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String[] names = {"aaaaa", "bbbbbb", "ccccccc", "ddddddd",
            "aaaaa", "bbbbbb", "ccccccc", "ddddddd", "aaaaa", "bbbbbb", "ccccccc", "ddddddd"};
    private DrawView drawView;
    private static final String TAG = "MainActivity";
    private BallGame ballGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        ballGame = (BallGame) findViewById(R.id.ball_game);
//        Handler handler = new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 0x123:
//                        ballGame.invalidate();
//                        break;
//                }
//            }
//        };
//        ballGame.setHandler(handler);
//        ballGame.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_DPAD_RIGHT:
//                        ballGame.setRackeyX(ballGame.getRackeyX() + BallGame.offset);
//                        ballGame.invalidate();
//                        break;
//                    case KeyEvent.KEYCODE_DPAD_LEFT:
//                        ballGame.setRackeyX(ballGame.getRackeyX() - BallGame.offset);
//                        ballGame.invalidate();
//                        break;
//                }
//                return true;
//            }
//        });
//        ballGame.setFocusable(true);
        drawView = (DrawView) findViewById(R.id.draw_view);
//        GridView gridView = (GridView) findViewById(R.id.grid_view);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
//        gridView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_color, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.red:
                drawView.changeColor(Color.RED);
                break;
            case R.id.green:
                drawView.changeColor(Color.GREEN);
                break;
            case R.id.yellow:
                drawView.changeColor(Color.YELLOW);
                break;
        }
        return true;
    }

    private void doRequestByRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("").addConverterFactory(GsonConverterFactory.create()).build();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherEntity> call = weatherService.getMessage("上海");
        call.enqueue(new Callback<WeatherEntity>() {
            @Override
            public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                WeatherEntity weatherEntity = response.body();
                String ganmao = weatherEntity.getData().getGanmao();
            }

            @Override
            public void onFailure(Call<WeatherEntity> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }
}