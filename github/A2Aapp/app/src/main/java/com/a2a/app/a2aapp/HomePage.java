package com.a2a.app.a2aapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Mounika on 3/3/2016.
 */
public class HomePage extends Activity {

    ImageView ma2a;
    Button mcustomersignin;
    Button mretailorsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      // ma2a = (ImageView)findViewById(R.id.a2a);
        mcustomersignin = (Button)findViewById(R.id.customersignin);
        mretailorsignin = (Button)findViewById(R.id.retailorsignin);

        mcustomersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,Signin.class);
                startActivity(i);
            }
        });
        mretailorsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,ReatailorSignin.class);
                startActivity(i);
            }
        });
    }
}

