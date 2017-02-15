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
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
/**
 * Created by root on 6/5/16.
 */
public class Deliveryoptions extends Activity{
    List<Bean> cart;
    String notify;
    String ret_id;
    String ret_name;
    String ret_addr;
    String cust_addr;
    String cust_phone;
    String delopt;
    long orderid;
    CartDatabase cartDatabase;


    ProgressDialog pd;//Progress Dialog Box for preexecute

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliveryoptions);
        RadioButton pickup=(RadioButton)findViewById(R.id.pickup);
        pickup.isSelected();

        SharedPreferences getcart= getSharedPreferences("CartItems", Context.MODE_PRIVATE);
        notify=getcart.getString("cart","No items");
        RadioButton homed=(RadioButton)findViewById(R.id.homed);

pickup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        delopt="pickup";
        Toast.makeText(getApplicationContext(), "the user want to pick up ",Toast.LENGTH_LONG).show();
        new AsyncDataClass().execute();

    }
});
        homed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delopt="home";
                Toast.makeText(getApplicationContext(), "the user want the home delivery",Toast.LENGTH_LONG).show();
                new AsyncDataClass().execute();
            }
        });
    }



    private class AsyncDataClass extends AsyncTask<String, String, String> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd =new ProgressDialog(Deliveryoptions.this);
            pd.setMessage("Placing Order");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                FileInputStream file = openFileInput("retailordet.txt");
                InputStreamReader reader= new InputStreamReader(file);
                BufferedReader buffer= new BufferedReader(reader);
                String str=buffer.readLine();


                FileInputStream file1 = openFileInput("customerdet.txt");
                InputStreamReader reader1= new InputStreamReader(file1);
                BufferedReader buffer1= new BufferedReader(reader1);
                String str1=buffer1.readLine();

                for(int i=1;str!=null;i++)
                {
                    if(i==1)
                    {ret_id=str;}
                    else if(i==2)
                    {ret_name=str;cust_phone=str1;}
                    else if(i==3)
                    {ret_addr=str;cust_addr=str1;}
                    str=buffer.readLine();
                    str1=buffer1.readLine();
                }

            }catch (IOException e){
                e.printStackTrace();
            }
            CartDatabase cartDatabase = new CartDatabase(getApplicationContext());
            try {
                cartDatabase.open();
                cart=cartDatabase.getData();
                cartDatabase.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Random randomGenerator = new Random();
            orderid=randomGenerator.nextInt(100000);
            for(int i=0;i<cart.size();i++)
            {
                String item_id=cart.get(i).getId();
                String item_price=cart.get(i).getPrice();
                int product_qty=cart.get(i).getPriceq();
                String product_name=cart.get(i).getProduct_name();
                String url="http://a2a.co.in/webservice2/notification.php?orderid="+orderid+"&name="+ret_name+"&address="+cust_addr+"&contact_no="+cust_phone+"&itemid="+item_id+"&itemname="+product_name+"&itemprice="+item_price+"&quantity="+product_qty+"&total_sum=0&delievery="+delopt+"&retailerid="+ret_id;
                ServiceHandler sh=new ServiceHandler();
                String response=sh.makeServiceCall(url,ServiceHandler.POST);
            }
            Log.e("MyActivity","Order placed");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(pd.isShowing()){
                pd.dismiss();
            }
            Toast.makeText(Deliveryoptions.this,"Order Placed",Toast.LENGTH_SHORT).show();


        }
    }
}
