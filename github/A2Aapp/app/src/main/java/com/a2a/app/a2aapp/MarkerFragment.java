package com.a2a.app.a2aapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


public class MarkerFragment extends Fragment {
    View v;
    String retail_id;
    String retail_name;
    TextView proceed;
    TextView retname;
    EditText pdt;
    SharedPreferences preferences;
        @Nullable
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_marker2, container, false);
            v=rootView;
            proceed = (TextView) rootView.findViewById(R.id.prc);
            pdt=(EditText)container.getRootView().findViewById(R.id.searchtext);
            return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                  Intent i = new Intent(getActivity(), SlidingHome.class);
                    startActivity(i);
                }
            }
        );
    }


}


