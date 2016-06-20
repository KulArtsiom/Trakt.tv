package com.kulartyom.trakttv.api.item.serials.show.images;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class Ids extends RealmObject{

    @SerializedName("trakt")
    private int trakt;

    @SerializedName("slug")
    private String slug;

    @SerializedName("tvdb")
    private int tvdb;

    @SerializedName("imdb")
    private String imdb;

    @SerializedName("tmdb")
    private int tmdb;

    @SerializedName("tvrage")
    private int tvrage;

    public int getTrakt() {
        return trakt;
    }

    public void setTrakt(int trakt) {
        this.trakt = trakt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getTvdb() {
        return tvdb;
    }

    public void setTvdb(int tvdb) {
        this.tvdb = tvdb;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public int getTmdb() {
        return tmdb;
    }

    public void setTmdb(int tmdb) {
        this.tmdb = tmdb;
    }

    public int getTvrage() {
        return tvrage;
    }

    public void setTvrage(int tvrage) {
        this.tvrage = tvrage;
    }
}
