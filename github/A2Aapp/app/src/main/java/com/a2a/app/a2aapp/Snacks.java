package com.a2a.app.a2aapp;

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
import android.widget.TextView;

import com.a2a.a2a.adapter.MyCustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mounika on 3/22/2016.
 */
public class Snacks extends ListActivity {
    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://a2a.co.in/webservice2/fetch_products.php?format=json&category=Snacks";

    // JSON Node names
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_ID = "id";
    private static final String TAG_PRICE = "product_price";
    private static final String TAG_PRODUCT = "product_name";
    // private static final String TAG_CATEGORY = "category_id";

    // products JSONArray
    JSONArray products = null;
    String jsonStr=null;

    //Hashmap for ListView
    List<Bean> productList;
    List<Bean> pricelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snacks);

        productList = new ArrayList<Bean>();
        pricelist=new ArrayList<Bean>();

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
                Log.v("jgvj","hjvikjb");
                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        SingleProductActivity.class);

                startActivity(in);
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
            pDialog = new ProgressDialog(Snacks.this);
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
                        Bean bean=new Bean();

                        String id = c.getString(TAG_ID);
                        // String name = c.getString(TAG_NAME);
                        // String email = c.getString(TAG_CATEGORY);
                        String product = c.getString(TAG_PRODUCT);
                        String price=c.getString(TAG_PRICE);
                        bean.setProduct_name(product);
                        bean.setId(id);
                        bean.setPrice(price);
                        // tmp hashmap for single contact


                        // adding each child node to HashMap key => value
                        //  product1.put(TAG_ID, id);
                        //  product1.put(TAG_NAME, name);
                        // product1.put(TAG_CATEGORY, email);
                        //  product1.put(TAG_PRODUCT, product);

                        // adding contact to contact list
                        productList.add(bean);
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
            ListAdapter adapter=new MyCustomAdapter(productList,getApplicationContext());
            setListAdapter(adapter);
        }

    }

}


