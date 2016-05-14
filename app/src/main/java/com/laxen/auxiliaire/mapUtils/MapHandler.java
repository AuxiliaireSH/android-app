package com.laxen.auxiliaire.mapUtils;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by laxen on 5/14/16.
 */
public class MapHandler implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;

    public MapHandler(GoogleMap map) {
        this.map = map;
        this.map.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("App", "testing heeeere!");
    }



    @Override
    public void onMapLongClick(LatLng latLng) {
        map.addMarker(new MarkerOptions().position(latLng).title("New Place"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.d("App", marker.getId());
        return false;
    }
}
