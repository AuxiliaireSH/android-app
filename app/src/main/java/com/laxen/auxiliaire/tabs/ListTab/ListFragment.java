package com.laxen.auxiliaire.tabs.ListTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laxen.auxiliaire.Job;
import com.laxen.auxiliaire.R;

import java.util.ArrayList;
import java.util.Date;

public class ListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<Job> jobList = new ArrayList<>();
        jobList.add(new Job(1, "User", 500, "Plumber", 43.421421, 44.21321, new Date(), "This is the descriptions of the job", "Wash my car"));
        jobList.add(new Job(2, "User", 500, "Plumber", 43.421421, 44.21321, new Date(), "This is the descriptions of the job", "Shitters Clogged"));
        jobList.add(new Job(3, "User", 500, "Plumber", 43.421421, 44.21321, new Date(), "This is the descriptions of the job", "Shitters Clogged"));
        jobList.add(new Job(4, "User", 500, "Plumber", 43.421421, 44.21321, new Date(), "This is the descriptions of the job", "Shitters Clogged"));
        mAdapter = new ListAdapter(jobList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
