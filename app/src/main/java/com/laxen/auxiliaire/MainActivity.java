package com.laxen.auxiliaire;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.laxen.auxiliaire.mapUtils.MapHandler;
import com.laxen.auxiliaire.models.Job;
import com.laxen.auxiliaire.models.JobsModel;
import com.laxen.auxiliaire.tabUtils.SlidingTabLayout;
import com.laxen.auxiliaire.tabUtils.ViewPagerAdapter;
import com.laxen.auxiliaire.tabs.MapFragmentTab;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        MapFragmentTab.MapFragmentListener{


    // google maps stuff
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private MapHandler mapHandler;
    private JobsModel jobsModel;
    public JobFragment jobFragment;
    public AddFragment addFragment;

    public Job currentJob;

    public Boolean isLAXEN false;

    public void setCurrentJob(Job job) {
        this.currentJob = job;
    }

    private final String url = "http://45.62.224.120:3000/jobs";

    // Toolbar toolbar;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    ViewPager pager;
    String tabNames[] = {"List", "Map", "My JobsModel", "Profile"};
    MapFragment mMapFragment;

    public SlidingTabLayout getTabs () {
        return tabs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobsModel = new JobsModel();
        jobFragment = new JobFragment();

        initToolBar();


        //VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequest);

        // TODO REMOVE
        // resets the fragment
        /*Fragment jobFragment = new JobFragment();

        android.support.v4.app.FragmentTransaction transaction;

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.appContainer, jobFragment).addToBackStack("jobFrag");
        transaction.commit();

        getSupportActionBar().hide();*/
    }

    // inits the toolbar, actionbar and tabs
    public void initToolBar() {

        // probably not needed
        // toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // setSupportActionBar(toolbar);

        // removes shadow of actionbar
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.aux);


        adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabNames, 4, jobsModel);
        adapter.setContext(this);

        // creates fragments in adapter
        adapter.initFragments(this);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        getSupportActionBar().show();

        initSlider();
    }

    public void initSlider() {
        // tabs for list and map
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setCustomTabView(R.layout.custom_tab, 0);
        // evens out the space between the tabs
        tabs.setDistributeEvenly(true);
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        // colors the scroller
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorTab);
            }
        });

        tabs.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
    }

    public void onMapFragmentCreated() {

        mMapFragment = MapFragment.newInstance();

        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, mMapFragment);
        fragmentTransaction.commit();

        mMapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapHandler = new MapHandler(mMap, this, jobsModel);

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        mMap.setOnMapClickListener(mapHandler);
        mMap.setOnMapLongClickListener(mapHandler);

        LatLng SWEDEN = new LatLng(62.2315, 16.1932);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SWEDEN, 4.5f));


        LocationResolver.LocationResult locationResult = new LocationResolver.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                if (location == null) {
                    Toast msg = Toast.makeText(MainActivity.this, "No position found", Toast.LENGTH_SHORT);
                    msg.show();
                    return;
                }
                LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mapHandler.zoomLocation(myLatLng);
            }
        };
        LocationResolver myLocation = new LocationResolver();
        if (!myLocation.getLocation(this, locationResult)) {
            Toast msg = Toast.makeText(this, "Please enable GPS and Network", Toast.LENGTH_SHORT);
            msg.show();
        }

        fetchData();
    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    public void popFragment() {
        getSupportFragmentManager().popBackStack();
    }

    // fetches data from server
    public void fetchData() {

        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequest);
    }

    final GsonRequest gsonRequest = new GsonRequest(url, Job[].class, null, new Response.Listener<Job[]>() {

        @Override
        public void onResponse(Job[] jobs) {

            jobsModel.setJobs(Arrays.asList(jobs));
            MainActivity.this.mapHandler.populateMap();
            adapter.getListFragment().populuateList();
            Toast.makeText(MainActivity.this, "Jobs refreshed", Toast.LENGTH_SHORT).show();

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
            Toast.makeText(MainActivity.this, "Server not reached", Toast.LENGTH_SHORT).show();

        }
    });

    public void createMap() {
        if(isLAXEN){
            mapHandler.populateMap();
        }
    }

}
