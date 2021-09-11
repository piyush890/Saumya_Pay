package com.Client.pay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Client.pay.R;
import com.Client.pay.adapter.AcDisplayAdapter;
import com.Client.pay.model.AccountDisplayRequest;
import com.Client.pay.model.DisplayAcModel;
import com.Client.pay.model.SattlementModel;
import com.Client.pay.model.SattlementReqest;
import com.Client.pay.model.SettlementModel;
import com.Client.pay.retrofit.APICallBack;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SatelmentActivity extends AppCompatActivity implements AcDisplayAdapter.PayInterface {

    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;
    TextView addAccountNumberTextview;
    EditText accountHolderName;
    EditText accountNumber;
    EditText confirmAccountNumber;
    EditText ifscCode;
    TextView addAccount;
    Context mcontext;
    List<DisplayAcModel> dataArrayList;
    AcDisplayAdapter acDisplayAdapter;
    RecyclerView accountRecyclerview;
    RelativeLayout bankDetailRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satelment);
        init();
        clickListener();
        accountRecyclerview = findViewById(R.id.account_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        accountRecyclerview.setLayoutManager(linearLayoutManager);
        showAllAccount();
    }

    private void showAllAccount() {
        AccountDisplayRequest obj = new AccountDisplayRequest();
        obj.setUser_id(Utilities.getInstance().getPreference(this, SharedPreferenceKeys.user_id));
        showAccountApi(obj);
    }

    private void showAccountApi(AccountDisplayRequest obj) {
        if (Utilities.getInstance().isNetworkAvailable(this)) {
            Utilities.getInstance().showProgressDialog(this);
            APIService.getInstance().getBaseUrl().allStmtAccount(obj).enqueue(new APICallBack<ArrayList<DisplayAcModel>>() {

                @Override
                protected void success(ArrayList<DisplayAcModel> displayAcModel) {
                    Utilities.getInstance().hideProgressdialog();
                    acDisplayAdapter = new AcDisplayAdapter(context, displayAcModel,SatelmentActivity.this);
                    accountRecyclerview.setAdapter(acDisplayAdapter);

                }

                @Override
                protected void failure(String errorMsg) {
                    Utilities.getInstance().hideProgressdialog();
                }

                @Override
                protected void onFailure(String failureReason) {
                    Utilities.getInstance().hideProgressdialog();
                }
            });
        }
    }


    private void clickListener() {
        addAccountNumberTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankDetailRelativeLayout.setVisibility(View.VISIBLE);
            }
        });

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });


        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        }

    private void addAccount()  {
        SattlementReqest obj = new SattlementReqest();
            obj.setUser_id(Utilities.getInstance().getPreference(this, SharedPreferenceKeys.user_id));
            if((accountNumber.getText().toString()).equals(confirmAccountNumber.getText().toString()))
            {
                obj.setAnumber(accountNumber.getText().toString());
            }
            obj.setHname(accountHolderName.getText().toString());
            obj.setIfsc(ifscCode.getText().toString());
            bankDetailApi(obj);

    }

    private void bankDetailApi(SattlementReqest obj) {
        if (Utilities.getInstance().isNetworkAvailable(this)) {
            Utilities.getInstance().showProgressDialog(this);
            APIService.getInstance().getBaseUrl().stmtAddAccount(obj).enqueue(new APICallBack<SattlementModel>() {

                @Override
                protected void success(SattlementModel model) {
                    Utilities.getInstance().hideProgressdialog();
                    bankDetailRelativeLayout.setVisibility(View.INVISIBLE);
                    Toast.makeText(mcontext, "" +model.getMsg(), Toast.LENGTH_SHORT).show();
                }

                @Override
                protected void failure(String errorMsg) {
                    Utilities.getInstance().hideProgressdialog();
                }

                @Override
                protected void onFailure(String failureReason) {
                    Utilities.getInstance().hideProgressdialog();
                }
            });
        }

    }

    private void init() {
        addAccountNumberTextview = findViewById(R.id.add_account_number_textview);
        bankDetailRelativeLayout = findViewById(R.id.bank_detail_layout);
        accountHolderName = findViewById(R.id.et_name);
        accountNumber = findViewById(R.id.edt_accountnumber);
        confirmAccountNumber = findViewById(R.id.et_confirm_account);
        ifscCode = findViewById(R.id.et_ifsc);
        addAccount = findViewById(R.id.txt_submit);
        context = this;
        toolbar = findViewById(R.id.toolbar);

        imgmenu = findViewById(R.id.imgmenu);
        imgmenu.setVisibility(View.INVISIBLE);

        imgback = findViewById(R.id.imgback);
        imgback.setVisibility(View.VISIBLE);

        txt_home = findViewById(R.id.txt_home);
        txt_home.setText("Settlement");

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);

    }



    @Override
    public void onItemPay(String amount) {
             SettlementModel settlementModel = new SettlementModel();
            settlementModel.setUser_id(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.user_id));
            settlementModel.setAmt(amount);
//            settlementModel.setTyp(typ);

            if (Utilities.getInstance().isNetworkAvailable(this)) {
                Utilities.getInstance().showProgressDialog(this);
                APIService.getInstance().getBaseUrl()
                        .pay(settlementModel)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Utilities.getInstance().hideProgressdialog();
                                try {
                                    Toast.makeText(context, "" + response.body().string(), Toast.LENGTH_SHORT).show();

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
    public void onItemDelete(String delete) {
        AccountDisplayRequest obj = new AccountDisplayRequest();
        obj.setUser_id(Utilities.getInstance().getPreference(this, SharedPreferenceKeys.user_id));
        obj.setAccount(delete);

        if (Utilities.getInstance().isNetworkAvailable(this)) {
            Utilities.getInstance().showProgressDialog(this);
            APIService.getInstance().getBaseUrl().delAccount(obj).enqueue(new APICallBack<SattlementModel>() {

                @Override
                protected void success(SattlementModel model) {
                    Utilities.getInstance().hideProgressdialog();
                    showAllAccount();
                    Toast.makeText(context, "" +model.getMsg(), Toast.LENGTH_SHORT).show();
                }

                @Override
                protected void failure(String errorMsg) {
                    Utilities.getInstance().hideProgressdialog();
                }

                @Override
                protected void onFailure(String failureReason) {
                    Utilities.getInstance().hideProgressdialog();
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}