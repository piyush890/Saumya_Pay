package com.Client.pay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.adapter.APESSettlementAdapter;
import com.Client.pay.handleclick.FragmentClick;
import com.Client.pay.handleclick.ItemClickListener;
import com.Client.pay.model.AepsSettlementModel;
import com.Client.pay.model.OperaterBean;
import com.Client.pay.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class AEPS_Fragment extends BottomSheetDialogFragment implements ItemClickListener {
    private RecyclerView recyclbottom;
    private View view;
    private APESSettlementAdapter apesSettlementAdapter;
    private ArrayList<AepsSettlementModel> apeslist = new ArrayList<>();
    private Context mcontext;
    private ItemClickListener itemClickListener = this;
    private LinearLayoutManager linearLayoutManager;
    private FragmentClick fragmentClickListenerBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_a_e_p_s_, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        mcontext = getActivity();
        recyclbottom = view.findViewById(R.id.recyclbottom);
        setAdapter();

    }

    private void setAdapter() {
        apeslist.clear();
        AepsSettlementModel aepsSettlementModel = new AepsSettlementModel();
        aepsSettlementModel.setAepsType("AEPS");
        apeslist.add(aepsSettlementModel);

        AepsSettlementModel aepsSettlementModel1 = new AepsSettlementModel();
        aepsSettlementModel1.setAepsType("AEPS settelement");
        apeslist.add(aepsSettlementModel1);

        apesSettlementAdapter = new APESSettlementAdapter(mcontext, itemClickListener, apeslist);
        linearLayoutManager = new LinearLayoutManager(mcontext);
        recyclbottom.setAdapter(apesSettlementAdapter);
        recyclbottom.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentClickListenerBean = (FragmentClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement interface");
        }
    }

    @Override
    public void onDetach() {
        fragmentClickListenerBean = null;
        super.onDetach();
    }

    @Override
    public void itemOnClick(AepsSettlementModel model, OperaterBean operater, int pos) {
        if (pos == 0)
            fragmentClickListenerBean.onClickFrag(Constants.AEPS_TAB_SELECTED);
        else if (pos == 1)
            fragmentClickListenerBean.onClickFrag(Constants.AEPS_SETTLEMENT);

        dismiss();

    }
}