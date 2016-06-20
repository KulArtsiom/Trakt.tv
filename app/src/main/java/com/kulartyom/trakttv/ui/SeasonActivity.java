package com.kulartyom.trakttv.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.api.ApiService;
import com.kulartyom.trakttv.api.HttpStatusCodes;
import com.kulartyom.trakttv.api.item.seasons.Season;
import com.kulartyom.trakttv.constans.Constans;
import com.kulartyom.trakttv.database.RealmManager;
import com.kulartyom.trakttv.ui.adapters.SeasonsAdapter;

import java.io.IOException;
import java.util.List;

import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeasonActivity extends AppCompatActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    public static final String TAG = AboutSerialsActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private SeasonsAdapter seasonsAdapter;
    private Retrofit retrofit;
    private List<Season> seasonData;
    private RealmManager realmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        seasonsAdapter = new SeasonsAdapter(this);
        realmManager = new RealmManager();

        ListView lvSeasons = (ListView) findViewById(R.id.lv_seasons);
        if (lvSeasons != null) {
            lvSeasons.setAdapter(seasonsAdapter);
        }
        retrofitSettings();
        loadDataSeasons();
    }


    private String getSlug() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return bundle.getString(Constans.ARG_SLUG);
    }

    private void loadDataSeasons() {
        ApiService service = retrofit.create(ApiService.class);
        Call<List<Season>> call = service.getSeasons(getSlug());
        call.enqueue(new Callback<List<Season>>() {
            @Override
            public void onResponse(Call<List<Season>> call, Response<List<Season>> response) {

                if (HttpStatusCodes.isSuccess(response.code())) {
                    seasonData = response.body();
                    if (seasonData != null && !seasonData.isEmpty()) {
                        realmManager.saveListSeasons(seasonData);
                        seasonsAdapter.addAll(realmManager.getListSeason());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Season>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void retrofitSettings(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader(Constans.INTERCEPTOR_HEADER_USER_AGENT, Constans.INTERCEPTOR_HEADER_RETROFIT).build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }
}
