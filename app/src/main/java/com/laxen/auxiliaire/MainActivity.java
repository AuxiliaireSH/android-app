package com.laxen.auxiliaire;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.laxen.auxiliaire.mapUtils.MapHandler;
import com.laxen.auxiliaire.tabUtils.SlidingTabLayout;
import com.laxen.auxiliaire.tabUtils.ViewPagerAdapter;
import com.laxen.auxiliaire.tabs.MapFragmentTab;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        MapFragmentTab.MapFragmentListener {


    // google maps stuff
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private MapHandler mapHandler;

    // Toolbar toolbar;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    ViewPager pager;
    String tabNames[] = {"List", "Map", "My Jobs", "Profile"};
    MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
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


        adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabNames, 4);
        adapter.setContext(this);

        // creates fragments in adapter
        adapter.initFragments(this);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

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
        mapHandler = new MapHandler(mMap, this);

        mMap.setOnMyLocationButtonClickListener(this);
        //enableMyLocation(); TODO ENABLE THIS WHEN NOT DEBUGGING

        mMap.setOnMapClickListener(mapHandler);
        mMap.setOnMapLongClickListener(mapHandler);
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
}