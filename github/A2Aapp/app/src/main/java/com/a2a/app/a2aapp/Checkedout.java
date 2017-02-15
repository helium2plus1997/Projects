package com.a2a.app.a2aapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

/**
 * Created by root on 4/5/16.
 */
public class Checkedout extends Activity {
    //  TextView totalsum;
    List<Bean> cart;
    TextView ttt;
    ListView listView;
    // TextView amountperproduct;
    //TextView productname;
    Button delivery;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkedout);
        ttt = (TextView) findViewById(R.id.ttt);

        delivery = (Button) findViewById(R.id.delivery);
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Deliveryoptions.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), " Choose Delivery Options ", Toast.LENGTH_LONG).show();
            }
        });
        listView = (ListView) findViewById(R.id.list_cart);


        // RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //mRecyclerView.addItemDecoration(itemDecoration);

      /*  productname=(TextView)findViewById(R.id.product_name);
        productname.setTextColor(Color.WHITE);
        int total=getIntent().getIntExtra("totalsum",0);
        int amountper_product=getIntent().getIntExtra("amountperproduct",0);
        String product_name=getIntent().getStringExtra("productname");
        productname.setText("product- "+ product_name);
        totalsum=(TextView)findViewById(R.id.total);
        amountperproduct=(TextView)findViewById(R.id.amount);
        amountperproduct.setText("amount- "+amountper_product);
        totalsum.setText("Tatal amount - " + total);
        */
//String ss=getIntent().getStringExtra("quantity");
        //     //  int s=getIntent().getIntExtra("sum");
        String ts = getIntent().getStringExtra("show");
        int t = getIntent().getIntExtra("ts", 0);
        SharedPreferences cart_iems=getSharedPreferences("CartItems", Context.MODE_PRIVATE);
        SharedPreferences.Editor items= cart_iems.edit();
        items.clear();
        items.putString("cart",ts);
        items.apply();
        String cart[] = ts.split("\n");

        ttt.setText("total = Rs " + t);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, cart);

        listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);

                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        listView.setAdapter(adapter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Checkedout Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.a2a.app.a2aapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Checkedout Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.a2a.app.a2aapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
         /*  Delivery=(Button)findViewById(R.id.delivery);
        Delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Deliveryoptions.class);
                startActivity(i);
            }
        });
    }*/
}