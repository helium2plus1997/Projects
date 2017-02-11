package com.a2a.app.a2aapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mounika on 3/3/2016.
 */
public class ReatailorSignin extends Activity {

    Button msignin;
    TextView mregister;
    EditText musername;
    EditText mpassword;
    Button btngplus;
    ImageView mimageback;
    private CallbackManager callbackManager;
    String enteredUsername, enteredPassword;
    String SIGNIN_URL;
    String response=null;
    ProgressDialog pd;

    private GoogleApiClient mGoogleApiClient;
    JSONArray retailer=null;
    SharedPreferences pref;
    private static final String TAG_USER = "user";
    private static final String TAG_ID="id";
    private static final String TAG_USERNAME="username";
    public static NotificationsDatabase mdb;
    static List<notification> mylist;
    public List<String>ORDERS;
    int NOTIFICATIONID=0;
    static String retId;
    String retusrname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retailor_signin);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        ORDERS=new ArrayList<String>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        msignin = (Button)findViewById(R.id.signin);
        mregister = (TextView)findViewById(R.id.register);
        musername = (EditText)findViewById(R.id.username);
        mpassword = (EditText)findViewById(R.id.password);
        btngplus = (Button)findViewById(R.id.btngplus);
        mimageback = (ImageView)findViewById(R.id.imageback);

        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReatailorSignin.this,RetailorRegistration.class);
                startActivity(i);
            }
        });
        mimageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReatailorSignin.this,RetailorHomeActivity.class);
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

                SIGNIN_URL = "http://a2a.co.in/webservice2/retailer_login.php?user=1&UserName="+enteredUsername+"&Password="+enteredPassword ;
                Log.e("finalurl", SIGNIN_URL);

                if (enteredUsername.equals("") || enteredPassword.equals("")) {
                    Toast.makeText(ReatailorSignin.this, "All Fields are Mandatory", Toast.LENGTH_LONG).show();
                    return;
                }

                new getNotifications().execute();
            }
        });
    }

    private class getNotifications extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ReatailorSignin.this);
            pd.setMessage("Loading...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }


        @Override
        protected String doInBackground(String... params) {
            String success = null;

            try {
                URL url = new URL(SIGNIN_URL);
                String res;

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();
                res = conn.getResponseMessage();
                Log.e("res", res);

                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                ServiceHandler sh = new ServiceHandler();
                response = sh.makeServiceCall(SIGNIN_URL, ServiceHandler.GET);
                Log.e("response", response);

                if (response != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        retailer = jsonObj.getJSONArray(TAG_USER);
                        for (int i = 0; i <= retailer.length(); i++) {
                            JSONObject c = retailer.getJSONObject(i);
                            retId = c.getString(TAG_ID);
                            retusrname = c.getString(TAG_USERNAME);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Output Local File for Storing retailordetails
                    try {
                        FileOutputStream file = openFileOutput("retailordet.txt", MODE_WORLD_READABLE);
                        OutputStreamWriter writer = new OutputStreamWriter(file);
                        writer.write(retId + "\n");
                        writer.write(retusrname + "\n");
                        writer.flush();
                        writer.close();
                        Log.e("MyActivity", "Retailor File Created");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    in.close();
                    reader.close();
                    response = sb.toString();

                    Log.e("jsondata", response);
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

            //Fetching retailer id from local file
            try {
                FileInputStream file = openFileInput("retailordet.txt");
                InputStreamReader reader= new InputStreamReader(file);
                BufferedReader buffer= new BufferedReader(reader);
                retId=buffer.readLine();

            }catch (IOException e){
                e.printStackTrace();
            }
            if (response != null) {
                //Notification code
                String order_str = "No items";
                long total = 0;
                String del_type = "Unknown";
                String number="Unknown";
                String address="Unknown";
                StringBuilder str = new StringBuilder();
                try {
                    String NOTIFY_URL="http://a2a.co.in/webservice2/fetch_notification.php?retailerid="+retId;
                    ServiceHandler sh = new ServiceHandler();
                    Log.e("MyActivity","notifyurl= "+NOTIFY_URL);
                    String response = sh.makeServiceCall(NOTIFY_URL, ServiceHandler.GET);
                    if (response != null) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray notify = obj.getJSONArray("notifications");
                            for (int i = 0; i < notify.length(); i++) {
                                JSONObject temp = notify.getJSONObject(i);
                                String noteid = temp.getString("note_id");
                                int orderid=temp.getInt("order_id");
                                String pdt_name = temp.getString("product_name");
                                int qty = temp.getInt("Quantity");
                                int price = temp.getInt("product_price");
                                int pdt_id = temp.getInt("product_id");
                                total = temp.getLong("total_sum");
                                del_type = temp.getString("delivery_type");
                                number=temp.getString("contact_no");
                                address=temp.getString("address");
                                //
                                //Database updation
                                try {
                                    mdb = new NotificationsDatabase(ReatailorSignin.this);
                                    mdb.open();
                                    Boolean is_present=mdb.checkentry(noteid);
                                    if(is_present==Boolean.FALSE) {
                                        Log.e("MyActivity", is_present.toString());
                                        mdb.createentry(noteid,orderid,retId, pdt_name, price, qty, total, pdt_id, del_type,number,address);
                                        Log.e("MyActivity",pdt_name+" added to database");
                                    }
                                    mdb.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            ///End of Notification
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (response != null) {
                    try {
                        mdb=new NotificationsDatabase(ReatailorSignin.this);
                        mdb.open();
                        mylist = mdb.getData();
                        mdb.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (mylist != null) {
                        for (int i = 0; i < mylist.size(); i++) {
                            notification temp1 = new notification();
                            notification temp2 = new notification();
                            String delivery_type;
                            String contactnum;
                            String customer_address;
                            temp1 = mylist.get(i);
                            String tempretid = temp1.getretid();
                            if (tempretid.equals(ReatailorSignin.retId)) {
                                total = temp1.getTotal();
                                contactnum = temp1.getContactnum();
                                customer_address = temp1.getAddress();
                                delivery_type = temp1.getDel_type();
                                int temporder1;
                                int temporder2;
                                temporder1 = temp1.getOrder_id();
                                int j;
                                StringBuilder str1 = new StringBuilder();
                                do {
                                    j = i;
                                    temp1 = mylist.get(j);
                                    String pdt_name = temp1.getProduct_name();
                                    int pdt_id = temp1.getProduct_id();
                                    int qty = temp1.getQuantity();
                                    int price = temp1.getPrice();
                                    str1.append(pdt_name);
                                    str1.append("\t");
                                    str1.append(qty);
                                    str1.append(" * ");
                                    str1.append(price);
                                    str1.append("\n");
                                    try {
                                        temp2 = mylist.get(j + 1);
                                        temporder2 = temp2.getOrder_id();
                                        i++;
                                    } catch (Exception e) {
                                        temporder2 = 999;
                                        if ((++i) == mylist.size())
                                            break;
                                        else
                                            i++;
                                        e.printStackTrace();
                                    }

                                } while (temporder1 == temporder2);
                                i = i - 1;
                                str1.append("Total= " + total);
                                str1.append("\n");
                                str1.append("Delivery type= " + delivery_type);
                                str1.append("\n");
                                str1.append("Address= " + customer_address);
                                str1.append("\n");
                                str1.append("Contact number= " + contactnum);
                                str1.append("\n");
                                str1.append("\n*********");
                                order_str = str1.toString();
                                ORDERS.add(order_str);

                                //Notification Build
                                Intent intent = new Intent(getApplicationContext(), MyNotifications.class);
                                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                                mBuilder.setSmallIcon(R.drawable.ic_launcher);
                                mBuilder.setContentTitle("*****Order Details*****");
                                mBuilder.setContentText(order_str);
                                mBuilder.setContentIntent(pIntent);
                                NOTIFICATIONID = NOTIFICATIONID;
                                NotificationManager mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                mManager.notify(NOTIFICATIONID, mBuilder.build());
                                NOTIFICATIONID++;
                                ///////
                            }
                            }

                        }

                    } else {
                        Toast.makeText(ReatailorSignin.this, "No notifications", Toast.LENGTH_SHORT).show();
                    }
                }


            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
                if(response!=null){
               Intent myintent = new Intent(ReatailorSignin.this, RetailorSlidingmenu.class);
               startActivity(myintent);
            }else {
                Toast.makeText(getApplicationContext(),"USER NOT FOUND",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
