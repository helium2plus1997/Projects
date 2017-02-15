package com.a2a.a2a.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.a2a.app.a2aapp.Bean;
import com.a2a.app.a2aapp.CartActivity;
import com.a2a.app.a2aapp.CartDatabase;
import com.a2a.app.a2aapp.R;
import com.a2a.app.a2aapp.Snacks;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mounika on 3/29/2016.
 */
public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private List<Bean> list;
    private Context context;
    private List<Bean> list1;


    public MyCustomAdapter(List<Bean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customlayout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getProduct_name());

        TextView price=(TextView)view.findViewById(R.id.price);
        price.setText(list.get(position).getPrice());
        //Handle buttons and add onClickListeners
       // Button deleteBtn = (Button)view.findViewById(R.id.childButton);
        Button addBtn = (Button)view.findViewById(R.id.addButton);

       /* deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });*/
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Boolean bb=false;
                notifyDataSetChanged();
                CartDatabase cartDatabase = new CartDatabase(context);

Boolean flag=false;

                try {
                    cartDatabase.open();
                    bb=cartDatabase.checkentry(list.get(position).getId());
                    //  cartDatabase.createentry(list.get(position).getProduct_name(), list.get(position).getPrice(), list.get(position).getId());
              //  } catch (SQLException e) {
              //      e.printStackTrace();
              //  }
//
                    if(bb){
  //                 try {
                      // cartDatabase.open();
                       cartDatabase.createentry(list.get(position).getProduct_name(), list.get(position).getPrice(), list.get(position).getId());
                       cartDatabase.close();
                        flag=false;
                    }else {
                       cartDatabase.close();
                        flag=true;
                        Log.v("in db","in db");
                   }
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
                   //  }

                    Log.d("yy", "yy");
                if(flag) {
                    Toast.makeText(context, " ---item already in the cart---", Toast.LENGTH_LONG).show();
                }
                   Intent i1 = new Intent(context, CartActivity.class);
                   i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   Bundle b = new Bundle();
                   b.putString("product_name", list.get(position).getProduct_name());
                   i1.putExtras(b);

                   context.startActivity(i1);

            }
        });


        return view;
    }

   /* public void filterData(String query){
        query = query.toLowerCase();
        Log.v("MyCustomAdapter", String.valueOf(list.size()));
        list.clear();

        if(query.isEmpty()){
            list.addAll(list1);
        }
        else {

            for(Snacks snacks: list1){

                ArrayList<> productList = continent.getCountryList();
                ArrayList<Country> newList = new ArrayList<Country>();
                for(Country country: countryList){
                    if(country.getCode().toLowerCase().contains(query) ||
                            country.getName().toLowerCase().contains(query)){
                        newList.add(country);
                    }
                }
                if(newList.size() > 0){
                    Continent nContinent = new Continent(continent.getName(),newList);
                    continentList.add(nContinent);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(.size()));
        notifyDataSetChanged();


    }*/
}
