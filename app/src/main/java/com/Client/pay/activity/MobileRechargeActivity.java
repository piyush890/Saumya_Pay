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

import com.Client.pay.R;
import com.Client.pay.adapter.CustomeSpinnerAdaptor;
import com.Client.pay.fragment.BottomSheetDialog;
import com.Client.pay.handleclick.FragmentClickListenerBean;
import com.Client.pay.model.AepsSettlementModel;
import com.Client.pay.model.OperaterBean;
import com.Client.pay.model.RecharageModel;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileRechargeActivity extends AppCompatActivity implements FragmentClickListenerBean {

    private View toolbar;
    private Context context;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;

    private RelativeLayout relenter;
    private EditText edt_mobilenumber;
    private EditText edtrecharamt;
    private TextView txt_optype;


    private String mobilenumber;
    private String rechargeamt;
    private String message;
    private String opertor_type;
    private String operaterCode="";


    private CustomeSpinnerAdaptor customeSpinnerAdaptor;
    private ArrayList<OperaterBean> operatorlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_recharge);
        overridePendingTransition(R.anim.enter, R.anim.steady);

        initUI();
        onClickListener();
    }


    private void initUI() {
        context = this;
        toolbar = findViewById(R.id.toolbar);
        imgmenu = findViewById(R.id.imgmenu);
        imgback = findViewById(R.id.imgback);
        txt_home = findViewById(R.id.txt_home);
        imgsearch = findViewById(R.id.imgsearch);

        relenter = findViewById(R.id.relenter);
        edt_mobilenumber = findViewById(R.id.edt_mobilenumber);
        edtrecharamt = findViewById(R.id.edtrecharamt);

        txt_optype = findViewById(R.id.txt_optype);

        mobilenumber = edt_mobilenumber.getText().toString();
        rechargeamt = edtrecharamt.getText().toString();
        opertor_type = txt_optype.getText().toString();


        imgmenu.setVisibility(View.GONE);
        imgback.setVisibility(View.VISIBLE);
        txt_home.setVisibility(View.GONE);
        imgsearch.setVisibility(View.GONE);


//        OperaterBean operaterBean=new OperaterBean();
//        operaterBean.setOperatorType("BSNL");
//        customeSpinnerAdaptor=new CustomeSpinnerAdaptor();
    }

    private void onClickListener() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        relenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "" + edt_mobilenumber.getText().toString() + rechargeamt + txt_optype.getText().toString(), Toast.LENGTH_SHORT).show();

                if (isValidate()) {
                    RecharageModel obj = new RecharageModel();
                    obj.setUser_id(Utilities.getInstance().getPreference(context, SharedPreferenceKeys.user_id));
                    obj.setNumber(edt_mobilenumber.getText().toString());
                    obj.setOprator(operaterCode);
                    obj.setAmt(edtrecharamt.getText().toString());

                    recharge(obj);

                } else {
                    Toast.makeText(MobileRechargeActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_optype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MobileRechargeActivity.class.getSimpleName());
                bottomSheetDialog.show(getSupportFragmentManager(), "Modal Bottom sheet");
            }
        });
    }

    private void recharge(RecharageModel obj) {
        if (Utilities.getInstance().isNetworkAvailable(context)) {
            Utilities.getInstance().showProgressDialog(context);
            APIService.getInstance().getBaseUrl()
                    .mobileRrechrage(obj)
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
                            Toast.makeText(context, ""+call.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });


        } else {
            Toast.makeText(context, "Please check the network connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidate() {
        if (Utilities.getInstance().isBlankString(edt_mobilenumber.getText().toString())) {
            message = "Please enter mobile number";
            return false;
        } else if (Utilities.getInstance().isBlankString(edtrecharamt.getText().toString())) {
            message = "Please enter recharge amount";
            return false;
        } else if (txt_optype.getText().toString().equalsIgnoreCase(getString(R.string.select_operater))) {
            message = "Please select operater type";
            return false;
        } else {
            return true;
        }
    }

    void clear() {
        edt_mobilenumber.setText("");
        edtrecharamt.setText("");
        txt_optype.setText("Select Operater");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }



    @Override
    public void itemOnClick(AepsSettlementModel model,OperaterBean operater, int pos) {
        txt_optype.setText(operater.getOperatorType());
        operaterCode=operater.getCode();
    }



}