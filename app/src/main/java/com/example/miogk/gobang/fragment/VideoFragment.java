package com.example.miogk.gobang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.miogk.gobang.Main4Activity;
import com.example.miogk.gobang.R;
import com.example.miogk.gobang.domain.ConstellationPicture;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/12/19.
 */

public class VideoFragment extends Fragment {
    private static final String TAG = "VideoFragment";
    private ArrayList<ConstellationPicture> pictures;
    private String url = "https://www.iqiyi.com/w_19rtukdh15.html";
    private String url2 = "blob:https://baike.baidu.com/7e03db05-d080-495b-b62e-ed474151a319";
    //    private String iframe = "<iframe src=\"http://open.iqiyi.com/developer/player_js/coopPlayerIndex.html?vid=e8013af30fd575b00ea8bbc1d7d0cef5&tvId=9479528209&accessToken=2.f22860a2479ad60d8da7697274de9346&appKey=3955c3425820435e86d0f4cdfe56f5e7&appId=1368&height=100%&width=100%\" frameborder=\"0\" allowfullscreen=\"true\" width=\"100%\" height=\"100%\"></iframe>";
//    private String iframe = "http://open.iqiyi.com/developer/player_js/coopPlayerIndex.html?vid=e8013af30fd575b00ea8bbc1d7d0cef5&tvId=9479528209&accessToken=2.f22860a2479ad60d8da7697274de9346&appKey=3955c3425820435e86d0f4cdfe56f5e7&appId=1368&height=100%25&width=100%";
    private String[] iframes = {"https://v.qq.com/txp/iframe/player.html?vid=x03769sfuvf"
            , "https://v.qq.com/txp/iframe/player.html?vid=n0393zphzpo"
            , "https://v.qq.com/txp/iframe/player.html?vid=d0812nc3f9w"
            , "https://v.qq.com/txp/iframe/player.html?vid=r0384lckpxi"
            , "https://v.qq.com/txp/iframe/player.html?vid=x03769sfuvf"
            , "https://v.qq.com/txp/iframe/player.html?vid=n0393zphzpo"
            , "https://v.qq.com/txp/iframe/player.html?vid=d0812nc3f9w"
            , "https://v.qq.com/txp/iframe/player.html?vid=r0384lckpxi"
            , "https://v.qq.com/txp/iframe/player.html?vid=x03769sfuvf"
            , "https://v.qq.com/txp/iframe/player.html?vid=n0393zphzpo"
            , "https://v.qq.com/txp/iframe/player.html?vid=d0812nc3f9w"
            , "https://v.qq.com/txp/iframe/player.html?vid=r0384lckpxi"};
    private WebView webView;
    private RecyclerView video_fragment_recycler_view;
    private ArrayList<String> urls = new ArrayList<>();
    private View rootView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG, "setUserVisibleHint: ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        View view = inflater.inflate(R.layout.video_fragment, container, false);
        Log.e(TAG, "onCreateView: ");
        rootView = view;
        video_fragment_recycler_view = (RecyclerView) rootView.findViewById(R.id.video_fragment_recycler_view);
        video_fragment_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        video_fragment_recycler_view.setAdapter(new MyAdapter(urls));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void init() {
        pictures = (ArrayList<ConstellationPicture>) getArguments().get("pictures");
        for (String s : iframes) {
            urls.add(s);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        ArrayList<String> urls;

        public MyAdapter() {
        }

        public MyAdapter(ArrayList<String> urls) {
            this.urls = urls;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.video_fragment_recycler_view_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            WebView webView = holder.webView;
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            //WebView的缓存模式
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            //自动加载图片
            settings.setLoadsImagesAutomatically(true);
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadUrl(urls.get(position));
        }

        @Override
        public int getItemCount() {
            return urls.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private WebView webView;

            public MyViewHolder(View itemView) {
                super(itemView);
                webView = (WebView) itemView.findViewById(R.id.web_view);
            }
        }
    }
}