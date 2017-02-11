package com.a2a.app.a2aapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mounika on 4/1/2016.
 */
public class HomeRetFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public HomeRetFragment(){}

    EditText mproduct,mprice;
    Button mupload;
    TextView mcategory;
    String UPLOAD_URL;
    String jsonresponce=null,enteredpice;
    ProgressDialog pd;
    private Spinner spinner;
    private Spinner per_spinner;
    TextView productsnum;
    String enteredRetailorname,enteredCategory,enteredProduct;
    String result = null;
    String getretId;

    List<String> list;
    List<String> per_list;
    boolean connection = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_homeret,container,false);


        try {
            FileInputStream file = getActivity().openFileInput("retailordet.txt");
            InputStreamReader reader= new InputStreamReader(file);
            BufferedReader buffer= new BufferedReader(reader);
            getretId=buffer.readLine();

        }catch (IOException e){
            e.printStackTrace();
        }
        mupload = (Button)rootView.findViewById(R.id.upload);
        mproduct = (EditText)rootView.findViewById(R.id.product);
        mprice=(EditText)rootView.findViewById(R.id.price);
        mcategory = (TextView)rootView.findViewById(R.id.category);
        spinner = (Spinner)rootView.findViewById(R.id.spinner);
        per_spinner=(Spinner)rootView.findViewById(R.id.perspinner);
        productsnum = (TextView)rootView.findViewById(R.id.productsnum);
        spinner.setOnItemSelectedListener(HomeRetFragment.this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Groceries");
        categories.add("Fruits");
        categories.add("Vegetables");
        categories.add("Cosmetics");
        categories.add("Snacks");
        categories.add("Beverages");
        categories.add("Branded Foods");

        getretId = ReatailorSignin.retId;
        Log.e("getretid",getretId);

        List<String> per=new ArrayList<String>();
        per.add("Kilogram");
        per.add("Litre");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> peradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, per);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        per_spinner.setAdapter(peradapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enteredCategory = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredProduct = mproduct.getText().toString();
                Log.e("product", enteredProduct);
                enteredpice=mprice.getText().toString();
                Log.e("price",enteredpice);
                //http://a2a.co.in/webservice2/addproduct.php?retailer=1&product=2&price=123&category=3&subcategory=4&retailerid=12
                UPLOAD_URL = "http://a2a.co.in/webservice2/addproduct.php?retailer=1" + "&product=" + enteredProduct + "&price="+enteredpice+"&category=" + enteredCategory+"&retailerid="+getretId;
                Log.e("finalurl",UPLOAD_URL);
                new AsyncDataClass().execute();
            }
        });

        return rootView;
    }

   /* @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref=this.getActivity().getSharedPreferences("retdetails", Context.MODE_PRIVATE);
        enteredRetailorname=pref.getString("retname","");
        retId=pref.getString("retid","");
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        enteredCategory=item;
        // Showing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        //set as selected item.
        spinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        enteredCategory=null;
    }

    private class AsyncDataClass extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(UPLOAD_URL);
                String res;

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                conn.setDoInput(true);
                conn.connect();
                res = conn.getResponseMessage();

                Log.e("res", res);

                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;

                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    in.close();
                    reader.close();
                    jsonresponce = sb.toString();
                    Log.e("jsondata", jsonresponce);
                    //  Toast.makeText(getApplicationContext(),jsonresponce,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonresponce;

        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();

            if(jsonresponce!= null) {
                Toast.makeText(getActivity(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(),"Please Try Again",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }
}

