package com.kulartyom.trakttv.api.item.seasons;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Season extends RealmObject {

    @PrimaryKey
    private String id;

    @SerializedName("number")
    private int number;

    @SerializedName("ids")
    private SIds ids;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SIds getIds() {
        return ids;
    }

    public void setIds(SIds ids) {
        this.ids = ids;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
