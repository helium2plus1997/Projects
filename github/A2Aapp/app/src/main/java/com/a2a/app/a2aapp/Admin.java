package com.a2a.app.a2aapp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.a2a.app.a2aapp.R;
import com.a2a.a2a.adapter.MyCustomAdapter;
import com.a2a.app.a2aapp.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mounika on 6/9/2016.
 */
public class Admin extends ListActivity {

    private ProgressDialog pd;


    private static final String TAG_USER = "user";
    private static final String TAG_NAME = "name";
    private static final String TAG_SHOPNAME = "shop_name";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_ABOUTSHOP = "about_shop";

    private static String url = "http://a2a.co.in/webservice2/fetch_retailers.php?";

    JSONArray user = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> retailorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        retailorList = new ArrayList<HashMap<String, String>>();
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = ((TextView) view.findViewById(R.id.ret_name)).getText().toString();
                String shopname = ((TextView) view.findViewById(R.id.shopname)).getText().toString();
                String location = ((TextView) view.findViewById(R.id.address)).getText().toString();
                String aboutshop = ((TextView) view.findViewById(R.id.aboutshop)).getText().toString();

                Intent i = new Intent(getApplicationContext(), SingleRetailorActivity.class);
                i.putExtra(TAG_NAME, name);
               i.putExtra(TAG_SHOPNAME, shopname);
                i.putExtra(TAG_LOCATION, location);
                i.putExtra(TAG_ABOUTSHOP, aboutshop);
                startActivity(i);
            }
        });
        new GetRetailors().execute();
    }

    private class GetRetailors extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Admin.this);
            pd.setMessage("Loading..");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);
            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    user = jsonObj.getJSONArray(TAG_USER);
                    // looping through All Contacts
                    for (int i = 0; i < user.length(); i++) {
                        JSONObject c = user.getJSONObject(i);
                        String retname = c.getString(TAG_NAME);
                        String shopname = c.getString(TAG_SHOPNAME);
                        String location = c.getString(TAG_LOCATION);
                        String aboutshop = c.getString(TAG_ABOUTSHOP);

                        // tmp hashmap for single contact
                        HashMap<String, String> list = new HashMap<String, String>();

                        list.put(TAG_NAME, retname);
                        list.put(TAG_SHOPNAME, shopname);
                        list.put(TAG_ADDRESS, location);
                        list.put(TAG_ABOUTSHOP, aboutshop);

                        retailorList.add(list);
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Log.e("ServiceHandler","couldn't get any data from url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            ListAdapter adapter = new SimpleAdapter(Admin.this,retailorList,R.layout.activity_single_retailor,
                    new String[] {TAG_NAME,TAG_SHOPNAME,TAG_ADDRESS,TAG_ABOUTSHOP}, new int[]{
            R.id.ret_name,R.id.shopname,R.id.address,R.id.aboutshop
            }
            );
            setListAdapter(adapter);

        }
    }
}
