package com.Client.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.handleclick.NavClickListenere;
import com.Client.pay.model.Menu_bean;

import java.util.ArrayList;

public class NavMenuAdapter extends RecyclerView.Adapter<NavMenuAdapter.MyViewHolder> {

    private Context mcontext;
    private ArrayList<Menu_bean> menulist = new ArrayList<>();
    private NavClickListenere navClickListenere;

    public NavMenuAdapter(Context mcontext, ArrayList<Menu_bean> menulist, NavClickListenere navClickListenere) {
        this.mcontext = mcontext;
        this.menulist = menulist;
        this.navClickListenere = navClickListenere;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.recycle_nav_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (menulist.size() != 0) {
            holder.rel_main.setBackgroundColor(mcontext.getResources().getColor(R.color.white));
            holder.img_smallicon.setVisibility(View.VISIBLE);
            holder.horizontal_line.setVisibility(View.VISIBLE);
            holder.img_smallicon.setImageResource(menulist.get(position).getImageID());
            holder.txt_menuname.setText(menulist.get(position).getMenuImg());
            holder.txt_menuname.setTextColor(mcontext.getResources().getColor(R.color.grey));
            holder.horizontal_line.setVisibility(View.VISIBLE);


            if (position == 4) {
                holder.rel_main.setBackgroundColor(mcontext.getResources().getColor(R.color.grey));
                holder.img_smallicon.setVisibility(View.GONE);
                holder.horizontal_line.setVisibility(View.GONE);
                holder.txt_menuname.setTextColor(mcontext.getResources().getColor(R.color.white));
            }

            if (position == menulist.size() - 1) {
                holder.horizontal_line.setVisibility(View.INVISIBLE);
            }

            holder.rel_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navClickListenere.onClick(position,"NavMenuAdpater");
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return menulist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rel_container;
        private ImageView img_smallicon;
        private TextView txt_menuname;
        private ImageView img_arrow;
        private View horizontal_line;
        private RelativeLayout rel_main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rel_container = itemView.findViewById(R.id.rel_container);
            img_smallicon = itemView.findViewById(R.id.img_smallicon);
            txt_menuname = itemView.findViewById(R.id.txt_menuname);
            img_arrow = itemView.findViewById(R.id.img_arrow);
            horizontal_line = itemView.findViewById(R.id.horizontal_line);
            rel_main = itemView.findViewById(R.id.rel_main);
        }
    }
}
