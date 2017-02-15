package com.a2a.app.a2aapp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Mounika on 3/10/2016.
 */
public class MyProfileFragment extends Fragment {

    public MyProfileFragment() {
    }

    private TextView mlname;
    private TextView mfname;
    private EditText mcontact;
    private EditText mpassword;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);

        mfname = (TextView) rootView.findViewById(R.id.fname);
        mlname = (TextView) rootView.findViewById(R.id.lname);
        mcontact = (EditText) rootView.findViewById(R.id.contact);
        mpassword = (EditText) rootView.findViewById(R.id.editpass);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        preferences =getActivity().getSharedPreferences("A2Auserdetails",Context.MODE_PRIVATE);
        String fname = preferences.getString("firstname", "Unknown");
        String lname=preferences.getString("lastname","Unknown");
        String password=preferences.getString("password","Unknown");
        String mobile=preferences.getString("phone","Unknown");
        mfname.setText(fname);
        mlname.setText(lname);
        mpassword.setText(password);
        mcontact.setText(mobile);
    }
}
