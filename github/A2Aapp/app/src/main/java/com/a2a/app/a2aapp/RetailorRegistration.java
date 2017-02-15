package com.a2a.app.a2aapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Mounika on 3/3/2016.
 */
public class RetailorRegistration extends Activity {

    EditText mname;
    EditText mshopname;
    EditText musername,mpassword;
    EditText mlatitude;
    EditText mlongitude;
    EditText maddress;
    EditText maboutshop;
    Button mretregister;
    ImageView gmapbtn;
    ImageView mimage,mimageback;
    TextView mtxname,mtxshopname,mtxtaddress,mtxtlocation,
            mtxusername,mtxaboutshop,mtxpassword;
    String enteredretailername, enteredshopname,enteredaddress,enteredUserName,enteredpassword,
            latitude,longitude,enteredaboutshop;
    String RETAILORREG_URL;
    String jsonresponce;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailor_register);

        mtxname = (TextView)findViewById(R.id.txtName);
        mtxtaddress=(TextView)findViewById(R.id.txtaddr);
        mtxshopname = (TextView)findViewById(R.id.txshopname);
        gmapbtn=(ImageView)findViewById(R.id.gmapbtn);
      //  mregsdress = (TextView)findViewById(R.id.txregaddress);
        mtxusername = (TextView)findViewById(R.id.txusername);
        mtxaboutshop = (TextView)findViewById(R.id.txaboutshop);
        mtxpassword = (TextView)findViewById(R.id.txpassword);
        mtxtaddress=(TextView)findViewById(R.id.txtlocation);


        mname = (EditText)findViewById(R.id.name);
        mshopname = (EditText)findViewById(R.id.shopname);
        maddress=(EditText)findViewById(R.id.addr);
      //  mRegAddress = (EditText)findViewById(R.id.regaddress);
        musername = (EditText)findViewById(R.id.username);
        mlatitude = (EditText)findViewById(R.id.latitude);
        mlongitude = (EditText)findViewById(R.id.longitude);
        mpassword = (EditText)findViewById(R.id.etpassword);
        maboutshop = (EditText)findViewById(R.id.aboutshop);
        mretregister = (Button)findViewById(R.id.retailerreg);
        mimage = (ImageView)findViewById(R.id.retailor);
        mimageback = (ImageView)findViewById(R.id.imageback);

        mimageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RetailorRegistration.this,RetailorHomeActivity.class);
                startActivity(i);
            }
        });

        gmapbtn.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent i=new Intent(RetailorRegistration.this,MapView1.class);
                        i.putExtra("type",2);
                        startActivityForResult(i, 1);
                    }
                }
        );

        mretregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredretailername = mname.getText().toString();
                Log.e("name",enteredretailername);
                enteredshopname = mshopname.getText().toString();
                Log.e("name",enteredshopname);
                enteredaddress=maddress.getText().toString();
                Log.e("name",enteredaddress);
                latitude=mlatitude.getText().toString();
                Log.e("name",latitude);
                longitude=mlongitude.getText().toString();
                Log.e("name",longitude);
                enteredUserName = musername.getText().toString();
                Log.e("name",enteredUserName);
                enteredpassword = mpassword.getText().toString();
                Log.e("name",enteredpassword);
               /* enteredlocation = mlocation.getText().toString();
                Log.e("name",enteredlocation);*/
                enteredaboutshop = maboutshop.getText().toString();
                Log.e("name",enteredaboutshop);

                //RETAILORREG_URL = "http://webservices.prolinktechnosoft.com/webservice2/retailer_register.php?retailername="+enteredretailername+"&shopname="+enteredshopname+"&address="+enteredaddress+"&UserName="+enteredUserName+"&password="+enteredpassword+"&location="+enteredlocation+"&aboutshop="+enteredaboutshop;

                RETAILORREG_URL="http://a2a.co.in/webservice2/retailer_register.php?retailername="+ URLEncoder.encode(enteredretailername)+"&shopname="+URLEncoder.encode(enteredshopname)+"&UserName="+URLEncoder.encode(enteredUserName)+"&password="+URLEncoder.encode(enteredpassword)+"&location="+URLEncoder.encode(enteredaddress)+"&aboutshop="+URLEncoder.encode(enteredaboutshop)+"&longitude="+URLEncoder.encode(longitude)+"&latitude="+URLEncoder.encode(latitude);

                Log.e("finalurl", RETAILORREG_URL);

                if (enteredretailername.equals("") || enteredshopname.equals("") ||enteredaddress.equals("") || enteredUserName.equals("")||enteredpassword.equals("") ||
                        latitude.equals("")|| enteredaboutshop.equals("")||latitude.equals("")) {
                    Toast.makeText(RetailorRegistration.this, "All Fields are Mandatory", Toast.LENGTH_LONG).show();
                    return;
                }
                new AsyncDataClass().execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1)
        {
            String latitude=data.getStringExtra("Latitude");
            String longitude=data.getStringExtra("Longitude");

            mlatitude.setText(latitude);
            mlongitude.setText(longitude);
        }
        else{
            Toast.makeText(RetailorRegistration.this,"Cannot Fetch Cordinates.Try Again",Toast.LENGTH_SHORT).show();
        }
    }

    private class AsyncDataClass extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(RetailorRegistration.this);
            pd.setMessage("Loading...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(RETAILORREG_URL);
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
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),jsonresponce,Toast.LENGTH_SHORT).show();

            }
        }
    }


