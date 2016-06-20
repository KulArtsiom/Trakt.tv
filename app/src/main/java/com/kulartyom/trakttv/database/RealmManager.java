package com.kulartyom.trakttv.database;

import com.kulartyom.trakttv.api.item.Serial;
import com.kulartyom.trakttv.api.item.seasons.SIds;
import com.kulartyom.trakttv.api.item.seasons.Season;
import com.kulartyom.trakttv.api.item.serials.Show;
import com.kulartyom.trakttv.api.item.serials.show.images.Ids;
import com.kulartyom.trakttv.api.item.serials.show.images.Image;
import com.kulartyom.trakttv.api.item.serials.show.images.Thumb;
import com.kulartyom.trakttv.facebook.Facebook;


import java.util.List;
import java.util.UUID;

import io.realm.Realm;


public class RealmManager {

    // ===========================================================
    // Constants
    // ===========================================================
    public static final String TAG = RealmManager.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================
    public void saveListSerial(List<Serial> serials) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < serials.size(); i++) {
            Serial serial = realm.createObject(Serial.class);
            Show show = realm.createObject(Show.class);
            Image image = realm.createObject(Image.class);
            Thumb thumb = realm.createObject(Thumb.class);
            Ids ids = realm.createObject(Ids.class);
            serial.setFavorites(false);
            serial.setId(UUID.randomUUID().toString());
            serial.setWatchers(serials.get(i).getWatchers());
            show.setTitle(serials.get(i).getShow().getTitle());
            show.setYear(serials.get(i).getShow().getYear());
            show.setOverview(serials.get(i).getShow().getOverview());
            show.setCertification(serials.get(i).getShow().getCertification());
            show.setNetwork(serials.get(i).getShow().getNetwork());
            show.setCountry(serials.get(i).getShow().getCountry());
            show.setTrailer(serials.get(i).getShow().getTrailer());
            show.setHomepage(serials.get(i).getShow().getHomepage());
            show.setRating(serials.get(i).getShow().getRating());
            show.setVotes(serials.get(i).getShow().getVotes());
            thumb.setFull(serials.get(i).getShow().getImages().getThumb().getFull());
            ids.setImdb(serials.get(i).getShow().getIds().getImdb());
            ids.setSlug(serials.get(i).getShow().getIds().getSlug());
            ids.setTmdb(serials.get(i).getShow().getIds().getTmdb());
            ids.setTrakt(serials.get(i).getShow().getIds().getTrakt());
            ids.setTvrage(serials.get(i).getShow().getIds().getTrakt());
            ids.setTvdb(serials.get(i).getShow().getIds().getTvdb());
            image.setThumb(thumb);
            show.setImages(image);
            show.setIds(ids);
            serial.setShow(show);
        }
        realm.commitTransaction();
    }

    public List<Serial> getListSerials() {
        Realm realm = Realm.getDefaultInstance();
        return realm.allObjects(Serial.class);
    }

    public void saveFavorites(Serial serial) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        serial.setFavorites(true);
        realm.copyToRealmOrUpdate(serial);
        realm.commitTransaction();
    }

    public void deleteFromFavorites(Serial serial) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        serial.setFavorites(false);
        realm.copyToRealmOrUpdate(serial);
        realm.commitTransaction();
    }

    public List<Serial> getFavoritesList() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Serial.class).equalTo("favorites", true).findAll();
    }

    public Serial getById(String string) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Serial.class).contains("id", string).findFirst();
    }

    public void saveListSeasons(List<Season> seasons) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < seasons.size(); i++) {
            Season season = realm.createObject(Season.class);
            SIds ids = realm.createObject(SIds.class);
            season.setId(UUID.randomUUID().toString());
            season.setNumber(seasons.get(i).getNumber());

            ids.setTvdb(seasons.get(i).getIds().getTvdb());
            ids.setTrakt(seasons.get(i).getIds().getTrakt());
            ids.setTmdb(seasons.get(i).getIds().getTmdb());
            season.setIds(ids);
        }
        realm.commitTransaction();
    }

    public List<Season> getListSeason() {
        Realm realm = Realm.getDefaultInstance();
        return realm.allObjects(Season.class);
    }

    public void saveFacebookInformation(String id, String name, String email, String url_photo) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Facebook facebook = realm.createObject(Facebook.class);
        facebook.setId(id);
        facebook.setName(name);
        facebook.setEmail(email);
        facebook.setUrl(url_photo);
        realm.commitTransaction();
    }

    public Facebook getProfileInformation() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Facebook.class).findFirst();
    }
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
