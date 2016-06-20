package com.kulartyom.trakttv.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.api.item.seasons.Season;
import com.kulartyom.trakttv.constans.Constans;


public class SeasonsAdapter extends ArrayAdapter {
    // ===========================================================
    // Constants
    // ===========================================================
    public static final String TAG = FavoritesAdapter.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================
    private Context context;
    private LayoutInflater inflater;
    private TextView tvSeasonNumber;
    private TextView tvSeasonTrakt;
    private TextView tvSeasonTvDb;
    private TextView tvSeasonTmDb;


    // ===========================================================
    // Constructors
    // ===========================================================
    public SeasonsAdapter(Context context) {
        super(context, R.layout.item_favorites);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_seasons, parent, false);
        }
        tvSeasonNumber = (TextView) convertView.findViewById(R.id.tv_season_number);
        tvSeasonTrakt = (TextView) convertView.findViewById(R.id.tv_season_trakt);
        tvSeasonTvDb = (TextView) convertView.findViewById(R.id.tv_season_tvdb);
        tvSeasonTmDb = (TextView) convertView.findViewById(R.id.tv_season_tmdb);

        Season season = (Season) getItem(position);
        if (!String.valueOf(season.getNumber()).isEmpty() && !String.valueOf(season.getIds().getTrakt()).isEmpty()
                && !String.valueOf(season.getIds().getTmdb()).isEmpty() && !String.valueOf(season.getIds().getTvdb()).isEmpty()) {
            tvSeasonNumber.setText(Constans.SEASON + String.valueOf(season.getNumber()));
            tvSeasonTrakt.setText(Constans.TRAKT + String.valueOf(season.getIds().getTrakt()));
            tvSeasonTmDb.setText(Constans.TMDB + String.valueOf(season.getIds().getTmdb()));
            tvSeasonTvDb.setText(Constans.TVDB + String.valueOf(season.getIds().getTvdb()));
        }

        return convertView;
    }
    // ===========================================================
    // Methods
    // ===========================================================


    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
