package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.Client.pay.R;
import com.Client.pay.model.CustomeSpinnerBean;

import java.util.ArrayList;

public class CustomeSpinnerAdaptorType extends ArrayAdapter<CustomeSpinnerBean> {
    private View view;
    private Context mcontext;
    private ArrayList<CustomeSpinnerBean> customspinnerlist;
    private TextView lblstate;

    public CustomeSpinnerAdaptorType(@NonNull Context context, int resource, ArrayList<CustomeSpinnerBean> customlist) {
        super(context, resource);
        this.mcontext = context;
        this.customspinnerlist = customlist;

    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        view=convertView;
        view = LayoutInflater.from(mcontext).inflate(R.layout.customspinnerorwtype, parent, false);

        lblstate = view.findViewById(R.id.lblstate);
        if(position==0){
            lblstate.setTextColor(ContextCompat.getColor(mcontext,R.color.greyborder));
        }
        lblstate.setText(customspinnerlist.get(position).getPaymentName());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getDropDownView(position, convertView, parent);
//        Toast.makeText(mcontext, ""+customspinnerlist.size(), Toast.LENGTH_SHORT).show();

        return getCustomView(position,convertView,parent);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        return getCustomView(position,convertView,parent);
    }

    @Override
    public int getCount() {
//        Toast.makeText(mcontext, ""+customspinnerlist.size(), Toast.LENGTH_SHORT).show();

        return customspinnerlist.size();
    }

}
