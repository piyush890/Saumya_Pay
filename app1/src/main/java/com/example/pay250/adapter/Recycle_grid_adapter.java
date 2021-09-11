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

public class Recycle_grid_adapter extends RecyclerView.Adapter<Recycle_grid_adapter.ViewHolder> {

    private ArrayList<CircularBean> paylist = new ArrayList<>();
    Context mcontext;
    private NavClickListenere onClickListener;

    public Recycle_grid_adapter(Context mcontext, ArrayList<CircularBean> paylist, NavClickListenere navClickListenere) {
        this.mcontext = mcontext;
        this.paylist = paylist;
        this.onClickListener = navClickListenere;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.circularrow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imgmobile.setImageResource(paylist.get(position).getImageID());
        holder.txtmobile.setText(paylist.get(position).getText());

        holder.llmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position,Recycle_grid_adapter.class.getSimpleName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return paylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llmobile;
        ImageView imgmobile;
        TextView txtmobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llmobile = itemView.findViewById(R.id.llmobile);
            imgmobile = itemView.findViewById(R.id.imgmobile);
            txtmobile = itemView.findViewById(R.id.txtmobile);
        }
    }
}
