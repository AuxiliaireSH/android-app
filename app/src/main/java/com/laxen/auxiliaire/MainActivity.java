package com.laxen.auxiliaire;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.laxen.auxiliaire.mapUtils.MapHandler;
import com.laxen.auxiliaire.models.Job;
import com.laxen.auxiliaire.models.JobsModel;
import com.laxen.auxiliaire.tabs.JobsFragment;
import com.laxen.auxiliaire.tabs.ListTab.ListAdapter;
import com.laxen.auxiliaire.tabs.ListTab.ListFragment;
import com.laxen.auxiliaire.tabs.MapFragmentTab;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, ListAdapter.ListAdapterListener {

    static final int ADD_JOB_REQUEST = 1337;


    // google maps stuff
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private MapHandler mapHandler;
    private JobsModel jobsModel;
    //public AddActivity addFragment;

    // fragments
    private ListFragment listFragment;
    private MapFragmentTab mapFragment;
    private JobsFragment jobsFragment;
    private UserFragment profileFragment;

    private RelativeLayout addText;
    private TextView titleText;
    private Job currentJob;

    public Boolean isLAXEN = false;

    private String idFragment = "ListFragment";

    private BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    idFragment = "ListFragment";
                    switchContent("ListFragment");
                    return true;
                case R.id.navigation_map:
                    idFragment = "MapFragmentTab";
                    switchContent("MapFragmentTab");
                    return true;
                case R.id.navigation_profile:
                    idFragment = "ProfileFragment";
                    switchContent("ProfileFragment");
                    return true;
            }

            return false;
        }
    };


    public void setCurrentJob(Job job) {
        this.currentJob = job;
    }

    private final String url = "http://10.0.2.2:3000/jobs";

    //ViewPagerAdapter adapter;
    //SlidingTabLayout tabs;
    //ViewPager pager;
    //MapFragment mMapFragment;

    /*public SlidingTabLayout getTabs () {
        return tabs;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobsModel = new JobsModel();

        titleText = (TextView) findViewById(R.id.title_text);

        addText = (RelativeLayout) findViewById(R.id.adder_button);
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addIntent, ADD_JOB_REQUEST);


                /*android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(R.id.content, addActivity).addToBackStack("jobFrag");
                transaction.commit();*/
            }
        });

        idFragment = "ListFragment";
        createFragments();


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, listFragment).commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Toolbar toolBar = (Toolbar)findViewById(R.id.home_toolbar);
        //setSupportActionBar(toolBar);
        //initToolBar();


    }

    public void createFragments() {
        listFragment = new ListFragment();
        listFragment.setJobsModel(jobsModel);
        listFragment.setAdapterListener(this);

        jobsFragment = new JobsFragment();
        profileFragment = new UserFragment();

        mapFragment = new MapFragmentTab();

        /*mMapFragment = MapFragment.newInstance();
        mMapFragment.getMapAsync(this);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_JOB_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "All good in the hood", Toast.LENGTH_SHORT).show();
                String result = data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    // inits the toolbar, actionbar and tabs
    /*public void initToolBar() {

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabNames, 4, jobsModel);
        adapter.setContext(this);

        // creates fragments in adapter
        adapter.initFragments(this);

        // sets pager for sliding tabs
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

    }*/

    private void switchContent(String content){

        Fragment fragment;
        String title;

        switch (content){
            case "ListFragment":
                fragment = jobsFragment;
                title = "All jobs";
                break;
            case "MapFragmentTab":
                fragment = mapFragment;
                title = "All jobs";
                break;
            case "JobsFragment":
                fragment = jobsFragment;
                title = "Your jobs";
                break;
            case "ProfileFragment":
                fragment = profileFragment;
                title = "Your profile";
                break;
            default:
                fragment = jobsFragment;
                title = "All jobs";
                break;
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, fragment).addToBackStack(content).commit();
        //getSupportActionBar().setTitle(title);
        titleText.setText(title);
    }

    /*public void initSlider() {



        // tabs for list and map
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setCustomTabView(R.layout.custom_tab, 0);
        // evens out the space between the tabs
        tabs.setDistributeEvenly(true);
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        tabs.setSelectedIndicatorColors(getResources().getColor(transparent));

        tabs.setVisibility(View.VISIBLE);

        tabs.addTabListener(this);
    }*/

    /*public void onMapFragmentCreated() {

        mMapFragment = MapFragment.newInstance();

        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, mMapFragment);
        fragmentTransaction.commit();

        mMapFragment.getMapAsync(this);

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
                if(isLAXEN){
                    mapHandler.zoomLocation(myLatLng);
                }
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

    final GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, url, Job[].class, null, null, new Response.Listener<Job[]>() {

        @Override
        public void onResponse(Job[] jobs) {

            jobsModel.setJobs(Arrays.asList(jobs));
            Toast.makeText(MainActivity.this, "Jobs refreshed", Toast.LENGTH_SHORT).show();

            refreshJobs();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
            Toast.makeText(MainActivity.this, "Server not reached", Toast.LENGTH_SHORT).show();

        }
    });


    public void refreshJobs() {
        if(mapHandler != null) {
            mapHandler.populateMap();
        }

        listFragment.populuateList();
    }

    public void createMap() {
        if(isLAXEN){
            mapHandler.populateMap();
        }
    }

    public JobsModel getJobsModel () {
        return jobsModel;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    @Override
    public void onJobClicked(Job job) {
        popFragment();
        currentJob = job;
        setCurrentJob(job);

        JobFragment jobFragment = new JobFragment();

        android.support.v4.app.FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.content, jobFragment).addToBackStack("jobFrag");
        transaction.commit();
    }
}
