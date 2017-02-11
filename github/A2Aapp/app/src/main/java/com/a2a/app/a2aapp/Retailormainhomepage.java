package com.a2a.app.a2aapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mounika on 3/8/2016.
 */
public class Retailormainhomepage extends Activity implements AdapterView.OnItemSelectedListener {

    EditText mname;
    TextView mcategory;
    EditText mproduct;
    Button mupload, mproducts;
    String enteredRetailorname, enteredCategory, enteredProduct;
    String enteredid, enteredRetailor_name, enteredCategory_id, enteredProduct_name;
    String jsonresponce = null;
    String UPLOAD_URL, PRODUCTS_URL;
    ProgressDialog pd;
    ImageView mimageback;

    InputStream is = null;

    private Spinner spinner;
    String result = null;

    List<String> list;
    boolean connection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailorspinner);
        mimageback = (ImageView) findViewById(R.id.imageback);
        // mname = (EditText)findViewById(R.id.rname);
        mcategory = (TextView) findViewById(R.id.category);

      //  mupload = (Button) findViewById(R.id.select);
      //  spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(Retailormainhomepage.this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Groceries");
        categories.add("Fruits");
        categories.add("Vegetables");
        categories.add("Cosmetics");
        categories.add("Snacks");
        categories.add("Beverages");
        categories.add("Branded Foods");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // mproducts = (Button)findViewById(R.id.products);
        mimageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Retailormainhomepage.this, ReatailorSignin.class);
                startActivity(i);
            }
        });

      /*  ArrayList<String>categories = getCategories("categories.json");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,categories);
        spinner.setAdapter(adapter); */

        mupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  enteredRetailorname = mname.getText().toString();
                Log.e("name", enteredRetailorname);
                enteredCategory = mcategory.getText().toString();
                Log.e("category", enteredCategory);
                enteredProduct = mproduct.getText().toString();
                Log.e("product", enteredProduct);

                UPLOAD_URL = "http://webservices.prolink-technologies.com/webservice2/addproduct.php?retailer=" + enteredRetailorname + "&product=" + enteredProduct + "&category=" + enteredCategory;
                Log.e("finalurl", UPLOAD_URL);

                if (enteredRetailorname.equals("") || enteredCategory.equals("") || enteredProduct.equals("")) {
                    Toast.makeText(Retailormainhomepage.this, "All Fields are Mandatory", Toast.LENGTH_LONG).show();
                    return;
                }

                new AsyncDataClass().execute();*/
                Intent i = new Intent(Retailormainhomepage.this, UploadPageActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        //set as selected item.
        spinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
