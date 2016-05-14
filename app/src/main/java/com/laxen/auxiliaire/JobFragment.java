package com.laxen.auxiliaire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by laxen on 5/14/16.
 */
public class JobFragment extends Fragment implements View.OnClickListener {

    private Button acceptButton;
    private Integer userID;
    private TextView nameText;
    private TextView jobTypeText;
    private TextView titleText;
    private TextView priceText;
    private Job currentJob;

    private TextView descText;
    private TextView positionText;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_job, container, false);

        initUI(view);

        if (savedInstanceState != null) {
            nameText.setText(savedInstanceState.getString("nameText"));
            titleText.setText(savedInstanceState.getString("titleText"));
            jobTypeText.setText(savedInstanceState.getString("jobTypeText"));
            priceText.setText(savedInstanceState.getString("priceText"));
            positionText.setText(savedInstanceState.getString("positionText"));
        }



        return view;
    }

    public void initUI(View view) {
        acceptButton = (Button) view.findViewById(R.id.acceptbtn);
        acceptButton.setOnClickListener(this);

        nameText = (TextView) view.findViewById(R.id.nameText);
        titleText = (TextView) view.findViewById(R.id.helpTitle);
        jobTypeText = (TextView) view.findViewById(R.id.jobTypeText);
        priceText = (TextView) view.findViewById(R.id.priceText);
        positionText = (TextView) view.findViewById(R.id.positionText);


        fillTextFields();
    }

    public void fillTextFields() {

    }

    @Override
    public void onClick(View v) {
        // Todo accept job
        ((MainActivity) getContext()).popFragment();
        ((MainActivity) getContext()).initToolBar();
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setNameText(TextView nameText) {
        this.nameText = nameText;
    }

    public void setTitleText(TextView titleText) {
        this.titleText = titleText;
    }

    public void setPriceText(TextView priceText) {
        this.priceText = priceText;
    }

    public void setDescText(TextView descText) {
        this.descText = descText;
    }

    public void setPositionText(TextView positionText) {
        this.positionText = positionText;
    }
}
