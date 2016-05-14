package com.laxen.auxiliaire.mapUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laxen.auxiliaire.Job;
import com.laxen.auxiliaire.JobFragment;
import com.laxen.auxiliaire.JobsModel;
import com.laxen.auxiliaire.MainActivity;
import com.laxen.auxiliaire.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by laxen on 5/14/16.
 */
public class MapHandler implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private MainActivity context;
    private FragmentTransaction transaction;
    private JobsModel jobModel;
    public Map<Marker, Job> markerJobMap = new HashMap<Marker, Job>();

    public MapHandler(GoogleMap map, MainActivity context, JobsModel jobModel) {
        this.jobModel = jobModel;
        this.context = context;
        this.map = map;
        this.map.setOnMarkerClickListener(this);
    }

    public void populateMap () {
        Job job1 = new Job();
        job1.setLat(51.5074);
        job1.setLon(0.1278);
        job1.setUsername("TRIST");

        Job job2 = new Job();
        job2.setLat(40.7128);
        job2.setLon(74.0059);
        job2.setUsername("ROLIGT");

        jobModel.getJobs().add(job1);
        jobModel.getJobs().add(job2);

        for (Job job : jobModel.getJobs()) {
            LatLng point = new LatLng(job.getLat(), job.getLon());

            Marker m = map.addMarker(new MarkerOptions().position(point).title(job.getTitle()));
            markerJobMap.put(m, job);
            Log.d("app", "ADDED JOB TO MAP");
        }
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
        context.popFragment();

        // resets the fragment
        JobFragment jobFragment = new JobFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userid", markerJobMap.get(marker).getUsername());
        jobFragment.setArguments(bundle);

        transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.appContainer, jobFragment).addToBackStack("jobFrag");
        transaction.commit();

        //context.getSupportActionBar().hide();
        //context.getTabs().removeAllViews();
        return false;
    }

}
