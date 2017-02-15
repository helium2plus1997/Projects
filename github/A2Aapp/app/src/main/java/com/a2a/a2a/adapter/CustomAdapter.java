package com.a2a.a2a.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a2a.app.a2aapp.HomeFragment;

/**
 * Created by Mounika on 3/5/2016.
 */
public class CustomAdapter extends BaseAdapter {

    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public CustomAdapter(HomeFragment fragment, String[] Itemlist, int[] Itemimages ) {
        result=Itemlist;
        context= fragment.getActivity();
        imageId=Itemimages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(com.a2a.app.a2aapp.R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(com.a2a.app.a2aapp.R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(com.a2a.app.a2aapp.R.id.imageView1);
        holder.tv.setTextColor(Color.parseColor("#ffffff"));

        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);

        return rowView;
    }

}