package com.kulartyom.trakttv.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.api.item.Serial;
import com.kulartyom.trakttv.constans.Constans;
import com.kulartyom.trakttv.database.RealmManager;
import com.kulartyom.trakttv.ui.adapters.FavoritesAdapter;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends AppCompatActivity {

    // ===========================================================
    // Constants
    // ===========================================================
    public static final String TAG = ProfileActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private ImageView ivProfileIcon;
    private TextView tvProfileName;
    private TextView getTvProfileEmail;
    private ListView lvFavoriteSerials;

    private RealmManager realmManager;
    private FavoritesAdapter favoritesAdapter;


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
        setContentView(R.layout.activity_profile);
        findViews();
        profileInformation();
        realmManager = new RealmManager();
        favoritesAdapter = new FavoritesAdapter(this);
        lvFavoriteSerials.setAdapter(favoritesAdapter);

        favoritesAdapter.addAll(realmManager.getFavoritesList());

        lvFavoriteSerials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                informationAboutSerial((Serial) parent.getItemAtPosition(position));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritesAdapter.clear();
        favoritesAdapter.addAll(realmManager.getFavoritesList());
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void profileInformation() {
        Intent in = getIntent();
        Bundle b = in.getExtras();
        tvProfileName.setText(b.getString(Constans.ARG_NAME));
        getTvProfileEmail.setText(b.getString(Constans.ARG_EMAIL));
        Picasso.with(this).load(b.getString(Constans.ARG_LINK)).into(ivProfileIcon);
    }

    private void findViews() {
        ivProfileIcon = (ImageView) findViewById(R.id.iv_profile_icon);
        tvProfileName = (TextView) findViewById(R.id.tv_name_profile);
        getTvProfileEmail = (TextView) findViewById(R.id.tv_email_profile);
        lvFavoriteSerials = (ListView) findViewById(R.id.list_favorite);
    }

    private void informationAboutSerial(Serial serial) {
        Bundle bundle = new Bundle();
        bundle.putString(Constans.ARG_ID_KEY, serial.getId());
        Intent in = new Intent(ProfileActivity.this, AboutSerialsActivity.class);
        in.putExtras(bundle);
        startActivity(in);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
