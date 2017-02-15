package com.a2a.app.a2aapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyNotifications extends AppCompatActivity {

    public static NotificationsDatabase mydb;
    static List<notification> notifications;
    static List<String> mylist;
    static ListView notify_list;
    static ArrayAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notifications);

        notify_list = (ListView) findViewById(R.id.notifylist);
        mylist=new ArrayList<String>();
        mydb = new NotificationsDatabase(getApplicationContext());
        new listnotifications().execute();

    }

    public class listnotifications extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            madapter=new ArrayAdapter<String>(MyNotifications.this,android.R.layout.simple_list_item_1,mylist);
            notify_list.setAdapter(madapter);
        }
        @Override
        protected String doInBackground(String... params) {
            String order_str="No items";
            try {
                mydb.open();
                notifications = mydb.getData();
                mydb.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < notifications.size(); i++) {
                notification temp = new notification();
                notification temp1 = new notification();
                StringBuilder str = new StringBuilder();
                temp=notifications.get(i);
                String id=temp.getretid();
                if(id.equals(ReatailorSignin.retId)){
                String customer_number=temp.getContactnum();
                String customer_address=temp.getAddress();
                String delivery_type = temp.getDel_type();
                long total=temp.getTotal();
                int tempOrder_id=temp.getOrder_id();
                int tempOrder_id1;
                int j;
                    do {
                        j = i;
                        temp = notifications.get(j);
                        String pdt_name = temp.getProduct_name();
                        int pdt_id = temp.getProduct_id();
                        int qty = temp.getQuantity();
                        int price = temp.getPrice();

                        str.append(pdt_name);
                        str.append("\t");
                        str.append(qty);
                        str.append(" * ");
                        str.append(price);
                        str.append("\n");
                        try {
                            temp1 = notifications.get(j + 1);
                            tempOrder_id1 = temp1.getOrder_id();
                            i++;
                        } catch (Exception e) {
                            tempOrder_id1 = 999;
                            if ((++i) == mylist.size())
                                break;
                            else
                                i++;
                            e.printStackTrace();
                        }
                    } while (tempOrder_id == tempOrder_id1);
                    i = i - 1;
                    str.append("Total= " + total);
                    str.append("\n");
                    str.append("Delivery type= " + delivery_type);
                    str.append("\n");
                    str.append("Address= " + customer_address);
                    str.append("\n");
                    str.append("Contact number= " + customer_number);
                    str.append("\n");
                    str.append("\n*********");
                    order_str = str.toString();

                    mylist.add(order_str);

                }
            }
            return null;
        }
    }

    public void deletenotifications(View v){
        deleteDatabase(NotificationsDatabase.DB_NAME);
        Toast.makeText(MyNotifications.this,"All notifications cleared",Toast.LENGTH_SHORT).show();
        Log.e("MyActivity","Database Cleared");
    }
}
