package com.example.miogk.gobang;

import com.example.miogk.gobang.domain.WeatherEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/12/10.
 */

public interface WeatherService {
    @GET("weather_mini")
    Call<WeatherEntity> getMessage(@Query("city") String city);
}