package com.a2a.app.a2aapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Mounika on 3/17/2016.
 */
public class SingleProductActivity extends Activity {

    // JSON Node names
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "retailer_name";
    private static final String TAG_PRODUCT = "product_name";
    private static final String TAG_CATEGORY = "category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String id = in.getStringExtra(TAG_ID);
        String name = in.getStringExtra(TAG_NAME);
        String product = in.getStringExtra(TAG_PRODUCT);
        String category = in.getStringExtra(TAG_CATEGORY);

        // Displaying all values on the screen
        TextView lblId = (TextView) findViewById(R.id.id);
        TextView lblRetailer = (TextView) findViewById(R.id.retailer);
        TextView lblProduct = (TextView) findViewById(R.id.product);
        TextView lblCategory = (TextView) findViewById(R.id.category);

        lblId.setText(id);
        lblRetailer.setText(name);
        lblProduct.setText(product);
        lblCategory.setText(category);

    }
}
