package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.handleclick.ItemClickListener;
import com.Client.pay.model.OperaterBean;

import java.util.ArrayList;

public class MobileBottomSheetAdapter extends RecyclerView.Adapter<MobileBottomSheetAdapter.ViewHolder> {

    private ArrayList<OperaterBean> operterlist = new ArrayList<>();
    private Context mcontext;
    private ItemClickListener buttonClickListener;

    public MobileBottomSheetAdapter(Context mcontext, ArrayList<OperaterBean> operaterlist, ItemClickListener buttonClickListener) {
        this.operterlist = operaterlist;
        this.mcontext = mcontext;
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.customspinnerorw, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lbloperatortype.setText(operterlist.get(position).getOperatorType());
        holder.llmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickListener.itemOnClick(null,operterlist.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return operterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llmain;
        private TextView lbloperatortype;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llmain = itemView.findViewById(R.id.llmain);
            lbloperatortype = itemView.findViewById(R.id.lbloperatortype);
        }
    }
}
