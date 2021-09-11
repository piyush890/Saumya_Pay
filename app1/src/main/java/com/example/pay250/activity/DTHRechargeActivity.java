package com.Client.pay.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Client.pay.R;
import com.Client.pay.fragment.BottomSheetDialog;
import com.Client.pay.handleclick.FragmentClickListenerBean;
import com.Client.pay.model.AepsSettlementModel;
import com.Client.pay.model.OperaterBean;
import com.Client.pay.model.RecharageModel;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DTHRechargeActivity extends AppCompatActivity implements FragmentClickListenerBean {

    private View toolbar;
    private ImageView imgmenu;
    private ImageView imgback;
    private TextView txt_home;
    private ImageView imgsearch;

    private TextView txt_optype;
    private EditText edt_mobilenumber;
    private EditText edtrecharamt;
    private LinearLayout llmain;
    private RelativeLayout relenter;
    private String message;
    private Context mcontext;
    private String operaterCode="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthrecharge);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        initUI();
        setClickListener();
    }

    private void initUI() {
        mcontext = this;
        toolbar = findViewById(R.id.toolbar);
        imgmenu = findViewById(R.id.imgmenu);
        imgback = findViewById(R.id.imgback);
        txt_home = findViewById(R.id.txt_home);
        imgsearch = findViewById(R.id.imgsearch);


        txt_optype = findViewById(R.id.txt_optype);
        edt_mobilenumber = findViewById(R.id.edt_mobilenumber);
        edtrecharamt = findViewById(R.id.edtrecharamt);
        llmain = findViewById(R.id.llmain);
        relenter = findViewById(R.id.relenter);

        imgmenu.setVisibility(View.GONE);
        imgback.setVisibility(View.VISIBLE);
        txt_home.setVisibility(View.GONE);
        imgsearch.setVisibility(View.GONE);
    }

    private void setClickListener() {
        relenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    RecharageModel obj = new RecharageModel();
//                    Toast.makeText(mcontext, ""+Utilities.getInstance().getPreference(mcontext,SharedPreferenceKeys), Toast.LENGTH_SHORT).show();
//                    Log.d("User id ", Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.user_id));
                    obj.setUser_id(Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.user_id));
                    obj.setNumber(edt_mobilenumber.getText().toString());
                    obj.setOprator(operaterCode);
                    obj.setAmt(edtrecharamt.getText().toString());
                    rechrage(obj);
                } else {
                    Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_optype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DTHRechargeActivity.class.getSimpleName());
                bottomSheetDialog.show(getSupportFragmentManager(), "Modal Bottom sheet");
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    private void rechrage(RecharageModel obj) {
        if (Utilities.getInstance().isNetworkAvailable(mcontext)) {
            Utilities.getInstance().showProgressDialog(mcontext);
            APIService.getInstance().getBaseUrl()
                    .mobileRrechrage(obj)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Utilities.getInstance().hideProgressdialog();
                            if (response.body() != null) {
                                try {
                                    Toast.makeText(mcontext, "" + response.body().string(), Toast.LENGTH_SHORT).show();
                                    clear();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Utilities.getInstance().hideProgressdialog();

                        }
                    });
        } else {
            Toast.makeText(mcontext, "Please check the network connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        edt_mobilenumber.setText("");
        edtrecharamt.setText("");
        txt_optype.setText("Select Operater");
    }

    private boolean isValidate() {
        if (Utilities.getInstance().isBlankString(edt_mobilenumber.getText().toString())) {
            message = "Please enter account number";
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

//    @Override
//    public void onClickFrag(String where) {
//        txt_optype.setText(where);
//    }

    @Override
    public void itemOnClick(AepsSettlementModel model,OperaterBean operater, int pos) {
        txt_optype.setText(operater.getOperatorType());
        operaterCode=operater.getCode();
//        Toast.makeText(mcontext, ""+operater.getCode(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}