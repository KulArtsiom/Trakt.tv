package com.kulartyom.trakttv.api.item.serials.show.images;

import com.google.gson.annotations.SerializedName;



import io.realm.RealmObject;


public class Image extends RealmObject {

    @SerializedName("thumb")
    private Thumb thumb;

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }
}
