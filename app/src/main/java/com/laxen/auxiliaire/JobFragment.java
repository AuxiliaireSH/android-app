package com.laxen.auxiliaire;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.laxen.auxiliaire.models.Job;

import org.w3c.dom.Text;

/**
 * Created by laxen on 5/14/16.
 */
public class JobFragment extends Fragment implements View.OnClickListener {

    private Button acceptButton;
    private ImageButton exitButton;
    private Integer userID;
    private TextView nameText;
    private TextView jobTypeText;
    private TextView titleText;
    private TextView priceText;
    private Job currentJob;

    private TextView descText;
    private TextView positionText;

    // UI components for ripple effect on color change
    private View rippleView;
    private View rippleBackView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job, container, false);
        currentJob = ((MainActivity)getContext()).getCurrentJob();

        initUI(view);

        return view;
    }

    public void initUI(View view) {
        // hides toolbar
        ((MainActivity)getContext()).getMToolBar().setElevation(0);

        rippleView = view.findViewById(R.id.reveal);
        rippleBackView = view.findViewById(R.id.revealBackground);

        acceptButton = (Button) view.findViewById(R.id.acceptbtn);
        acceptButton.setOnClickListener(this);

        exitButton = (ImageButton) view.findViewById(R.id.exiticon);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).popFragment();
            }
        });


        nameText = (TextView) view.findViewById(R.id.nameText);
        titleText = (TextView) view.findViewById(R.id.helpTitle);
        descText = (TextView) view.findViewById(R.id.descText);
        jobTypeText = (TextView) view.findViewById(R.id.jobTypeText);
        priceText = (TextView) view.findViewById(R.id.priceText);

        if(currentJob != null) {
            nameText.setText(currentJob.getUsername());
            titleText.setText(currentJob.getTitle());
            jobTypeText.setText(currentJob.getKind());
            priceText.setText(currentJob.getPrice()+"");
            descText.setText(currentJob.getDescription());
        }

        // set color depending on job
        colorize();
    }

    @Override
    public void onClick(View v) {
        // Todo accept job
        ((MainActivity) getContext()).popFragment();
        ((MainActivity) getContext()).initToolBar();
        Toast msg = Toast.makeText(getContext(), "Job accepted!", Toast.LENGTH_SHORT);
        msg.show();
    }

    // colors the UI components depending on job category
    public void colorize() {
        int color = ((MainActivity)getContext())
                .getJobsModel()
                .getCatToColor()
                .get(currentJob
                        .getKind());

        rippleView.setBackgroundColor(getResources().getColor(color));
        rippleBackView.setBackgroundColor(getResources().getColor(color));

    }
}
