package com.kulartyom.trakttv.api.item.serials.show.images;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class Thumb extends RealmObject {

    @SerializedName("full")
    private String full;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }
}
