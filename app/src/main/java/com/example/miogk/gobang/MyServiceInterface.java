package com.example.miogk.gobang;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/12/10.
 */

public interface MyServiceInterface {
    @GET("/")
    Call<String> getBaidu();
}
