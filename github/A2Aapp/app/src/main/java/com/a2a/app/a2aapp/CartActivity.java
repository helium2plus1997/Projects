package com.a2a.app.a2aapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 2/5/16.
 */
public class CartActivity extends Activity {
    CartDatabase cartDatabase;
    List<Bean> cart;
    TextView total_sum;
    Button back;
    ListView listView;
Button checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        listView=(ListView)findViewById(R.id.list_cart);
        cartDatabase=new CartDatabase(this);
        back=(Button)findViewById(R.id.deleteproduct);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 ;               Intent i =new Intent(getApplicationContext(),SlidingHome.class);
                startActivity(i);
            }
        });
       /* deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartDatabase.deleteEntry(cart.getId());

            }
        });*/
        total_sum=(TextView)findViewById(R.id.total_sum);
        checkout=(Button)findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int total=0;
                int amountperproduct=0;
                String productname=null;
                for(int i=0;i<cart.size();i++)
                { productname=cart.get(i).getProduct_name();
                    String price=cart.get(i).getPrice();
                    int quantity=cart.get(i).getQuantity();
                    int amount=Integer.parseInt(price);
                      amountperproduct=amount*quantity;
                    total=total+amount*quantity;

                }
                Intent i=new Intent(getApplicationContext(),Checkedout.class);
                i.putExtra("totalsum",total);
                i.putExtra("amountperproduct",amountperproduct);
                i.putExtra("productname",productname);
                startActivity(i);
*/
                sendCheckout();
                Toast.makeText(getApplicationContext(), " your final order ", Toast.LENGTH_LONG).show();

            }

        });
       try {
            cartDatabase.open();
            cart=cartDatabase.getData();
            cartDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        //ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,cart);
        ArrayAdapter adapter=new CustomListAdapter(CartActivity.this, R.layout.cardview,cart,this);
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
    }
    public void sendCheckout()
    {
        String s="",show="";
       int sum=0;
        int total_sum=0;

        for(int i=0;i<cart.size();i++)
        {
           // s=s+cart.get(i).getProduct_name()+"\n"+cart.get(i).getPrice()+"X"+cart.get(i).getQuantity()+"\n";
           sum=Integer.parseInt(cart.get(i).getPrice())*(cart.get(i).getQuantity());
            total_sum=total_sum + Integer.parseInt(cart.get(i).getPrice())*(cart.get(i).getQuantity());
            show=show+cart.get(i).getProduct_name()+" ,"+cart.get(i).getQuantity()+"          Rs "+sum +"\n";
        }
        Intent in=new Intent(getApplicationContext(),Checkedout.class);
      //  in.putExtra("sum",sum);
        in.putExtra("show",show);
        //in.putExtra("ts",total_sum);
        in.putExtra("ts",total_sum);
        startActivity(in);

    }

    public void updateList()
    {
        CartDatabase cartDatabase=new CartDatabase(this);
        try {
            cartDatabase.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cart=cartDatabase.getData();
        cartDatabase.close();
        ArrayAdapter adapter=new CustomListAdapter(CartActivity.this, R.layout.cardview,cart,this);
        listView.setAdapter(adapter);
    }

}
