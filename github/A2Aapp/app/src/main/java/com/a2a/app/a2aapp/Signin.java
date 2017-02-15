package com.a2a.app.a2aapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Mounika on 3/3/2016.
 */
public class Signin extends Activity {

    Button msignin;
    TextView mregister;
    EditText musername;
    EditText mpassword;
    ImageView mimageback;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog pd;
    Button btngplus;
    TextView tv;
    String enteredUsername, enteredPassword;
    String SIGNIN_URL;
    String jsonresponce=null;

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView info1;
    //JSON element ids from repsonse of php script:
    JSONArray user=null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_USER="user";
    private static final String UserDetails="A2Auserdata";
    private static final String Usercache="A2aCache";
    SharedPreferences sharedpref;
    SharedPreferences userdetails;

    //For Admin Privelleges
    Boolean admin=Boolean.FALSE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_signin);


        loginButton = (LoginButton)findViewById(R.id.login_button);
        info1 = (TextView)findViewById(R.id.info1);
       // loginButton.setReadPermissions("profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                info1.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
                Intent i = new Intent(Signin.this,MapView2.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                info1.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info1.setText("Login attempt failed.");
            }
        });

        msignin = (Button) findViewById(R.id.signin);
        mregister = (TextView) findViewById(R.id.register);
        musername = (EditText) findViewById(R.id.username);
        mpassword = (EditText) findViewById(R.id.password);
        btngplus = (Button) findViewById(R.id.btngplus);
      //  btnfb = (ImageView)findViewById(R.id.login_button);
        mimageback = (ImageView)findViewById(R.id.imageback);

        btngplus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),Gpluslogin.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });
       /* btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signin.this,socialauthentication.class);
                startActivity(i);
            }
        });*/
        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //saving username and password
                Intent i = new Intent(Signin.this,Register.class);
                i.putExtra("username",enteredUsername);
                i.putExtra("password",enteredPassword);
                startActivity(i);
            }
        });

        msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredUsername = musername.getText().toString();
                Log.e("username", enteredUsername);

                enteredPassword = mpassword.getText().toString();
                Log.e("password", enteredPassword);

                SIGNIN_URL = "http://a2a.co.in/webservice2/login.php?user=1&format=json&UserName=" + URLEncoder.encode(enteredUsername) + "&Password=" +URLEncoder.encode(enteredPassword);
                Log.e("finalurl", SIGNIN_URL);

                if (enteredUsername.equals("") || enteredPassword.equals("")) {
                    Toast.makeText(Signin.this, "All Fields are Mandatory", Toast.LENGTH_LONG).show();
                    return;
                }
                new AsyncDataClass().execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class AsyncDataClass extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Signin.this);
            pd.setMessage("Loading...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            String fname="Unknown";
            String lname="Unknown";
            String pass="Unknown";
            String phone="Unknown";
            String username="unknown";
            jsonresponce = sh.makeServiceCall(SIGNIN_URL, ServiceHandler.GET);
            if (jsonresponce != null) {
                try {
                        JSONObject obj=new JSONObject(jsonresponce);
                        user=obj.getJSONArray(TAG_USER);
                        for(int i=0;i<user.length();i++) {
                            JSONObject temp=user.getJSONObject(i);
                            fname = temp.getString("first_name");
                            lname = temp.getString("last_name");
                            username=temp.getString("Username");
                            pass = temp.getString("password");
                            phone = temp.getString("mobile");

                            if(username.equals("admin") && pass.equals("admin"))
                            {admin=Boolean.TRUE;}
                        }
                    }
                      catch (JSONException e) {
                    e.printStackTrace();
                     }
                    //For My profile Fragment
                    SharedPreferences sharedpref = getSharedPreferences("A2Auserdetails",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedpref.edit();
                    editor.clear();
                    editor.putString("firstname", fname);
                    editor.putString("lastname", lname);
                    editor.putString("phone", phone);
                    editor.putString("username",username);
                    editor.putString("password", pass);
                    editor.commit();

                //local text File
                try {
                    FileOutputStream file = openFileOutput("customerdet.txt", MODE_WORLD_READABLE);
                    OutputStreamWriter writer = new OutputStreamWriter(file);
                    writer.write(username + "\n");
                    writer.flush();
                    writer.close();
                    Log.e("MyActivity", "Customer File Created");
                }catch (IOException e){
                    e.printStackTrace();
                }
                return jsonresponce;
            }
            else {

                return jsonresponce;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if(jsonresponce!=null && !jsonresponce.equals("username or password is incorrect")){
                if(admin==Boolean.TRUE){
                    Intent i=new Intent(Signin.this,AdminActivity.class);
                    startActivity(i);
                }
                else {
                    Intent myintent = new Intent(Signin.this, MapView2.class);
                    startActivity(myintent);
                }
               /* Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:37.827500,-122.481670"));
                i.setClassName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity");
                startActivity(i);*/
            }
            else {
                Toast.makeText(getApplicationContext(),"USER NOT FOUND",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }
}


