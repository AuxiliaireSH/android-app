package com.laxen.auxiliaire.tabUtils;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.laxen.auxiliaire.MainActivity;
import com.laxen.auxiliaire.R;
import com.laxen.auxiliaire.models.JobsModel;
import com.laxen.auxiliaire.tabs.JobsFragment;
import com.laxen.auxiliaire.tabs.ListTab.ListFragment;
import com.laxen.auxiliaire.tabs.MapFragmentTab;
import com.laxen.auxiliaire.tabs.ProfileFragment;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private final JobsModel jobs;

    // Fragments
    ListFragment listFragment;
    MapFragmentTab mapFragment;
    JobsFragment jobsFragment;
    ProfileFragment profileFragment;

    MainActivity context;

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, JobsModel jobs) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.jobs = jobs;

    }

    public void setContext (MainActivity context) {
        this.context = context;
    }

    public void initFragments(MapFragmentTab.MapFragmentListener listener) {
        initListFragment();
        initMapFragment(listener);
    }

    public void initListFragment() {
        listFragment = new ListFragment();
        listFragment.setJobsModel(jobs);
    }

    public void initJobsFragment() {
        jobsFragment = new JobsFragment();
    }

    public void initProfileFragment() {
        profileFragment = new ProfileFragment();
    }
    public void initMapFragment(MapFragmentTab.MapFragmentListener listener) {
        mapFragment = new MapFragmentTab();

        if (listener != null) {
            mapFragment.subscribe(listener);
        }
    }


    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                if (listFragment == null)
                    initListFragment();
                return listFragment;
            case 1:
                if (mapFragment == null)
                    initMapFragment(null);
                return mapFragment;
            case 2:
                if (jobsFragment == null)
                    initJobsFragment();
                return jobsFragment;
            case 3:
                if (profileFragment == null)
                    initProfileFragment();
                return profileFragment;
            default:
                return new ListFragment();
        }
    }

    public int[] tabIcons = {R.drawable.ic_list_white_24dp, R.drawable.ic_explore_white_24dp,
                                R.drawable.ic_work_white_24dp, R.drawable.ic_person_white_24dp};

    @Override
    public CharSequence getPageTitle(int position) {


        Drawable image = context.getResources().getDrawable(tabIcons[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        //image.setBounds(image.getIntrinsicWidth(), image.getIntrinsicHeight(), 0, 1);
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BASELINE);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
        //return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    public ListFragment getListFragment() {
        return listFragment;
    }
}
