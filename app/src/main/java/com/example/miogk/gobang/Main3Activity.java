package com.example.miogk.gobang;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main3Activity extends AppCompatActivity {
    private String s = "https://www.baidu.com";
    private static final String TAG = "Main3Activity";
    private TextView tv;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage: " + msg.what);
            if (msg.what == 0x123) {
                Bundle bundle = msg.getData();
                String result = (String) bundle.get("result");
                if (tv != null) {
                    tv.setText(result);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        tv = (TextView) findViewById(R.id.text_view);
        Button get = (Button) findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getDateFromNet();
                MyAsyncTask task = new MyAsyncTask(Main3Activity.this);
                try {
                    task.execute(new URL(s));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDateFromNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(s);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    InputStream in = conn.getInputStream();
                    String read;
                    final StringBuilder sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(in));
                    while ((read = reader.readLine()) != null) {
                        sb.append(read);
                    }
                    sendMessageToHandler(sb);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void sendMessageToHandler(StringBuilder sb) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("result", sb.toString());
        message.setData(bundle);
        message.what = 0x123;
        handler.sendMessage(message);
    }

    class MyAsyncTask extends AsyncTask<URL, Integer, String> {
        private ProgressDialog dialog;
        private int hasRead = 0;
        private Context context;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(URL... params) {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    hasRead++;
                    publishProgress(hasRead);
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setTitle("任务正在进行中...");
            dialog.setMessage("任务正在进行中...请稍后");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            tv.setText(s);
            dialog.cancel();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tv.setText("已经读取了..." + values[0] + "行");
            dialog.setProgress(values[0]);
        }
    }
}