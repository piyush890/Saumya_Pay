package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.model.DisplayAcModel;

import java.util.ArrayList;

public class AcDisplayAdapter extends RecyclerView.Adapter<AcDisplayAdapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<DisplayAcModel> reportList = new ArrayList<>();
    String name, ifsc, account, bankName;
    private ArrayList<DisplayAcModel> displayAcModels = new ArrayList<>();
    PayInterface payInterface;

    public AcDisplayAdapter(Context context, ArrayList<DisplayAcModel> displayAcModels, PayInterface payInterface) {
        this.mcontext = context;
        this.displayAcModels = displayAcModels;
        this.payInterface = payInterface;
    }


    @NonNull
    @Override
    public AcDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AcDisplayAdapter.MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.row_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AcDisplayAdapter.MyViewHolder holder, int position) {
        holder.accountNumber.setText(displayAcModels.get(position).getHaccount());
        holder.ifsc.setText(displayAcModels.get(position).getHifsc());
        holder.bankName.setText(displayAcModels.get(position).getHbank());
        holder.accountHolderName.setText(displayAcModels.get(position).getHname());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payInterface.onItemDelete(displayAcModels.get(position).getHaccount());
            }
        });
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.payLayout.setVisibility(View.VISIBLE);
            }
        });

        holder.pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.amountEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(mcontext, "Please enter the amount", Toast.LENGTH_SHORT).show();
                }
                else{
                    payInterface.onItemPay(holder.amountEditText.getText().toString());
                    holder.payLayout.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return displayAcModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bankName, accountHolderName, ifsc, accountNumber, pay, delete,pay1;
        LinearLayout payLayout;
        EditText amountEditText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bankName = itemView.findViewById(R.id.txt_bank);
            accountHolderName = itemView.findViewById(R.id.ac_holder_textview);
            ifsc = itemView.findViewById(R.id.txt_ifsc);
            accountNumber = itemView.findViewById(R.id.txt_accno);
            pay = itemView.findViewById(R.id.pay);
            pay1 = itemView.findViewById(R.id.pay1);
            delete = itemView.findViewById(R.id.delete);
            payLayout = itemView.findViewById(R.id.pay_layout);
            amountEditText = itemView.findViewById(R.id.edtrecharamt);
        }
    }
    public interface PayInterface {

        void onItemPay(String amount);
        void onItemDelete(String delete);

    }
}