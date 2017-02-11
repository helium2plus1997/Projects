package com.a2a.app.a2aapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mounika on 3/3/2016.
 */
public class RetailorHomeActivity extends Activity {

    Button msignin;
    Button mregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailor_homeactivity);

        msignin = (Button)findViewById(R.id.signin);
        mregister = (Button)findViewById(R.id.register);

        msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RetailorHomeActivity.this,ReatailorSignin.class);
                startActivity(i);
            }
        });

        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RetailorHomeActivity.this,RetailorRegistration.class);
                startActivity(i);
            }
        });
    }
}

