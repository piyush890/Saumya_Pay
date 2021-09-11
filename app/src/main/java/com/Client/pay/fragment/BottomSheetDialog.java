package com.Client.pay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.activity.BroadBandActivity;
import com.Client.pay.activity.DTHRechargeActivity;
import com.Client.pay.activity.MobileRechargeActivity;
import com.Client.pay.adapter.MobileBottomSheetAdapter;
import com.Client.pay.handleclick.FragmentClickListenerBean;
import com.Client.pay.handleclick.ItemClickListener;
import com.Client.pay.model.AepsSettlementModel;
import com.Client.pay.model.OperaterBean;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment implements ItemClickListener {
    private RecyclerView recyclbottom;
    private MobileBottomSheetAdapter mobileBottomSheetAdapter;
    private ArrayList<OperaterBean> operaterlist = new ArrayList<>();
    private Context mcontext;
    private ItemClickListener itemClickListener;
    private String operator_type;
    private FragmentClickListenerBean fragmentClick;
    private String classname;

    public BottomSheetDialog(String simpleName) {
        classname = simpleName;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.modaloperaterbottomsheet,
                container, false);
        mcontext = getActivity();
        itemClickListener = this;
        recyclbottom = v.findViewById(R.id.recyclbottom);
        setOperatorList();
        setAdaptor();
        return v;
    }


    private void setOperatorList() {
        if (classname.equalsIgnoreCase(MobileRechargeActivity.class.getSimpleName())) {
            OperaterBean operaterBean = new OperaterBean();
            operaterBean.setOperatorType("BSNL");
            operaterBean.setCode("11");
            operaterlist.add(operaterBean);

            OperaterBean operaterBean1 = new OperaterBean();
            operaterBean1.setOperatorType("VODAFONE");
            operaterBean1.setCode("2");
            operaterlist.add(operaterBean1);

            OperaterBean operaterBean2 = new OperaterBean();
            operaterBean2.setOperatorType("IDEA");
            operaterBean2.setCode("3");
            operaterlist.add(operaterBean2);

            OperaterBean operaterBean3 = new OperaterBean();
            operaterBean3.setOperatorType("RELIANCE JIO");
            operaterBean3.setCode("15");
            operaterlist.add(operaterBean3);

            OperaterBean operaterBean4 = new OperaterBean();
            operaterBean4.setOperatorType("TATA DOCOMO");
            operaterBean4.setCode("5");
            operaterlist.add(operaterBean4);

            OperaterBean operaterBean5 = new OperaterBean();
            operaterBean5.setOperatorType("TATA INDICOM");
            operaterBean5.setCode("4");
            operaterlist.add(operaterBean5);

            OperaterBean operaterBean6 = new OperaterBean();
            operaterBean6.setOperatorType("MTNL");
            operaterBean6.setCode("9");
            operaterlist.add(operaterBean6);
        } else if (classname.equalsIgnoreCase(DTHRechargeActivity.class.getSimpleName())) {

            OperaterBean operaterBean = new OperaterBean();
            operaterBean.setOperatorType("AIRTEL DIGITAL TV");
            operaterBean.setCode("16");
            operaterlist.add(operaterBean);

            OperaterBean operaterBean1 = new OperaterBean();
            operaterBean1.setOperatorType("DISH TV");
            operaterBean1.setCode("17");
            operaterlist.add(operaterBean1);

            OperaterBean operaterBean2 = new OperaterBean();
            operaterBean2.setOperatorType("TATA SKY");
            operaterBean2.setCode("18");
            operaterlist.add(operaterBean2);

            OperaterBean operaterBean3 = new OperaterBean();
            operaterBean3.setOperatorType("SUN TV");
            operaterBean3.setCode("19");
            operaterlist.add(operaterBean3);

            OperaterBean operaterBean4 = new OperaterBean();
            operaterBean4.setOperatorType("VIDEOCON D2H TV");
            operaterBean4.setCode("20");
            operaterlist.add(operaterBean4);

            OperaterBean operaterBean5 = new OperaterBean();
            operaterBean5.setOperatorType("RELIANCE BIG TV");
            operaterBean5.setCode("21");
            operaterlist.add(operaterBean5);
        } else if (classname.equalsIgnoreCase(BroadBandActivity.class.getSimpleName())) {

            OperaterBean obj1 = new OperaterBean();
            obj1.setCode("22");
            obj1.setOperatorType("MTS MBLAZE");
            operaterlist.add(obj1);

            OperaterBean obj2 = new OperaterBean();
            obj2.setCode("23");
            obj2.setOperatorType("MTS MBROWSE");
            operaterlist.add(obj2);

            OperaterBean obj3 = new OperaterBean();
            obj3.setCode("24");
            obj3.setOperatorType("RELIANCE NETCONNECT");
            operaterlist.add(obj3);

            OperaterBean obj4 = new OperaterBean();
            obj4.setCode("25");
            obj4.setOperatorType("TATA PHOTON WHIZ");
            operaterlist.add(obj4);

            OperaterBean obj5 = new OperaterBean();
            obj5.setCode("26");
            obj5.setOperatorType("TATA PHOTON MAX");
            operaterlist.add(obj5);

            OperaterBean obj6 = new OperaterBean();
            obj6.setCode("27");
            obj6.setOperatorType("TATA PHOTON PLUS");
            operaterlist.add(obj6);
        }
    }

    private void setAdaptor() {
        mobileBottomSheetAdapter = new MobileBottomSheetAdapter(mcontext, operaterlist, itemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
        recyclbottom.setLayoutManager(linearLayoutManager);
        recyclbottom.setAdapter(mobileBottomSheetAdapter);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentClick = (FragmentClickListenerBean) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement interface");
        }
    }

    @Override
    public void onDetach() {
        fragmentClick = null;
        super.onDetach();
    }


    @Override
    public void itemOnClick(AepsSettlementModel aepsSettlementModel,OperaterBean operater, int pos) {
        fragmentClick.itemOnClick(aepsSettlementModel,operater, pos);
        dismiss();
    }
}
