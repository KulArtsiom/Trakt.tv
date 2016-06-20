package com.kulartyom.trakttv.api.item.serials;

import com.google.gson.annotations.SerializedName;
import com.kulartyom.trakttv.api.item.serials.show.images.Ids;
import com.kulartyom.trakttv.api.item.serials.show.images.Image;

import io.realm.RealmObject;


public class Show extends RealmObject {

    @SerializedName("title")
    private String title;

    @SerializedName("year")
    private int year;

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    @SerializedName("ids")
    private Ids ids;

    @SerializedName("overview")
    private String overview;

    @SerializedName("certification")
    private String certification;

    @SerializedName("network")
    private String network;

    @SerializedName("country")
    private String country;

    @SerializedName("trailer")
    private String trailer;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("rating")
    private Double rating;

    @SerializedName("votes")
    private Double votes;

    @SerializedName("images")
    private Image images;


    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    public Double getVotes() {
        return votes;
    }

    public void setVotes(Double votes) {
        this.votes = votes;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
