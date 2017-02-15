package com.a2a.app.a2aapp;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Heerok Banerjee on 5/19/2016.
 */
public class RetailorListAdapter extends ArrayAdapter<retailors> implements CompoundButton.OnCheckedChangeListener {

    List<retailors>ret1=null;
    SparseBooleanArray mCheckStates;
    public RetailorListAdapter(Context context, List<retailors> ret) {
        super(context, R.layout.activity_retailorlists, ret);
        ret1=ret;
        mCheckStates = new SparseBooleanArray(ret.size());
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AppInfoHolder holder= null;
        View row = convertView;
        if (row==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row= inflater.inflate(R.layout.retailors_list, parent, false);
            holder = new AppInfoHolder();
            holder.retName=(TextView) row.findViewById(R.id.ret_name);
            holder.userName =(TextView)row.findViewById(R.id.username);
            holder.chkSelect = (CheckBox) row.findViewById(R.id.check);
            row.setTag(holder);
        }
        else {
            holder = (AppInfoHolder)row.getTag();
        }
        retailors retailorsList = ret1.get(position);
        holder.retName.setText(retailorsList.getname());
        holder.userName.setText(retailorsList.getusername());
        holder.chkSelect.setTag(position);
        holder.chkSelect.setChecked(mCheckStates.get(position, false));
        holder.chkSelect.setOnCheckedChangeListener(this);
        return row;
    }
    public boolean isChecked(int position) {
        return mCheckStates.get(position, false);
    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStates.put(position, isChecked);

    }

    public void toggle(int position) {
        setChecked(position, !isChecked(position));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mCheckStates.put((Integer) buttonView.getTag(), isChecked);
    }

    static class AppInfoHolder
    {
        TextView retName;
        TextView userName;
        CheckBox chkSelect;

    }


}
