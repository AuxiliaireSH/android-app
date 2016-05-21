package com.laxen.auxiliaire;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.laxen.auxiliaire.models.Job;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by laxen on 5/14/16.
 */
public class AddFragment extends Fragment {

    private Button addButton;
    private ImageButton exitButton;
    private Integer userID;
    private EditText nameEdit;
    private Spinner categories;
    private ExpandableListAdapter listAdapter;
    private EditText titleEdit;
    private EditText priceEdit;
    private Job currentJob;

    private TextView descText;
    private TextView positionText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        initUI(view);

        return view;
    }

    public void initUI(View view) {
        addButton = (Button) view.findViewById(R.id.addBtn);
        nameEdit = (EditText) view.findViewById(R.id.nameEdit);
        titleEdit = (EditText) view.findViewById(R.id.titleEdit);
        priceEdit = (EditText) view.findViewById(R.id.priceEdit);
        descText = (EditText) view.findViewById(R.id.descEdit);
        categories = (Spinner) view.findViewById(R.id.categories);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, R.layout.cat_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories.setAdapter(adapter);
        categories.setSelection(0);

        initCategoires();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job job = new Job(0,
                        nameEdit.getText().toString(),
                        Integer.parseInt(priceEdit.getText().toString()),
                        categories.getSelectedItem().toString(),
                        11.974560,
                        57.708870,
                        new Date(),
                        new Date(),
                        descText.getText().toString(),
                        titleEdit.getText().toString());

                ((MainActivity)getContext()).getJobsModel().getMyJobs().add(job);

                ((MainActivity)getContext()).getJobsModel().getJobs().add(job);

                // ((MainActivity)getContext()).fetchData(); TODO use this instead
                ((MainActivity)getContext()).refreshJobs();
                ((MainActivity)getContext()).popFragment();


                // TODO SKICKA UPP SKIT HÄR JOHAN
            }
        });

        exitButton = (ImageButton) view.findViewById(R.id.exiticon);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).popFragment();
            }
        });
    }

    public void initCategoires() {


    }
}
