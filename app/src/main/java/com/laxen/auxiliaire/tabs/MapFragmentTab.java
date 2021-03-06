package com.laxen.auxiliaire.tabs;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laxen.auxiliaire.R;

import java.util.ArrayList;

public class MapFragmentTab extends Fragment {

    public interface MapFragmentListener {
        void onMapFragmentCreated();
    }

    ArrayList<MapFragmentListener> subs;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        if(subs == null) {
            subs = new ArrayList<>();
        }

        for (MapFragmentListener listener : subs) {
            listener.onMapFragmentCreated();
        }


        return view;
    }

    public void subscribe(MapFragmentListener listener) {
        if (subs == null) {
            subs = new ArrayList<>();
        }

        subs.add(listener);
    }

}
