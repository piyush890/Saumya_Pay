package com.Client.pay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SliderBean implements Serializable {
    @SerializedName("imgaeid")
    private int imgaeid;
    @SerializedName("imageheading")
    private int imageheading;

    public int getImgaeid() {
        return imgaeid;
    }

    public void setImgaeid(int imgaeid) {
        this.imgaeid = imgaeid;
    }

    public int getImageheading() {
        return imageheading;
    }

    public void setImageheading(int imageheading) {
        this.imageheading = imageheading;
    }
}
