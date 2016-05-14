package com.laxen.auxiliaire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by laxen on 5/14/16.
 */
public class JobFragment extends Fragment implements View.OnClickListener {

    private Button acceptButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_job,container,false);

        initUI(view);

        return view;
    }

    public void initUI(View view) {
        acceptButton = (Button) view.findViewById(R.id.acceptbtn);
        acceptButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Todo accept job
        //((MainActivity) getContext()).
    }
}
