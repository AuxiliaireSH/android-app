package com.laxen.auxiliaire;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

    private TextView descText;
    private TextView positionText;

    private int currentColor;


    // UI components for ripple effect on color change
    private View mRevealView;
    private View mRevealBackgroundView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        initUI(view);

        return view;
    }

    public void initUI(View view) {

        // current color to be used by animator
        currentColor = R.color.colorPrimary;

        // hides toolbar
        ((MainActivity)getContext()).getMToolBar().setElevation(0);

        mRevealView = view.findViewById(R.id.reveal);
        mRevealBackgroundView = view.findViewById(R.id.revealBackground);

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

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
                final int color;

                switch (position) {
                    case 0 : // handiwork
                        color = R.color.colorPrimary;
                        break;
                    case 1 : // computers
                        color = R.color.colorComputer;
                        break;
                    case 2 : // studies
                        color = R.color.colorStudies;
                        break;
                    case 3 : // cooking
                        color = R.color.colorCooking;
                        break;
                    case 4 : // other
                        color = R.color.colorOther;
                        break;
                    default:
                        color = R.color.colorPrimary;
                }

                // in order to avoid unnecessary animation
                if(color != currentColor) {
                    ((MainActivity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            animateColor(color);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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


    public void animateColor(final int color) {
        Animator animator = ViewAnimationUtils.createCircularReveal(
                mRevealView,
                mRevealView.getWidth() / 2,
                mRevealView.getHeight() / 2, 0,
                mRevealView.getWidth() / 2);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRevealView.setBackgroundColor(getResources().getColor(color));
            }
        });

        mRevealBackgroundView.setBackgroundColor(getResources().getColor(currentColor));
        animator.setStartDelay(100);
        animator.setDuration(250);
        animator.start();
        mRevealView.setVisibility(View.VISIBLE);

        currentColor = color;
    }
}
