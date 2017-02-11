package com.a2a.app.a2aapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class SingleRetailorActivity extends Activity{

private static final String TAG_NAME = "name";
private static final String TAG_SHOPNAME = "shop_name";
private static final String TAG_ADDRESS = "address";
private static final String TAG_LOCATION = "location";
private static final String TAG_ABOUTSHOP = "about_shop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_retailor);
        //Bundle data=getIntent().getExtras();
        Intent i = getIntent();

        String name = i.getStringExtra(TAG_NAME);
        String shopname = i.getStringExtra(TAG_SHOPNAME);
        String address= i.getStringExtra(TAG_ADDRESS);
        String aboutshop = i.getStringExtra(TAG_ABOUTSHOP);
       // String retname=data.getString("retusername");

        TextView lblName = (TextView) findViewById(R.id.ret_name);
        TextView lblshopname = (TextView) findViewById(R.id.shopname);
        TextView lbladdress = (TextView) findViewById(R.id.address);
        TextView lblaboutshop = (TextView) findViewById(R.id.aboutshop);

        lblName.setText(name);
        lblshopname.setText(shopname);
        lbladdress.setText(address);
        lblaboutshop.setText(aboutshop);
    }
}
