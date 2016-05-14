package com.laxen.auxiliaire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by laxen on 5/14/16.
 */
public class JobFragment extends Fragment implements View.OnClickListener {

    private Button acceptButton;
    private Integer userID;
    private TextView nameText;
    private TextView titleText;
    private TextView priceText;
    private Job currentJob;

    private TextView descText;
    private TextView positionText;

    public void setCurrentJob (Job job) {
        currentJob = job;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job,container,false);

        initUI(view);

        return view;
    }

    public void initUI(View view) {
        acceptButton = (Button) view.findViewById(R.id.acceptbtn);
        acceptButton.setOnClickListener(this);
        nameText = (TextView) view.findViewById(R.id.nameText);

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
