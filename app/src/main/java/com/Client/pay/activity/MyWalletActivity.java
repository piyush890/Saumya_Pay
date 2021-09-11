package com.Client.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Client.pay.R;
import com.Client.pay.adapter.CustomeSpinnerAdaptor;
import com.Client.pay.adapter.CustomeSpinnerAdaptorType;
import com.Client.pay.model.AcCheck;
import com.Client.pay.model.AccountRequest;
import com.Client.pay.model.Balance;
import com.Client.pay.model.BalanceModel;
import com.Client.pay.model.CustomeSpinnerBean;
import com.Client.pay.model.SettlementModel;
import com.Client.pay.retrofit.APICallBack;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletActivity extends AppCompatActivity {

    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;
    private RelativeLayout rel_main;
    private Boolean isTypeSelected = false;
    private RelativeLayout rel_spinner;
    private Spinner spinnerstatus;
    private Intent intent;
    private SettlementModel settlementModel;
    private BalanceModel balanceModel;
//    private Spinner spinnertype;
    private TextView txtmainamt, bankNameTextView, accountTextView,ifscTextView;
    private TextView txtapesamt;
    private TextView txtcreditamt;
    private EditText edtrecharamt, etRemark;
    private TextView addAccountTextView;
    private RelativeLayout detailRelativeLayout, addAccountRelativeLayout, relenter;
    private CustomeSpinnerBean customspinnerbin;
    private CustomeSpinnerAdaptorType customeSpinnerAdaptorType;
    private ArrayList<CustomeSpinnerBean> customList = new ArrayList<>();
//    private ArrayList<Customspinnerbin> customlist = new ArrayList<>();
    private String typ;
    CustomeSpinnerAdaptor customeSpinnerAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        overridePendingTransition(R.anim.enter, R.anim.exit);

        checkAccount();
        initUI();
        setList();
        setClickListener();


    }

    private void setList() {
        CustomeSpinnerBean customeSpinnerBean = new CustomeSpinnerBean();
        customeSpinnerBean.setPaymentName("Select Type");
        customeSpinnerBean.setPaymentType("0");
        customList.add(customeSpinnerBean);

        CustomeSpinnerBean customeSpinnerBean1 = new CustomeSpinnerBean();
        customeSpinnerBean1.setPaymentName("Move To Wallet");
        customeSpinnerBean1.setPaymentType("1");
        customList.add(customeSpinnerBean1);

        CustomeSpinnerBean customeSpinnerBean2 = new CustomeSpinnerBean();
        customeSpinnerBean2.setPaymentName(" Move To Bank");
        customeSpinnerBean2.setPaymentType("2");
        customList.add(customeSpinnerBean2);

        customeSpinnerAdaptorType = new CustomeSpinnerAdaptorType(context, R.layout.customspinnerorwtype, customList);
        spinnerstatus.setAdapter(customeSpinnerAdaptorType);
    }

    private void initUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);

        imgmenu = findViewById(R.id.imgmenu);
        imgmenu.setVisibility(View.INVISIBLE);

        imgback = findViewById(R.id.imgback);
        imgback.setVisibility(View.VISIBLE);

        txt_home = findViewById(R.id.txt_home);
        txt_home.setText(R.string.wallet);

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);

        edtrecharamt = findViewById(R.id.edtrecharamt);

        txtmainamt = findViewById(R.id.txtmainamt);
        txtapesamt = findViewById(R.id.txtapesamt);
        txtcreditamt = findViewById(R.id.txtcreditamt);
        accountTextView = findViewById(R.id.txt_accno);
        ifscTextView = findViewById(R.id.txt_ifsc);
        bankNameTextView = findViewById(R.id.txt_bank);
        etRemark = findViewById(R.id.edt_remark);
        addAccountTextView = findViewById(R.id.add_account_textview);

        detailRelativeLayout = findViewById(R.id.detail);
        addAccountRelativeLayout = findViewById(R.id.addaccount_layout);
        relenter = findViewById(R.id.relenter);
        spinnerstatus=findViewById(R.id.spinnerstatus);

//        if (!Utilities.getInstance().getPreference(context, SharedPreferenceKeys.main_balance).isEmpty())
//            txtmainamt.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.main_balance));
//        else
//            txtmainamt.setText("0.00");
//
//        if (!Utilities.getInstance().getPreference(context, SharedPreferenceKeys.aeps_balnce).isEmpty())
//            txtapesamt.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.aeps_balnce));
//        else
//            txtapesamt.setText("0.00");
//
//        if (!Utilities.getInstance().getPreference(context, SharedPreferenceKeys.stock_balance).isEmpty())
//            txtcreditamt.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.stock_balance));
//        else
//            txtcreditamt.setText("0.00");

//        customspinnerbin = new CustomeSpinnerBean();
//        customspinnerbin.setName("Select Payment Method");
//        customlist.add(customspinnerbin);

//        customeSpinnerAdaptor = new CustomeSpinnerAdaptor(context, R.layout.customspinnerorw, customlist);
//        spinnerstatus.setAdapter(customeSpinnerAdaptor);
    }

    private void setClickListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinnerstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isTypeSelected = false;
                } else {
                    isTypeSelected = true;
                    typ = customList.get(position).getPaymentType();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        relenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtrecharamt.getText().toString().isEmpty() || !isTypeSelected) {
                    if (!isTypeSelected) {
                        Toast.makeText(context, "Please select the Whether you want to move to bank or wallet ", Toast.LENGTH_SHORT).show();
                    } else if (edtrecharamt.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Please enter the amount", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    callStatementAPI();
                }
            }
        });


        addAccountRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, BankDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

    private void checkAccount() {
        AccountRequest obj = new AccountRequest();
        obj.setUser_id(Utilities.getInstance().getPreference(this,SharedPreferenceKeys.user_id));
        obj.setAeps("1");
        accountCheckAPI(obj);


    }

    private void callStatementAPI() {
        settlementModel = new SettlementModel();
        settlementModel.setUser_id(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.user_id));
        settlementModel.setAmt(edtrecharamt.getText().toString());
        settlementModel.setTyp(typ);

        if (Utilities.getInstance().isNetworkAvailable(this)) {
            Utilities.getInstance().showProgressDialog(this);
            APIService.getInstance().getBaseUrl()
                    .stmt(settlementModel)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Utilities.getInstance().hideProgressdialog();
                            try {
                                Toast.makeText(context, "" + response.body().string(), Toast.LENGTH_SHORT).show();
                                checkBalance();
                                clear();
//                                Intent i = new Intent(MyWalletActivity.this, MyWalletActivity.class);
//                                startActivity(i);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Utilities.getInstance().hideProgressdialog();
                            Toast.makeText(context, "" + t, Toast.LENGTH_SHORT).show();

                        }
                    });

        } else {
            Utilities.getInstance().hideProgressdialog();
            Toast.makeText(context, "Please check the internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        edtrecharamt.setText("");
        etRemark.setText("");
        Utilities.getInstance().hideKeyBoard(MyWalletActivity.this);
    }

    private void accountCheckAPI(AccountRequest obj) {
        if (Utilities.getInstance().isNetworkAvailable(this)) {
            Utilities.getInstance().showProgressDialog(this);
            APIService.getInstance().getBaseUrl()
                    .accountCheck(obj)
                    .enqueue(new APICallBack<AcCheck>() {
                        @Override
                        protected void success(AcCheck model) {
                            Utilities.getInstance().hideProgressdialog();
                            if (model != null) {
                                if (model.getStatus().equals("success")) {
                                    checkBalance();
                                    detailRelativeLayout.setVisibility(View.VISIBLE);
                                    bankNameTextView.setText(model.getName());
                                    accountTextView.setText(model.getAccount());
                                    ifscTextView.setText(model.getIfsc());

                                } else {
                                    if (model.getStatus().equals("fail")) {

                                        addAccountRelativeLayout.setVisibility(View.VISIBLE);
                                        detailRelativeLayout.setVisibility(View.INVISIBLE);

//                                        Toast.makeText(context, "Kyc not submitted", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }

                        @Override
                        protected void failure(String errorMsg) {
                            Utilities.getInstance().hideProgressdialog();
                            Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        protected void onFailure(String failureReason) {
                            Utilities.getInstance().hideProgressdialog();
                            Toast.makeText(context, "" + failureReason, Toast.LENGTH_SHORT).show();


                        }
                    });
        } else {
            Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkBalance() {
        balanceModel= new BalanceModel();
        balanceModel.setUser_id(Utilities.getInstance().getPreference(this, SharedPreferenceKeys.user_id));
        if (Utilities.getInstance().isNetworkAvailable(this)) {
            Utilities.getInstance().showProgressDialog(this);
            APIService.getInstance().getBaseUrl()
                    .balance(balanceModel)
                    .enqueue(new Callback<Balance>() {
                        @Override
                        public void onResponse(Call<Balance> call, Response<Balance> response) {
                            Utilities.getInstance().hideProgressdialog();
                            txtmainamt.setText(String.format("₹ %s", response.body().getMain()));
                            txtapesamt.setText(String.format("₹ %s", response.body().getAeps()));

                            if (!Utilities.getInstance().getPreference(context, SharedPreferenceKeys.stock_balance).isEmpty())
                                txtcreditamt.setText(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.stock_balance));
                            else
                                txtcreditamt.setText("0.00");

                        }




                        @Override
                        public void onFailure(Call<Balance> call, Throwable t) {
                            Utilities.getInstance().hideProgressdialog();
                            Toast.makeText(context, "" + t, Toast.LENGTH_SHORT).show();

                        }
                    });


        } else {
            Utilities.getInstance().hideProgressdialog();
            Toast.makeText(this, "Please check the internet connection", Toast.LENGTH_SHORT).show();
        }
    }
}