package com.kulartyom.trakttv.database.module;

import com.kulartyom.trakttv.api.item.Serial;
import com.kulartyom.trakttv.api.item.seasons.SIds;
import com.kulartyom.trakttv.api.item.seasons.Season;
import com.kulartyom.trakttv.api.item.serials.Show;
import com.kulartyom.trakttv.api.item.serials.show.images.Ids;
import com.kulartyom.trakttv.api.item.serials.show.images.Image;
import com.kulartyom.trakttv.api.item.serials.show.images.Thumb;
import com.kulartyom.trakttv.facebook.Facebook;


@io.realm.annotations.RealmModule(classes = {Serial.class, Show.class, Ids.class, Image.class, Thumb.class, Season.class, SIds.class, Facebook.class})
public class RealmModule {
}
