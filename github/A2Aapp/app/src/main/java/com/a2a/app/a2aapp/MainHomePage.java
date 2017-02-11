package com.a2a.app.a2aapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mounika on 3/3/2016.
 */
public class MainHomePage extends Activity {

    Button customer;
    Button retailor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailor_homepage);
        customer = (Button)findViewById(R.id.customer);
        retailor = (Button)findViewById(R.id.retailor);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainHomePage.this,HomeActivity.class);
                startActivity(i);
            }
        });

        retailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainHomePage.this,RetailorHomeActivity.class);
                startActivity(i);
            }
        });
    }
}

