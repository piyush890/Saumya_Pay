package com.Client.pay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Menu_bean implements Serializable {
    @SerializedName("imageId")
    private int imageID;

    @SerializedName("menuName")
    private String menuImg;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }
}
