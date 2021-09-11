package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.model.HistoryBean;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context mcontext;
    private ArrayList<HistoryBean> historylist;

    public HistoryAdapter(Context mcontext, ArrayList<HistoryBean> historylist) {
        this.mcontext = mcontext;
        this.historylist = historylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.row_history_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_txno.setText(historylist.get(position).getTxt_number());
        holder.txt_status.setText(historylist.get(position).getStatus());
        holder.amt.setText(historylist.get(position).getMoney());
        holder.lblnumber.setText(historylist.get(position).getPhone_no());

    }

    @Override
    public int getItemCount() {
        return historylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_txno;
        private TextView txt_status;
        private TextView amt;
        private TextView lblnumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_txno = itemView.findViewById(R.id.txt_txno);
            txt_status = itemView.findViewById(R.id.txt_status);
            amt = itemView.findViewById(R.id.amt);
            lblnumber = itemView.findViewById(R.id.lblnumber);
        }
    }
}
