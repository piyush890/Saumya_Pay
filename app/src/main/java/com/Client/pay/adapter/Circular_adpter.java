package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.handleclick.NavClickListenere;
import com.Client.pay.model.CircularBean;

import java.util.ArrayList;

public class Circular_adpter extends RecyclerView.Adapter<Circular_adpter.MyViewHolder> {

    Context mcontext;
    private ArrayList<CircularBean> circularlist = new ArrayList<>();
    private NavClickListenere navClickListenere;

    public Circular_adpter(Context mcontext, ArrayList<CircularBean> circularlist, NavClickListenere navClickListenere) {
        this.mcontext = mcontext;
        this.circularlist = circularlist;
        this.navClickListenere = navClickListenere;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.circularrow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.imgmobile.setImageResource(circularlist.get(position).getImageID());
        holder.txtmobile.setText(circularlist.get(position).getText());

        holder.llmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClickListenere.onClick(position,Circular_adpter.class.getSimpleName());
            }
        });


    }

    @Override
    public int getItemCount() {
        return circularlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgmobile;
        private TextView txtmobile;
        private LinearLayout llmobile;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgmobile = itemView.findViewById(R.id.imgmobile);
            txtmobile = itemView.findViewById(R.id.txtmobile);
            llmobile = itemView.findViewById(R.id.llmobile);
        }
    }
}
