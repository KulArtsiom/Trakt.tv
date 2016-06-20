package com.kulartyom.trakttv.api.item;


import com.google.gson.annotations.SerializedName;
import com.kulartyom.trakttv.api.item.serials.Show;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Serial extends RealmObject {

    private boolean favorites;

    @PrimaryKey
    private String id;

    @SerializedName("watchers")
    private int watchers;

    @SerializedName("show")
    private Show show;

    public String getId() {
        return id;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
