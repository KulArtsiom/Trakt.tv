package com.kulartyom.trakttv.api.item.seasons;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class SIds extends RealmObject {

    @SerializedName("trakt")
    private int trakt;

    @SerializedName("tvdb")
    private int tvdb;

    @SerializedName("tmdb")
    private int tmdb;

    public int getTrakt() {
        return trakt;
    }

    public void setTrakt(int trakt) {
        this.trakt = trakt;
    }

    public int getTvdb() {
        return tvdb;
    }

    public void setTvdb(int tvdb) {
        this.tvdb = tvdb;
    }

    public int getTmdb() {
        return tmdb;
    }

    public void setTmdb(int tmdb) {
        this.tmdb = tmdb;
    }
}
