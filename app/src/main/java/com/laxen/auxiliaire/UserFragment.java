package com.laxen.auxiliaire;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by laxen on 5/14/16.
 */
public class UserFragment extends Fragment{



    // code for init all the user
    // horv is the default for now
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);


        return view;
    }
}
