package com.Client.pay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pay_Option_Bean implements Serializable {
    @SerializedName("imageId")
    private int imageID;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @SerializedName("imageName")
    private String imageName;



}
