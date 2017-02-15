package com.a2a.app.a2aapp;

import android.*;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mounika on 3/10/2016.
 */
public class CallOperatorFragment extends Fragment {

    public CallOperatorFragment(){}

    private TextView mtext;
    private Button mclick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calloperator, container, false);
        mtext = (TextView)rootView.findViewById(R.id.text);
        mclick= (Button)rootView.findViewById(R.id.call);

        mclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callintent = new Intent(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:9753320037"));
                startActivity(callintent);
            }
        });
        return rootView;
    }

}

