package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.model.ReportBean;

import java.util.ArrayList;

public class ReportAdaptor extends RecyclerView.Adapter<ReportAdaptor.MyViewHolder> {
    private Context mcontext;
    private ArrayList<ReportBean> reportList = new ArrayList<>();

    public ReportAdaptor(Context context, ArrayList<ReportBean> reportBeans) {
        this.mcontext = context;
        this.reportList = reportBeans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.row_reporrt_recyccle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_txno.setText(reportList.get(position).getTxnid());
        holder.lblnumber.setText(reportList.get(position).getNumber());
        holder.totalbalamt.setText(reportList.get(position).getTotal_balance());
        holder.profiltamt.setText(reportList.get(position).getProfit());
        holder.lbldate.setText(reportList.get(position).getCreated_at());
        holder.amt.setText(reportList.get(position).getAmount());
        if (reportList.get(position).getStatus_id().equalsIgnoreCase("1")) {
            holder.txt_status.setText("Success");
        }else{
            holder.txt_status.setText("Failure");
        }
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_txno;
        private TextView lbldate;
        private TextView amt;
        private TextView profiltamt;
        private TextView totalbalamt;
        private TextView lblnumber;
        private TextView txt_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_txno = itemView.findViewById(R.id.txt_txno);
            lbldate = itemView.findViewById(R.id.lbldate);
            amt = itemView.findViewById(R.id.amt);
            profiltamt = itemView.findViewById(R.id.profiltamt);
            totalbalamt = itemView.findViewById(R.id.totalbalamt);
            lblnumber = itemView.findViewById(R.id.lblnumber);
            txt_status = itemView.findViewById(R.id.txt_status);
        }
    }
}
