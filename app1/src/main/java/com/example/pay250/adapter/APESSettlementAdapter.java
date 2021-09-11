package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.handleclick.ItemClickListener;
import com.Client.pay.model.AepsSettlementModel;

import java.util.ArrayList;

public class APESSettlementAdapter extends RecyclerView.Adapter<APESSettlementAdapter.MyViewHolder> {
    private Context mcontext;
    private ItemClickListener itemClickListener;
    private ArrayList<AepsSettlementModel> aepslist = new ArrayList<>();

    public APESSettlementAdapter(Context mcontext, ItemClickListener itemClickListener, ArrayList<AepsSettlementModel> apeslist) {
        this.mcontext = mcontext;
        this.itemClickListener = itemClickListener;
        this.aepslist = apeslist;
    }

    @Override
    public APESSettlementAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.customspinnerorw, parent, false));
    }

    @Override
    public void onBindViewHolder(APESSettlementAdapter.MyViewHolder holder, int position) {
        holder.lbloperatortype.setText(aepslist.get(position).getAepsType());
        holder.lbloperatortype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemOnClick(aepslist.get(position),null,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return aepslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView lbloperatortype;

        public MyViewHolder(View itemView) {
            super(itemView);
            lbloperatortype = itemView.findViewById(R.id.lbloperatortype);

        }
    }
}
