package com.a2a.app.a2aapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a2a.a2a.adapter.CustomAdapter;

import java.util.ArrayList;

/**
 * Created by Mounika on 3/4/2016.
 */
public class HomeFragment extends Fragment {

    GridView gv;
    Context context;
    ArrayList prgmName;
    TextView viewall;
    public static String[] Itemslist = {
            "GROCERIES",
            "FRIUTS",
            "VEGETABLES",
            "BEVERAGES",
            "SNACKS",
            "COSMETICS",
            "STATIONARIES",
            "BRANDED FOODS" };
    public static int[] Itemimages = {R.drawable.cosmetics1,
        R.drawable.stationeries1,
        R.drawable.brandedfood1,
        R.drawable.beverages1,
        R.drawable.fruits1,
        R.drawable.groceries1,
        R.drawable.snacks1,
        R.drawable.fruits1
       };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_customerhomepage, container, false);
        viewall = (TextView)rootView.findViewById(R.id.viewall);
        gv=(GridView)rootView.findViewById(R.id.gridView1);

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeFragment.this.getActivity(),Viewallproducts.class);
                startActivity(i);
            }
        });

      //  ImageHelper adapter = new ImageHelper(this);
       gv.setAdapter(new CustomAdapter(HomeFragment.this,Itemslist,Itemimages));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) view.findViewById(R.id.image);

                if (Itemslist[+position] == "GROCERIES") {
                    Intent i = new Intent(getActivity(), Groceries.class);
                    startActivity(i);
                } else if (Itemslist[+position] == "FRIUTS") {
                    Intent intent = new Intent(getActivity(), Fruits.class);
                    startActivity(intent);
                } else if (Itemslist[+position] == "VEGETABLES") {
                    Intent intent = new Intent(getActivity(), Vegetables.class);
                    startActivity(intent);
                } else if (Itemslist[+position] == "BEVERAGES") {
                    Intent intent = new Intent(getActivity(), Beverages.class);
                    startActivity(intent);
                } else if (Itemslist[+position] == "SNACKS") {
                    Intent intent = new Intent(getActivity(), Snacks.class);
                    startActivity(intent);
                } else if (Itemslist[+position] == "COSMETICS") {
                    Intent intent = new Intent(getActivity(), Cosmetics.class);
                    startActivity(intent);
                } else if (Itemslist[+position] == "STATIONARIES") {
                    Intent intent = new Intent(getActivity(), Stationeries.class);
                    startActivity(intent);
                } else if (Itemslist[+position] == "BRANDED FOODS") {
                    Intent intent = new Intent(getActivity(), Brandedfoods.class);
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }
}


