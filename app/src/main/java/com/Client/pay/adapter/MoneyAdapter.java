package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.handleclick.NavClickListenere;
import com.Client.pay.model.CircularBean;

import java.util.ArrayList;

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<CircularBean> moneylist = new ArrayList<>();
    private NavClickListenere navClickListenere;

    public MoneyAdapter(Context mcontext, ArrayList<CircularBean> moneylist, NavClickListenere navClickListenere) {
        this.mcontext = mcontext;
        this.moneylist = moneylist;
        this.navClickListenere = navClickListenere;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.circularrow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imgmobile.setImageResource(moneylist.get(position).getImageID());
        holder.txtmobile.setText(moneylist.get(position).getText());

        holder.llmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navClickListenere.onClick(position,MoneyAdapter.class.getSimpleName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return moneylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llmobile;
        private RelativeLayout relmobile;
        private ImageView imgmobile;
        private TextView txtmobile;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llmobile = itemView.findViewById(R.id.llmobile);
            relmobile = itemView.findViewById(R.id.relmobile);
            imgmobile = itemView.findViewById(R.id.imgmobile);
            txtmobile = itemView.findViewById(R.id.txtmobile);
        }
    }
}
