package com.laxen.auxiliaire.mapUtils;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laxen.auxiliaire.JobFragment;
import com.laxen.auxiliaire.MainActivity;
import com.laxen.auxiliaire.R;

/**
 * Created by laxen on 5/14/16.
 */
public class MapHandler implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private MainActivity context;
    private FragmentTransaction transaction;

    public MapHandler(GoogleMap map, MainActivity context) {
        this.context = context;
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
        popFragment();

        // resets the fragment
        Fragment jobFragment = new JobFragment();

        transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.appContainer, jobFragment).addToBackStack("jobFrag");
        transaction.commit();

        context.getTabs().removeAllViews();
        return false;
    }

    // pops a fragment from the backstack
    public void popFragment() {

        FragmentManager fragmentManager = context.getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
