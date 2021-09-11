package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Client.pay.R;
import com.Client.pay.model.Customspinnerbin;

import java.util.ArrayList;


public class CustomeSpinnerAdaptor extends ArrayAdapter {

    private Context mcontext;
    private TextView lbljobtitle;
    private ArrayList<Customspinnerbin> stringobj=new ArrayList<>();
    private View view;

    public CustomeSpinnerAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Customspinnerbin> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.stringobj = objects;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.customspinnerorw, parent, false);
        lbljobtitle = view.findViewById(R.id.lbloperatortype);
        lbljobtitle.setText(stringobj.get(position).getName());

        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
