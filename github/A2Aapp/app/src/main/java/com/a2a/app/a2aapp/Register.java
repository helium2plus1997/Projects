package com.a2a.app.a2aapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Mounika on 3/3/2016.
 */
public class Register extends Activity implements LocationListener{


    /**
     * Defining layout items.
     **/

    EditText mFirstName, mLastName, mUsername, mMobile, mPassword,mlocation;
    ImageView img_back;
    Button btnRegister, mbtnSignIn;
    TextView registerErrorMsg;
    String jsonresponce = null;
    String enteredUsername, enteredPassword, enteredfname, enteredlname, enteredmobile;
    String REGISTER_URL;
    ProgressDialog pd;
    private SharedPreferences sharedpreferences;
    String username;
    String password;
    String address_str;
    private LocationManager locationManager;
    Location myloc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirstName = (EditText) findViewById(R.id.fname);
        mLastName = (EditText) findViewById(R.id.lname);
        mUsername = (EditText) findViewById(R.id.username);
        mMobile = (EditText) findViewById(R.id.mobie);
        mlocation=(EditText)findViewById(R.id.location);
        mPassword = (EditText) findViewById(R.id.pword);
        btnRegister = (Button) findViewById(R.id.register);
        mbtnSignIn = (Button) findViewById(R.id.signin);
        registerErrorMsg = (TextView) findViewById(R.id.tv);

        sharedpreferences = getSharedPreferences("A2aCache", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        username = sharedpreferences.getString("username", "");
        password = sharedpreferences.getString("pass", "");
        mUsername.setText(username);
        mPassword.setText(password);
        //  sharedpreferences = getSharedPreferences("a2a", Context.MODE_PRIVATE);
        img_back = (ImageView) findViewById(R.id.imageback);

        img_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor.clear();
                Intent i_backtohome = new Intent(Register.this, HomeActivity.class);
                startActivity(i_backtohome);
            }
        });
        mbtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                Intent i_backtosignin = new Intent(Register.this, Signin.class);
                startActivity(i_backtosignin);

            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredUsername = mUsername.getText().toString();
                Log.e("username", enteredUsername);

                enteredPassword = mPassword.getText().toString();
                Log.e("username", enteredPassword);

                enteredfname = mFirstName.getText().toString();
                Log.e("username", enteredfname);

                enteredlname = mLastName.getText().toString();
                Log.e("username", enteredlname);

                enteredmobile = mMobile.getText().toString();
                Log.e("username", enteredmobile);

                REGISTER_URL = "http://a2a.co.in/webservice2/register.php?UserName=" + enteredUsername + "&FirstName=" + enteredfname + "&LastName=" + enteredlname + "&Mobile=" + enteredmobile + "&Pass=" + enteredPassword;

                // REGISTER_URL = REGISTER_URL + enteredUsername + enteredPassword + enteredfname + enteredlname + enteredmobile;
                Log.e("finalurl", REGISTER_URL);

                if (enteredUsername.equals("") || enteredfname.equals("") || enteredlname.equals("")
                        || enteredmobile.equals("") || enteredPassword.equals("")) {

                    Toast.makeText(Register.this, "All Fields are Mandatory", Toast.LENGTH_LONG).show();

                    return;
                }
                if (mUsername.length() <= 1 || mPassword.length() <= 1) {

                    Toast.makeText(Register.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
                    return;
                }
                editor.clear();
                new AsyncDataClass().execute();

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
            myloc=location;
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            Geocoder geo = new Geocoder(Register.this);
            List<Address> add_list = null;
            try {
                add_list = geo.getFromLocation(loc.latitude, loc.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address add = add_list.get(0);
            if (add == null) {
                Toast.makeText(Register.this, "Unable to fetch current Location", Toast.LENGTH_SHORT).show();
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < add.getMaxAddressLineIndex(); i++) {
                    sb.append(add.getAddressLine(i)).append("\n");
                }
                sb.append(add.getLocality()).append("\n");
                sb.append(add.getPostalCode()).append("\n");
                sb.append(add.getCountryName());
                address_str = sb.toString();
            }
        }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this,"Enable GPS services",Toast.LENGTH_SHORT).show();

    }

    class AsyncDataClass extends AsyncTask<String, String, String> {

       /* String fName;
        String lName;
          public AsyncDataClass(){
            this.fName = enteredfname;
             this.lName = enteredlname;
          }*/

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Register.this);
            pd.setMessage("Loading...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(REGISTER_URL);
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

            if ("SUCCESS".equals(jsonresponce)) {
                Toast.makeText(getApplicationContext(), jsonresponce, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), jsonresponce, Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }

    public void onClick(View view) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,0,this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        if(myloc!=null) {
            mlocation.setText(address_str);
            // Intent i=new Intent(Register.this,MapView1.class);
            // i.putExtra("type",1);
            // startActivityForResult(i,2);
        }
        else{
            Toast.makeText(this,"Unable to fetch current Location",Toast.LENGTH_SHORT).show();
        }

    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2)
        {
            String address=data.getStringExtra("address");
            mlocation.setText(address);
            mlocation.setTextColor(Color.YELLOW);

        }
    }

*/
}










