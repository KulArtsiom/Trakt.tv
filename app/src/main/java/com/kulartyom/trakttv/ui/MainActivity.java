package com.kulartyom.trakttv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.api.ApiService;
import com.kulartyom.trakttv.api.HttpStatusCodes;

import com.kulartyom.trakttv.api.item.Serial;
import com.kulartyom.trakttv.constans.Constans;
import com.kulartyom.trakttv.database.RealmManager;
import com.kulartyom.trakttv.ui.adapters.SerialsAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // ===========================================================
    // Constants
    // ===========================================================
    private static final String TAG = MainActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================
    private SwipeRefreshLayout swipeLayout;
    private CircleImageView ivProfilePhoto;
    private Button btnProfile;
    private GridView gvSerials;
    private List<Serial> serialsData;
    private HashMap<String, String> params;
    private SerialsAdapter adapter;
    private int page = 1;
    private RealmManager realmManager;
    private Retrofit retrofit;


    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader(Constans.INTERCEPTOR_HEADER_USER_AGENT, Constans.INTERCEPTOR_HEADER_RETROFIT)
                        .build();
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


        findViews();
        getProfileIcon();
        adapter = new SerialsAdapter(getApplicationContext());
        realmManager = new RealmManager();
        gvSerials.setAdapter(adapter);
        loadData();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (page != 0) {

                        } else {
                            loadData();
                        }
                        swipeLayout.setRefreshing(false);
                    }
                }, Constans.REFRESH_TIME_OUT);
            }
        });

        gvSerials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                informationAboutSerial((Serial) parent.getItemAtPosition(position));

            }
        });

        gvSerials.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (gvSerials.getAdapter() == null)
                    return;
                if (gvSerials.getAdapter().getCount() == 0)
                    return;
                int length = visibleItemCount + firstVisibleItem;

                if (length >= totalItemCount) {
                    if (Integer.valueOf(params.get(Constans.PARAMS_PAGE)) == page) {
                        page += 1;
                        loadData();
                    }

                }
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentProfileActivity();
            }
        });
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void intentProfileActivity() {
        Bundle b = new Bundle();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        b.putString(Constans.ARG_NAME, bundle.getString(Constans.ARG_NAME));
        b.putString(Constans.ARG_EMAIL, bundle.getString(Constans.ARG_EMAIL));
        b.putString(Constans.ARG_LINK, bundle.getString(Constans.ARG_LINK));
        Intent intent_profile = new Intent(this, ProfileActivity.class);
        intent_profile.putExtras(b);
        startActivity(intent_profile);
    }

    private void getProfileIcon() {
        Intent in = getIntent();
        Bundle b = in.getExtras();
        String s = b.getString(Constans.ARG_LINK);
        Picasso.with(this).load(s).into(ivProfilePhoto);
    }

    private void loadData() {
        ApiService service = retrofit.create(ApiService.class);
        params = new HashMap<>();
        params.put(Constans.PARAMS_PAGE, String.valueOf(page));
        params.put(Constans.PARAMS_LIMIT, Constans.PARAMS_LIMIT_VALUE);
        params.put(Constans.PARAMS_EXTENDED, Constans.PARAMS_EXTENDED_VALUE);

        Call<List<Serial>> call = service.getSerials(params);
        call.enqueue(new Callback<List<Serial>>() {
            @Override
            public void onResponse(Call<List<Serial>> call, Response<List<Serial>> response) {
                if (HttpStatusCodes.isSuccess(response.code())) {
                    serialsData = response.body();
                    if (serialsData != null && !serialsData.isEmpty()) {
                        realmManager.saveListSerial(serialsData);
                        adapter.clear();
                        adapter.addAll(realmManager.getListSerials());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Serial>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    private void findViews() {
        View view = View.inflate(this, R.layout.custom_action_bar, null);
        ivProfilePhoto = (CircleImageView) view.findViewById(R.id.iv_profile_icon);
        btnProfile = (Button) view.findViewById(R.id.btn_profile);
        gvSerials = (GridView) findViewById(R.id.gv_serials);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbar.setContentInsetsRelative(0, 0);
            toolbar.addView(view);
        }
        setSupportActionBar(toolbar);

    }

    private void informationAboutSerial(Serial serial) {
        Bundle bundle = new Bundle();
        bundle.putString(Constans.ARG_ID_KEY, serial.getId());
        Intent in = new Intent(MainActivity.this, AboutSerialsActivity.class);
        in.putExtras(bundle);
        startActivity(in);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================


}
