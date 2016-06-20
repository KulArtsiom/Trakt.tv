package com.kulartyom.trakttv.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.api.item.Serial;


public class FavoritesAdapter extends ArrayAdapter {

    // ===========================================================
    // Constants
    // ===========================================================
    public static final String TAG = FavoritesAdapter.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================
    private Context context;
    private LayoutInflater inflater;


    // ===========================================================
    // Constructors
    // ===========================================================
    public FavoritesAdapter(Context context) {
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
            convertView = inflater.inflate(R.layout.item_favorites, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_serial_title);
        Serial serial = (Serial) getItem(position);
        if (serial.getShow() != null && serial.getShow().getTitle() != null) {
            textView.setText(serial.getShow().getTitle());
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
