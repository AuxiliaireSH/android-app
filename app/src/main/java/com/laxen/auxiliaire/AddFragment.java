package com.laxen.auxiliaire;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.UiThread;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.laxen.auxiliaire.models.Job;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laxen on 5/14/16.
 */
public class AddFragment extends Fragment {

    private View background;

    private Button addButton;
    private EditText nameEdit;
    private Spinner categories;
    private EditText titleEdit;
    private EditText priceEdit;

    private TextView descText;


    final int[] startColors = {   R.color.colorHandiworkStart, R.color.colorComputerStart,
            R.color.colorStudiesStart, R.color.colorCookingStart, R.color.colorOtherStart};

    final int[] endColors = {   R.color.colorHandiworkEnd, R.color.colorComputerEnd,
            R.color.colorStudiesEnd, R.color.colorCookingEnd, R.color.colorOtherEnd};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        initUI(view);

        return view;
    }

    public void initUI(View view) {

        background = view.findViewById(R.id.add_background);

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

                updateBackgroundColor(startColors[position], endColors[position]);

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

                String url = "http://10.0.2.2:3000/jobs";

                Map<String, String> jobMap = new HashMap<String, String>();
                jobMap.put("title", job.getTitle());
                jobMap.put("description", job.getDescription());
                jobMap.put("price", job.getPrice().toString());
                jobMap.put("kind", job.getKind());
                jobMap.put("latitude", job.getLatitude().toString());
                jobMap.put("longitude", job.getLongitude().toString());
                jobMap.put("user_id", "1"); //TODO add user here


                GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, url, Job[].class, null,
                        jobMap, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Add toast here
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Add toast here
                                if (null != error.networkResponse) {
                                    Log.d("e13" + ": ", "Error Response code: " + error.networkResponse.statusCode);
                                }
                            }
                        });
                VolleyHelper.getInstance(getContext()).addToRequestQueue(gsonRequest);
            }
        });
    }

    @UiThread
    public void updateBackgroundColor(int colorStart, int colorEnd) {

        Log.d("test", "updating color");

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {getResources().getColor(colorStart), getResources().getColor(colorEnd)});

        Drawable[] grads = {background.getBackground(), gd};

        TransitionDrawable transitionDrawable = new TransitionDrawable(grads);
        background.setBackgroundDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);


        //background.setBackgroundColor(getResources().getColor(R.color.colorComputer));


        Log.d("test", "background: " + background.getBackground());

    }


}
