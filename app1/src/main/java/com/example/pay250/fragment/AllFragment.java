package com.Client.pay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.adapter.HistoryAdapter;
import com.Client.pay.model.HistoryBean;

import java.util.ArrayList;


public class AllFragment extends Fragment {


    private RecyclerView recyle_history;
    private View view;
    private ArrayList<HistoryBean> historylist = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private Context mcontext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all, container, false);
        recyle_history = view.findViewById(R.id.recyle_history);
        mcontext = getActivity();

        setHistoryList();

        return view;
    }

    private void setHistoryList() {
        historylist.clear();

        HistoryBean historyBean = new HistoryBean();
        historyBean.setDate("2020-08-18T00:00:00");
        historyBean.setMoney("Rs.10.0");
        historyBean.setPhone_no("9404443585");
        historyBean.setTxt_number("0000917267");
        historyBean.setStatus("Success");
        historylist.add(historyBean);

        HistoryBean historyBean1 = new HistoryBean();
        historyBean1.setDate("2020-08-18T00:00:00");
        historyBean1.setMoney("Rs.10.0");
        historyBean1.setPhone_no("7756966493");
        historyBean1.setTxt_number("0000157642");
        historyBean1.setStatus("Success");

        historylist.add(historyBean1);

        HistoryBean historyBean2 = new HistoryBean();
        historyBean2.setDate("2020-08-18T00:00:00");
        historyBean2.setMoney("Rs.10.0");
        historyBean2.setPhone_no("8600259030");
        historyBean2.setTxt_number("0000667688");
        historyBean2.setStatus("Success");

        historylist.add(historyBean2);

        HistoryBean historyBean3 = new HistoryBean();
        historyBean3.setDate("2020-08-18T00:00:00");
        historyBean3.setMoney("Rs.10.0");
        historyBean3.setPhone_no("9404443585");
        historyBean3.setTxt_number("0000917267");
        historyBean3.setStatus("Success");

        historylist.add(historyBean3);

        HistoryBean historyBean4 = new HistoryBean();
        historyBean4.setDate("2020-08-18T00:00:00");
        historyBean4.setMoney("Rs.10.0");
        historyBean4.setPhone_no("8647117675");
        historyBean4.setTxt_number("0000890984");
        historyBean4.setStatus("Success");

        historylist.add(historyBean4);

        HistoryBean historyBean5 = new HistoryBean();
        historyBean5.setDate("2020-08-18T00:00:00");
        historyBean5.setMoney("Rs.10.0");
        historyBean5.setPhone_no("9404443585");
        historyBean5.setTxt_number("0000917267");
        historyBean5.setStatus("Success");

        historylist.add(historyBean5);

        HistoryBean historyBean6 = new HistoryBean();
        historyBean6.setDate("2020-08-18T00:00:00");
        historyBean6.setMoney("Rs.10.0");
        historyBean6.setPhone_no("9404443585");
        historyBean6.setTxt_number("0000917267");
        historyBean6.setStatus("Success");

        historylist.add(historyBean6);


        historyAdapter = new HistoryAdapter(mcontext, historylist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
        recyle_history.setAdapter(historyAdapter);
        recyle_history.setLayoutManager(linearLayoutManager);

    }
}