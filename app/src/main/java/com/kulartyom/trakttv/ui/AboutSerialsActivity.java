package com.kulartyom.trakttv.ui;

import android.content.Intent;

import android.support.design.widget.CollapsingToolbarLayout;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.method.LinkMovementMethod;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.api.item.Serial;
import com.kulartyom.trakttv.constans.Constans;
import com.kulartyom.trakttv.database.RealmManager;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;


public class AboutSerialsActivity extends AppCompatActivity {

    // ===========================================================
    // Constants
    // ===========================================================
    public static final String TAG = AboutSerialsActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private ImageView ivThug;
    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvCountry;
    private TextView tvOverview;
    private TextView tvCertification;
    private TextView tvNetwork;
    private TextView tvTrailer;
    private TextView tvHomePage;
    private TextView tvRating;
    private TextView tvVotes;
    private TextView tvTrakt;
    private TextView tvSlug;
    private TextView tvTvdb;
    private TextView tvImdb;
    private TextView tvTmdb;
    private TextView tvTvrage;
    private Button btnSeasons;
    private ArrayList<Serial> favorites;

    private Bundle bundle;
    private RealmManager realmManager;
    private Serial serial;


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
        setContentView(R.layout.acivity_about_serial);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        realmManager = new RealmManager();

        findViews();
        getInfoSerial();
        favorites = new ArrayList<>();
        favorites.addAll(realmManager.getFavoritesList());
        serial = realmManager.getById(bundle.getString(Constans.ARG_ID_KEY));
        addToFavorites();
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        }
        final View content = findViewById(R.id.coordinator);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fab:
                        boolean isFavourite = addToFavorites();
                        assert content != null;
                        if (isFavourite) {
                            realmManager.saveFavorites(serial);
                            Snackbar.make(content, getString(R.string.add_to_favorites) + tvTitle.getText(), Snackbar.LENGTH_SHORT).show();
                            fab.setImageResource(R.drawable.ic_star);
                        } else {
                            fab.setImageResource(R.drawable.ic_star_border);
                            realmManager.deleteFromFavorites(serial);
                            Snackbar.make(content, getString(R.string.delete_from_favorites) + tvTitle.getText(), Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        btnSeasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                informationAboutSlug();
            }
        });
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void findViews() {
        ivThug = (ImageView) findViewById(R.id.background_image);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        tvCountry = (TextView) findViewById(R.id.tv_country);
        tvCertification = (TextView) findViewById(R.id.tv_certification);
        tvNetwork = (TextView) findViewById(R.id.tv_network);
        tvTrailer = (TextView) findViewById(R.id.tv_trailer);
        tvHomePage = (TextView) findViewById(R.id.tv_homepage);
        tvRating = (TextView) findViewById(R.id.tv_rating);
        tvVotes = (TextView) findViewById(R.id.tv_votes);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        btnSeasons = (Button) findViewById(R.id.btn_seasons);
        tvTrakt = (TextView) findViewById(R.id.tv_trakt);
        tvSlug = (TextView) findViewById(R.id.tv_slug);
        tvTvdb = (TextView) findViewById(R.id.tv_tvdb);
        tvImdb = (TextView) findViewById(R.id.tv_imdb);
        tvTmdb = (TextView) findViewById(R.id.tv_tmdb);
        tvTvrage = (TextView) findViewById(R.id.tv_tvrage);
    }

    private void getInfoSerial() {
        Serial serial = realmManager.getById(bundle.getString(Constans.ARG_ID_KEY));
        Picasso.with(this).load(serial.getShow().getImages().getThumb().getFull()).into(ivThug);
        tvTitle.setText(serial.getShow().getTitle());
        tvYear.setText(String.valueOf(serial.getShow().getYear()));
        tvOverview.setText(serial.getShow().getOverview());
        tvCertification.setText(serial.getShow().getCertification());
        tvCountry.setText(serial.getShow().getCountry());
        tvNetwork.setText(serial.getShow().getNetwork());
        tvTrailer.setText(serial.getShow().getTrailer());
        tvTrailer.setMovementMethod(LinkMovementMethod.getInstance());
        tvTrailer.setClickable(true);
        tvHomePage.setText(serial.getShow().getHomepage());
        tvHomePage.setClickable(true);
        tvHomePage.setMovementMethod(LinkMovementMethod.getInstance());
        tvRating.setText(String.valueOf(serial.getShow().getRating()));
        tvVotes.setText(String.valueOf(serial.getShow().getVotes()));
        tvTrakt.setText(String.valueOf(serial.getShow().getIds().getTrakt()));
        tvSlug.setText(serial.getShow().getIds().getSlug());
        tvTvdb.setText(String.valueOf(serial.getShow().getIds().getTvdb()));
        tvImdb.setText(serial.getShow().getIds().getImdb());
        tvTmdb.setText(String.valueOf(serial.getShow().getIds().getTmdb()));
        tvTvrage.setText(String.valueOf(serial.getShow().getIds().getTvrage()));
    }

    private boolean addToFavorites() {
        if (!favorites.contains(serial)) {
            fab.setImageResource(R.drawable.ic_star_border);
            return true;
        } else {
            fab.setImageResource(R.drawable.ic_star);
            return false;
        }
    }

    private void informationAboutSlug() {
        Bundle bundle = new Bundle();
        bundle.putString(Constans.ARG_SLUG, serial.getShow().getIds().getSlug());
        Intent in = new Intent(AboutSerialsActivity.this, SeasonActivity.class);
        in.putExtras(bundle);
        startActivity(in);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
