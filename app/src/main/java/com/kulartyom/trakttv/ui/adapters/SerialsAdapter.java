package com.kulartyom.trakttv.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;


import com.kulartyom.trakttv.R;
import com.kulartyom.trakttv.api.item.Serial;

import com.squareup.picasso.Picasso;


public class SerialsAdapter extends ArrayAdapter {

    // ===========================================================
    // Constants
    // ===========================================================
    public static final String TAG = SerialsAdapter.class.getSimpleName();


    // ===========================================================
    // Fields
    // ===========================================================
    private Context context;
    private LayoutInflater inflater;


    // ===========================================================
    // Constructors
    // ===========================================================
    public SerialsAdapter(Context context) {
        super(context, R.layout.item);
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
            convertView = inflater.inflate(R.layout.item, parent, false);
        }
        Serial serial = (Serial) getItem(position);

        if (serial.getShow() != null && serial.getShow().getImages() != null && serial.getShow().getImages().getThumb() != null
                && serial.getShow().getImages().getThumb().getFull() != null) {
            String url_image = serial.getShow().getImages().getThumb().getFull();

            Picasso
                    .with(context)
                    .load(String.valueOf(url_image))
                    .fit()
                    .into((ImageView) convertView);
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

