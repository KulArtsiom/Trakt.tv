package com.kulartyom.trakttv.api;

import com.kulartyom.trakttv.api.item.*;
import com.kulartyom.trakttv.api.item.seasons.Season;


import java.util.List;
import java.util.Map;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Headers;

import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface ApiService {

    @Headers({
            "Content-type: application/json",
            "trakt-api-key: 6dacbd2fb9d8563ab80756a8b3ab54ec1dc85ffee6be3dbca75505fc7fe197d1",
            "trakt-api-version: 2"
    })
    @GET("shows/trending")
    Call<List<Serial>> getSerials(@QueryMap Map<String, String> options);


    @Headers({
            "Content-type: application/json",
            "trakt-api-key: 6dacbd2fb9d8563ab80756a8b3ab54ec1dc85ffee6be3dbca75505fc7fe197d1",
            "trakt-api-version: 2"
    })

    @GET("shows/{name}/seasons?extended=full,episodes,images")
    Call<List<Season>> getSeasons(@Path("name") String name);

}



