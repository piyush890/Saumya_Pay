package com.Client.pay.activity;

import android.content.Context;
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
import com.Client.pay.adapter.CustomeSpinnerAdaptorType;
import com.Client.pay.model.CustomeSpinnerBean;
import com.Client.pay.model.SettlementModel;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettleMentActivity extends AppCompatActivity {

    private View toolbar;

    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;
    private Boolean isTypeSelected = false;

    private EditText edtrecharamt;
    private RelativeLayout relenter;

    private SettlementModel settlementModel;
    private Spinner spinnertype;
    private CustomeSpinnerAdaptorType customeSpinnerAdaptorType;
    private ArrayList<CustomeSpinnerBean> customList = new ArrayList<>();
    private String typ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_ment);
        initUI();
        setList();
        serClickListener();
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
        spinnertype.setAdapter(customeSpinnerAdaptorType);

    }


    private void initUI() {
        context = this;

        toolbar = findViewById(R.id.toolbar);
        imgmenu = findViewById(R.id.imgmenu);
        imgback = findViewById(R.id.imgback);
        txt_home = findViewById(R.id.txt_home);
        spinnertype = findViewById(R.id.spinnertype);

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);
        imgback.setVisibility(View.VISIBLE);
        imgmenu.setVisibility(View.GONE);
        txt_home.setVisibility(View.GONE);

        edtrecharamt = findViewById(R.id.edtrecharamt);
        relenter = findViewById(R.id.relenter);
    }

    private void serClickListener() {

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

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    }

    private void callStatementAPI() {
        settlementModel = new SettlementModel();
        settlementModel.setUser_id(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.user_id));
        settlementModel.setAmt(edtrecharamt.getText().toString());
        settlementModel.setTyp(typ);

        if (Utilities.getInstance().isNetworkAvailable(context)) {
            Utilities.getInstance().showProgressDialog(context);
            APIService.getInstance().getBaseUrl()
                    .stmt(settlementModel)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Utilities.getInstance().hideProgressdialog();
                            try {
                                Toast.makeText(context, "" + response.body().string(), Toast.LENGTH_SHORT).show();
                                clear();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }

    private void clear() {
        edtrecharamt.setText("");
        Utilities.getInstance().hideKeyBoard(SettleMentActivity.this);
    }
}