package com.Client.pay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.adapter.Circular_adpter;
import com.Client.pay.adapter.MoneyAdapter;
import com.Client.pay.adapter.Recycle_grid_adapter;
import com.Client.pay.handleclick.FragmentClick;
import com.Client.pay.handleclick.NavClickListenere;
import com.Client.pay.model.CircularBean;
import com.Client.pay.model.Pay_Option_Bean;
import com.Client.pay.utils.Constants;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements NavClickListenere {


    private RelativeLayout relrecycle;
    private RecyclerView recycle_pay;
    private Pay_Option_Bean pay_option_bean;

    private ArrayList<CircularBean> paylist = new ArrayList<>();
    NavClickListenere navClickListenere = this;
    private Recycle_grid_adapter recycle_grid_adapter;
    private Context mcontext;
    private GridLayoutManager gridLayoutManager;

    private RelativeLayout relbill;
    private RelativeLayout relates;
    private TextView txtates;
    private View viewates;
    private TextView txtbill;

    private View viewrecharge;
    private TextView txtmainamt;
    private TextView txtapesamt;
    private TextView txtcreditamt;

    private FragmentClick fragmentClick;
    private LinearLayout llmobile;
    private LinearLayout lldth;
    private LinearLayout llbroadband;

    private LinearLayout llbillpayment;
    private RecyclerView recycle_payment;

    private CircularBean circularBean;
    private Circular_adpter circular_adpter;
    private ArrayList<CircularBean> circularlist = new ArrayList<>();
    private ArrayList<CircularBean> moneylist = new ArrayList<>();
    private GridLayoutManager cirgridlayoutmanager;
    private MoneyAdapter moneyAdapter;

    private RecyclerView recycle_money;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mcontext = getActivity();

        relrecycle = view.findViewById(R.id.relrecycle);
        recycle_pay = view.findViewById(R.id.recycle_pay);
        recycle_money = view.findViewById(R.id.recycle_money);
//        relbill = view.findViewById(R.id.relbill);
//        relates = view.findViewById(R.id.relates);
//
//        txtates = view.findViewById(R.id.txtates);
//        viewates = view.findViewById(R.id.viewates);
//        txtbill = view.findViewById(R.id.txtbill);
//        viewrecharge = view.findViewById(R.id.viewrecharge);
//
//        llmobile = view.findViewById(R.id.llmobile);
//        lldth = view.findViewById(R.id.lldth);
//        llbroadband = view.findViewById(R.id.llbroadband);
//
//        llbillpayment = view.findViewById(R.id.llbillpayment);

        recycle_payment = view.findViewById(R.id.recycle_payment);

        txtmainamt = view.findViewById(R.id.txtmainamt);
//        if (!Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.main_balance).isEmpty())
//            txtmainamt.setText(Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.main_balance));
//        else
//            txtmainamt.setText("0.00");
//
//        txtapesamt = view.findViewById(R.id.txtapesamt);
//        if (!Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.aeps_balnce).isEmpty())
//            txtapesamt.setText(Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.aeps_balnce));
//        else
//            txtapesamt.setText("0.00");
//
//        txtcreditamt = view.findViewById(R.id.txtcreditamt);
//        if (!Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.stock_balance).isEmpty())
//            txtcreditamt.setText(Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.stock_balance));
//        else
//            txtcreditamt.setText("0.00");

        setArraylist();
        setCircularArraylist();
        setAdaptor();
        setMoneyList();
        setOnclicklistener();


        return view;
    }

    private void setMoneyList() {
        CircularBean circularBean = new CircularBean();
        circularBean.setImageID(R.drawable.dmt);
        circularBean.setText("DMT");
        moneylist.add(circularBean);

        CircularBean circularBean1 = new CircularBean();
        circularBean1.setImageID(R.drawable.moneytransfer);
        circularBean1.setText("Money Transfer");
        moneylist.add(circularBean1);

        moneyAdapter = new MoneyAdapter(mcontext, moneylist, navClickListenere);
        recycle_money.setAdapter(moneyAdapter);
        gridLayoutManager = new GridLayoutManager(mcontext, 4);
        recycle_money.setLayoutManager(gridLayoutManager);


    }

    private void setCircularArraylist() {
        CircularBean circularBean = new CircularBean();
        circularBean.setImageID(R.drawable.mobile);
        circularBean.setText("Mobile");
        circularlist.add(circularBean);

        CircularBean circularBean1 = new CircularBean();
        circularBean1.setImageID(R.drawable.dth);
        circularBean1.setText("DTH");
        circularlist.add(circularBean1);

        CircularBean circularBean2 = new CircularBean();
        circularBean2.setImageID(R.drawable.data_card);
        circularBean2.setText("DataCard");
        circularlist.add(circularBean2);

        CircularBean circularBean3 = new CircularBean();
        circularBean3.setImageID(R.drawable.postpaid);
        circularBean3.setText("PostPaid");
        circularlist.add(circularBean3);

        CircularBean circularBean4 = new CircularBean();
        circularBean4.setImageID(R.drawable.broadband);
        circularBean4.setText("Broadband");
        circularlist.add(circularBean4);

        CircularBean circularBean5 = new CircularBean();
        circularBean5.setImageID(R.drawable.billpaymentnew);
        circularBean5.setText("Electricity");
        circularlist.add(circularBean5);

        CircularBean circularBean6 = new CircularBean();
        circularBean6.setImageID(R.drawable.gas);
        circularBean6.setText("Gas");
        circularlist.add(circularBean6);

        CircularBean circularBean7 = new CircularBean();
        circularBean7.setImageID(R.drawable.water);
        circularBean7.setText("Water");
        circularlist.add(circularBean7);

        CircularBean circularBean8 = new CircularBean();
        circularBean8.setImageID(R.drawable.fastpay);
        circularBean8.setText("FastPay");
        circularlist.add(circularBean8);

        CircularBean circularBean9 = new CircularBean();
        circularBean9.setImageID(R.drawable.insurance);
        circularBean9.setText("Insurance");
        circularlist.add(circularBean9);

        CircularBean circularBean10 = new CircularBean();
        circularBean10.setImageID(R.drawable.emi);
        circularBean10.setText("EMI");
        circularlist.add(circularBean10);

        CircularBean circularBean11 = new CircularBean();
        circularBean11.setImageID(R.drawable.landline);
        circularBean11.setText("LandLine");
        circularlist.add(circularBean11);

        circular_adpter = new Circular_adpter(mcontext, circularlist, navClickListenere);
        cirgridlayoutmanager = new GridLayoutManager(mcontext, 4);
        recycle_payment.setAdapter(circular_adpter);
        recycle_payment.setLayoutManager(cirgridlayoutmanager);


    }

    private void setOnclicklistener() {
//        relbill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewrecharge.setVisibility(View.VISIBLE);
//                viewates.setVisibility(View.INVISIBLE);
//
//                txtbill.setTextColor(getResources().getColor(R.color.skyblue));
//                viewrecharge.setBackgroundColor(getResources().getColor(R.color.skyblue));
//
//                txtates.setTextColor(getResources().getColor(R.color.textcolor));
//                viewates.setBackgroundColor(getResources().getColor(R.color.textcolor));
//            }
//        });
//
//        relates.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                viewrecharge.setVisibility(View.INVISIBLE);
//                viewates.setVisibility(View.VISIBLE);
//
//                txtbill.setTextColor(getResources().getColor(R.color.textcolor));
//                viewrecharge.setBackgroundColor(getResources().getColor(R.color.textcolor));
//
//                txtates.setTextColor(getResources().getColor(R.color.skyblue));
//                viewates.setBackgroundColor(getResources().getColor(R.color.skyblue));
//                fragmentClick.onClickFrag(Constants.AEPS_TAB_CLICK);
//            }
//        });

//        llmobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentClick.onClickFrag(Constants.mobileclick);
//            }
//        });
//
//        lldth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentClick.onClickFrag(Constants.DTH_CLICK);
//            }
//        });
//        llbroadband.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentClick.onClickFrag(Constants.Broad_band);
//
//            }
//        });
//
//        llbillpayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentClick.onClickFrag(Constants.Bill_payment);
//            }
//        });
    }

    private void setAdaptor() {

        recycle_grid_adapter = new Recycle_grid_adapter(mcontext, paylist, navClickListenere);
        recycle_pay.setAdapter(recycle_grid_adapter);

        gridLayoutManager = new GridLayoutManager(mcontext, 4);
        recycle_pay.setLayoutManager(gridLayoutManager);

    }

    private void setArraylist() {
        paylist.clear();

//        Pay_Option_Bean pay_option_bean = new Pay_Option_Bean();
//        pay_option_bean.setImageID(R.drawable.mobile);
//        pay_option_bean.setImageName("Mobile");
//        paylist.add(pay_option_bean);

//        Pay_Option_Bean pay_option_bean1 = new Pay_Option_Bean();
//        pay_option_bean1.setImageID(R.drawable.dth);
//        pay_option_bean1.setImageName("DTH");
//        paylist.add(pay_option_bean1);

//        Pay_Option_Bean pay_option_bean2 = new Pay_Option_Bean();
//        pay_option_bean2.setImageID(R.drawable.billpaymentnew);
//        pay_option_bean2.setImageName("Electricity");
//        paylist.add(pay_option_bean2);

//        Pay_Option_Bean pay_option_bean3 = new Pay_Option_Bean();
//        pay_option_bean3.setImageID(R.drawable.gas);
//        pay_option_bean3.setImageName("Gas");
//        paylist.add(pay_option_bean3);

//        Pay_Option_Bean pay_option_bean4 = new Pay_Option_Bean();
//        pay_option_bean4.setImageID(R.drawable.landline);
//        pay_option_bean4.setImageName("Landline");
//        paylist.add(pay_option_bean4);
//
//        Pay_Option_Bean pay_option_bean5 = new Pay_Option_Bean();
//        pay_option_bean5.setImageID(R.drawable.broadband);
//        pay_option_bean5.setImageName("Broadband");
//        paylist.add(pay_option_bean5);

        CircularBean circularBean = new CircularBean();
        circularBean.setImageID(R.drawable.apes);
        circularBean.setText("AEPS");
        paylist.add(circularBean);

        CircularBean circularBean1 = new CircularBean();
        circularBean1.setImageID(R.drawable.aepssettlement);
        circularBean1.setText("AEPS\nSettlement");
        paylist.add(circularBean1);
    }

    @Override
    public void onClick(int position, String where) {
//        Toast.makeText(mcontext, "buttonclick", Toast.LENGTH_SHORT).show();

        if (where.equalsIgnoreCase(Circular_adpter.class.getSimpleName())) {
            if (position == 0) {
                fragmentClick.onClickFrag(Constants.mobileclick);
            } else if (position == 1) {
                fragmentClick.onClickFrag(Constants.DTH_CLICK);
            } else if (position == 5) {
                fragmentClick.onClickFrag(Constants.Electricity);
            } else if (position == 2) {
                fragmentClick.onClickFrag(Constants.Data_card);
            } else if (position == 6) {
                fragmentClick.onClickFrag(Constants.gas);
            } else if (position == 7) {
                fragmentClick.onClickFrag(Constants.water);
            } else if (position == 3) {
                fragmentClick.onClickFrag(Constants.PostPaid);
            } else if (position == 4) {
                fragmentClick.onClickFrag(Constants.Broad_band);
            } else if (position == 8) {
                fragmentClick.onClickFrag(Constants.fastPay);
            } else if (position == 9) {
                fragmentClick.onClickFrag(Constants.Insurance);
            } else if (position == 10) {
                fragmentClick.onClickFrag(Constants.Emi);
            } else if (position == 11) {
                fragmentClick.onClickFrag(Constants.Landline);
            }
        } else if (where.equalsIgnoreCase(Recycle_grid_adapter.class.getSimpleName())) {
            if (position == 0) {
                fragmentClick.onClickFrag(Constants.AEPS_TAB_SELECTED);
            } else if (position == 1) {
                fragmentClick.onClickFrag(Constants.AEPS_SETTLEMENT);

            }

        } else if (where.equalsIgnoreCase(MoneyAdapter.class.getSimpleName())) {
            if (position == 0) {
                fragmentClick.onClickFrag(Constants.DMT);

            } else if (position == 1) {
                fragmentClick.onClickFrag(Constants.MoneyTransfer);

            }
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentClick = (FragmentClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement interface");
        }
    }

    @Override
    public void onDetach() {
        fragmentClick = null; // to avoid leak
        super.onDetach();
    }
}