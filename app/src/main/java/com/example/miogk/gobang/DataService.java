package com.example.miogk.gobang;

import com.example.miogk.gobang.domain.ConstellationBase;
import com.example.miogk.gobang.domain.ConstellationMonth;
import com.example.miogk.gobang.domain.ConstellationToday;
import com.example.miogk.gobang.domain.ConstellationWeekly;
import com.example.miogk.gobang.domain.ConstellationYear;
import com.example.miogk.gobang.domain.Picture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/12/13.
 */

public interface DataService {
    @GET
    Call<String> baidu(@Url String url);

    //http://web.juhe.cn:8080/constellation/getAll?consName=%E7%99%BD%E7%BE%8A%E5%BA%A7&type=year&key=ad07929110b53faab4f27caa260c1761
    @GET("constellation/getAll")
    Call<ConstellationToday> getConstellationToday(@Query("consName") String consName, @Query("type") String type, @Query("key") String key);

    @GET("constellation/getAll")
    Call<ConstellationWeekly> getConstellationWeekly(@Query("consName") String consName, @Query("type") String type, @Query("key") String key);

    @GET("constellation/getAll")
    Call<ConstellationYear> getConstellationYear(@Query("consName") String consName, @Query("type") String type, @Query("key") String key);

    @GET("constellation/getAll")
    Call<ConstellationMonth> getConstellationMonth(@Query("consName") String consName, @Query("type") String type, @Query("key") String key);


    @GET("HPImageArchive.aspx")
    Call<Picture> getPicture(@Query("format") String format, @Query("idx") String idx, @Query("n") String n);
}
