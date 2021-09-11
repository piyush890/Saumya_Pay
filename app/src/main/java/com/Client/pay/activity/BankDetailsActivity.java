package com.Client.pay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Client.pay.R;
import com.Client.pay.model.BankDetailModel;
import com.Client.pay.model.BankDetailRequest;
import com.Client.pay.retrofit.APICallBack;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

public class BankDetailsActivity extends AppCompatActivity {
    EditText accountHolderName;
    EditText accountNumber;
    EditText confirmAccountNumber;
    EditText ifscCode;
    TextView submit;
    private ImageView imgback;
    Context mcontext;
    private View toolbar;
    private TextView txt_home;
    private ImageView imgmenu;
    private ImageView imgsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        init();
        onClickListener();
    }

    private void onClickListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveBankDetail();
            }
        });

    }

    private void saveBankDetail() {
        BankDetailRequest obj = new BankDetailRequest();
        obj.setUser_id(Utilities.getInstance().getPreference(this,SharedPreferenceKeys.user_id));
        if(accountNumber.equals(confirmAccountNumber))
        {
            obj.setAccount(accountNumber.getText().toString());
        }
        obj.setName(accountHolderName.getText().toString());
//        obj.setAccount(accountHolderName.getText().toString());
        obj.setIfsc(ifscCode.getText().toString());


        bankDetailApi(obj);

    }

    private void bankDetailApi(BankDetailRequest obj) {
        if (Utilities.getInstance().isNetworkAvailable(this)) {
            Utilities.getInstance().showProgressDialog(this);
            APIService.getInstance().getBaseUrl()
                    .bankDetails(obj)
                    .enqueue(new APICallBack<BankDetailModel>() {
                        @Override
                        protected void success(BankDetailModel model) {
                            Utilities.getInstance().hideProgressdialog();
                            if (model != null) {
                                if (model.getStatus().equals("success")) {
                                    Toast.makeText(BankDetailsActivity.this, ""+model.getMsg(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mcontext, MyWalletActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (model.getStatus().equals("fail")) {
                                       // Toast.makeText(mcontext, "User not found", Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(mcontext, "" + model.getStatus(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }

                        @Override
                        protected void failure(String errorMsg) {
                            Utilities.getInstance().hideProgressdialog();
                            Toast.makeText(mcontext, "" + errorMsg, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        protected void onFailure(String failureReason) {
                            Utilities.getInstance().hideProgressdialog();
                            Toast.makeText(mcontext, "" + failureReason, Toast.LENGTH_SHORT).show();


                        }
                    });
        } else {
            Toast.makeText(mcontext, "Please check internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        accountHolderName = findViewById(R.id.et_name);
        accountNumber = findViewById(R.id.edt_accountnumber);
        confirmAccountNumber = findViewById(R.id.et_confirm_account);
        ifscCode = findViewById(R.id.et_ifsc);
        submit = findViewById(R.id.txt_submit);
        imgback = findViewById(R.id.imgback);
        imgback.setVisibility(View.VISIBLE);

        toolbar = findViewById(R.id.toolbar);

        imgmenu = findViewById(R.id.imgmenu);
        imgmenu.setVisibility(View.INVISIBLE);


        txt_home = findViewById(R.id.txt_home);
        txt_home.setText("Bank Details");
        txt_home.setVisibility(View.VISIBLE);

        imgsearch = findViewById(R.id.imgsearch);
        imgsearch.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}