package com.example.miogk.gobang.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/14.
 */

public class ConstellationPicture implements Serializable {
    private int imageId;
    private String name;
    private String date;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
