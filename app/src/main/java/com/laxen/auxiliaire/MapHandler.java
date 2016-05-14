package com.laxen.auxiliaire;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by laxen on 5/14/16.
 */
public class MapHandler implements GoogleMap.OnMapClickListener {
    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("App", "testing heeeere!");
    }
}
