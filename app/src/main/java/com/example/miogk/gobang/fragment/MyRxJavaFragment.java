package com.example.miogk.gobang.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.example.miogk.gobang.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import rx.observables.GroupedObservable;

@SuppressLint("CheckResult")
public class MyRxJavaFragment extends Fragment {
    @BindView(R.id.my_rxjava_fragment_button) Button myRxjavaFragmentButton;
    @BindView(R.id.my_rxjava_fragment_image_view) ImageView imageView;
    private Unbinder unbinder;
    private static final String TAG = "MyRxJavaFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_rxjava_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
//        init();
//        init2();
//        init3();
//        init4();
//        init5();
//        init6();
        h5(view);
        return view;
    }

    private void h5(View view) {
        final WebView webView = view.findViewById(R.id.my_rxjava_fragment_web_view);
//        String url = "http:\\/\\/v.juhe.cn\\/wepiao\\/go?key=b3555b9b670eddca74d676c08ff203cc";
        String url = "http:\\/\\/v.juhe.cn\\/wepiao\\/go?key=b3555b9b670eddca74d676c08ff203cc&s=weixin";
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }

    private void init2() {
        Observable.range(1, 100).toMap(new Function<Integer, Object>() {
            @Override
            public Object apply(Integer integer) throws Exception {
                return Integer.toHexString(integer);
            }
        }).subscribe(new Consumer<Map<Object, Integer>>() {
            @Override
            public void accept(Map<Object, Integer> objectIntegerMap) throws Exception {
                Log.e(TAG, "accept: " + objectIntegerMap);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}