package com.a2a.app.a2aapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by my hp on 2/7/2016.
 */
public class CustomListAdapter extends ArrayAdapter {
    List<Bean> cart;

    private Context mContext;
    private int id;
    public List<Bean> items ;
    Button plus,min,dbtn;
    int c=1;
    CartActivity cartActivity;
    public CustomListAdapter(Context context, int textViewResourceId,List<Bean>  list,CartActivity cartActivity)
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
        for(int i=0;i<items.size();i++)
        {
            items.get(i).setQuantity(1);
        }
        this.cartActivity=cartActivity;
    }


    @Override
    public View getView(final int position, View v, ViewGroup parent)
    {
        View mView = v ;

        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }
       //
        TextView id = (TextView) mView.findViewById(R.id.id);
        final CartDatabase cartDatabase=new CartDatabase(getContext());
     /*   try {
            cartDatabase.open();
            cart=cartDatabase.getData();
           // cartDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/


        TextView product_name = (TextView) mView.findViewById(R.id.product_name);
        TextView price = (TextView) mView.findViewById(R.id.price);

        final TextView qty = (TextView) mView.findViewById(R.id.qty);
        Button dbtn= (Button) mView.findViewById(R.id.dbtn);
        dbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();

                try {
                    cartDatabase.open();
                    // cart = cartDatabase.getData();
                    cartDatabase.deleteEntry(items.get(position).getId());

                    cartDatabase.close();
                    cartActivity.updateList();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(mContext, " Item removed from cart " , Toast.LENGTH_LONG).show();

            }
        });


        final TextView tvf=(TextView)mView.findViewById(R.id.qty1);
        qty.setText("Rs "+items.get(position).getPrice());
        tvf.setTextColor(Color.BLACK);
        plus=(Button)mView.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                items.get(position).setQuantity(c);

                tvf.setText("" + c);

                int priceq=0;
               priceq=c*Integer.parseInt(items.get(position).getPrice());
                items.get(position).setPriceq(priceq);
                qty.setText("Rs "+priceq + "");

            }

        });

        min=(Button)mView.findViewById(R.id.min);
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c>1){
                    c--;
                    items.get(position).setQuantity(c);
                    tvf.setText(""+c);

                    int priceq=0;
                    priceq=c*Integer.parseInt(items.get(position).getPrice());
                    items.get(position).setPriceq(priceq);
                    qty.setText("Rs "+priceq+"");
                }

            }
        });





        if(items.get(position)!= null )
        {
         int c=0;

           c=items.get(position).getQuantity()*(Integer.parseInt(items.get(position).getPrice()));
            //text.setTextColor(Color.BLACK);
            id.setText(items.get(position).getId());
            product_name.setText(items.get(position).getProduct_name());
            product_name.setTextColor(Color.BLACK);
            price.setText("Rs " + items.get(position).getPrice());
            price.setTextColor(Color.BLACK);
           // qty.setText(""+c);
         // qty.setTextColor(Color.BLACK);

            //text.setBackgroundColor(Color.BLUE);
            // int color = Color.argb(200, 255, 64, 64);
            // text.setBackgroundColor( color );

        }


        return mView;
    }

}

