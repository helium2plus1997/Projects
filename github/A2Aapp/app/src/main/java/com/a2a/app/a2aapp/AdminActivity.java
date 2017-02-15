package com.a2a.app.a2aapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    List ret_list = new ArrayList<retailors>();
    ListView retlist;
    ProgressDialog pd;
    Button approve;
    CheckBox check;
    String username;
    TextView user_name;
    RetailorListAdapter mlistadapter;
    private static final String TAG_USER = "user";
    private static final String TAG_NAME = "name";
    private static final String TAG_SHOPNAME = "shop_name";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_ABOUTSHOP = "about_shop";

    private static final String FETCH_RETAILORS = "http://a2a.co.in/webservice2/fetch_retailers.php?";
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approveadmin);

        mlistadapter = new RetailorListAdapter(AdminActivity.this, ret_list);
        contactList = new ArrayList<HashMap<String, String>>();

        Button mdeails = (Button)findViewById(R.id.details);
        mdeails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AdminActivity.this, Admin.class);
                startActivity(i);
            }
        });

        retlist = (ListView) findViewById(R.id.retailors_ls);
        retlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user_name=((TextView)view.findViewById(R.id.username)).getText().toString();
                String name = ((TextView) view.findViewById(R.id.ret_name)).getText().toString();
                Log.e("name", name);
                String shopname = ((TextView) view.findViewById(R.id.shopname))
                        .getText().toString();
                String address = ((TextView) view.findViewById(R.id.address))
                        .getText().toString();
                String aboutshop = ((TextView) view.findViewById(R.id.aboutshop))
                        .getText().toString();
                Intent i = new Intent(getApplicationContext(), SingleRetailorActivity.class);
                i.putExtra(TAG_NAME, name);
                i.putExtra(TAG_SHOPNAME,shopname);
                i.putExtra(TAG_ADDRESS,address);
                i.putExtra(TAG_ABOUTSHOP,aboutshop);
                startActivity(i);
            }
        });

        new AsyncDataClass1().execute();

        approve = (Button) findViewById(R.id.aprvbtn);
        approve.setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           for (int i = 0; i < mlistadapter.mCheckStates.size(); i++) {

                                               if (mlistadapter.mCheckStates.get(i) == true) {

                                                 // Toast.makeText(AdminActivity.this, "Succesfully Approved!", Toast.LENGTH_SHORT).show();
                                                  //TextView user_name;

                                                   user_name = (TextView) retlist.getChildAt(i).findViewById(R.id.username);
                                                   username = user_name.getText().toString();
                                                   Log.e("res",username);
                                                   String APPROVE_URL = "http://a2a.co.in/webservice2/update_retailer.php?username=" + username;
                                                   Log.e("URL",APPROVE_URL);
                                                   HttpClient myclient=new DefaultHttpClient();
                                                   HttpPost post=new HttpPost(APPROVE_URL);
                                                   HttpResponse resp=null;
                                                   try{
                                                       resp=myclient.execute(post);
                                                   }catch (Exception e){}
                                                   if(resp!=null)
                                                   {
                                                       Toast.makeText(AdminActivity.this,"Succesfully Approved!",Toast.LENGTH_SHORT).show();}
                                                   else{
                                                       Toast.makeText(AdminActivity.this,"Error in Approving!",Toast.LENGTH_SHORT).show();
                                                   }
                                            } /*else{
                                                   Toast.makeText(AdminActivity.this,"Please select atleast one!",Toast.LENGTH_SHORT).show();
                                               }*/
                                           }
                                       }
                                   }
        );
    }

    private class AsyncDataClass1 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AdminActivity.this);
            pd.setTitle("Fetching Retailors...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            String response = sh.makeServiceCall(FETCH_RETAILORS, ServiceHandler.GET);
            if (response != null) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray user = obj.getJSONArray(TAG_USER);
                    for (int i = 0; i < user.length(); i++) {
                        JSONObject temp = user.getJSONObject(i);
                       retailors a = new retailors();
                        String status = temp.getString("status");
                        if (status.equals("0")) {
                            String retname = temp.getString(TAG_NAME);
                            String shopname=temp.getString(TAG_SHOPNAME);
                            String address = temp.getString(TAG_ADDRESS);
                            String aboutshop = temp.getString(TAG_ABOUTSHOP);

                         a.setname(retname);
                            a.setShopname(shopname);
                            a.setaddr(address);

                            ret_list.add(a);

                            // tmp hashmap for single contact
                           /* HashMap<String, String> list = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            list.put(TAG_NAME, retname);
                            list.put(TAG_SHOPNAME,shopname);
                            list.put(TAG_ADDRESS,address);
                            list.put(TAG_ABOUTSHOP,aboutshop);*/
                            // contactList.add(list);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

                Toast.makeText(AdminActivity.this, "Check Internet Connectivity", Toast.LENGTH_SHORT).show();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            retlist.setAdapter(mlistadapter);
        }
    }
}