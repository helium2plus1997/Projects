package com.a2a.app.a2aapp;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.a2a.app.a2aapp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mounika on 3/31/2016.
 */
public class GroceriesFragment extends ListFragment {

    public GroceriesFragment(){}

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    static String retid;
    private static String url = "http://a2a.co.in/webservice2/fetch_products.php?format=json&category=Groceries&retailerid="+retid;

    // JSON Node names
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_ID = "id";
    // private static final String TAG_NAME = "retailer_name";
    private static final String TAG_PRODUCT = "product_name";
    //private static final String TAG_CATEGORY = "category_id";

    // products JSONArray
    JSONArray products = null;
    String jsonStr=null;

    //Hashmap for ListView
    ArrayList<HashMap<String, String>> productList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_allproducts, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        try {
            FileInputStream file = getActivity().openFileInput("retailordet.txt");
            InputStreamReader reader= new InputStreamReader(file);
            BufferedReader buffer= new BufferedReader(reader);
            retid=buffer.readLine();

        }catch (IOException e){
            e.printStackTrace();
        }

        // tv = (ListView) rootView.findViewById(R.id.listView1);
        productList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// getting values from selected ListItem
                String id1 = ((TextView) view.findViewById(R.id.id))
                        .getText().toString();
                String name = ((TextView) view.findViewById(R.id.retailer))
                        .getText().toString();
                String product = ((TextView) view.findViewById(R.id.product))
                        .getText().toString();
                String category = ((TextView) view.findViewById(R.id.category))
                        .getText().toString();
            }
        });
        // Calling async task to get json
        new GetProducts().execute();
    }

    private class GetProducts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
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
                    products = jsonObj.getJSONArray(TAG_PRODUCTS);

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        // String name = c.getString(TAG_NAME);
                        // String email = c.getString(TAG_CATEGORY);
                        String product = c.getString(TAG_PRODUCT);

                        // tmp hashmap for single contact
                        HashMap<String, String> product1 = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        product1.put(TAG_ID, id);
                        // product1.put(TAG_NAME, name);
                        //product1.put(TAG_CATEGORY, email);
                        product1.put(TAG_PRODUCT, product);

                        // adding contact to contact list
                        productList.add(product1);
                    }

                }  catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(),productList,
                    R.layout.retfruits,new String[]{TAG_ID,
                    TAG_PRODUCT},new int[] { R.id.id, R.id.product
            });
            setListAdapter(adapter);
        }
    }
}