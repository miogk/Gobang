package com.example.miogk.gobang.domain;

/**
 * Created by Administrator on 2018/12/14.
 */


public class Picture {
    private Images[] images;

    public Images[] getImages() {
        return images;
    }

    public void setImages(Images[] images) {
        this.images = images;
    }

    public class Images {
        public String url;
    }
}