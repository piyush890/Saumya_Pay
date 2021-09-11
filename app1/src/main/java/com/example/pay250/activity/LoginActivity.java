package com.Client.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.Client.pay.R;
import com.Client.pay.model.AppRequest;
import com.Client.pay.model.Login_bean;
import com.Client.pay.retrofit.APICallBack;
import com.Client.pay.retrofit.APIService;
import com.Client.pay.utils.SharedPreferenceKeys;
import com.Client.pay.utils.Utilities;
import com.Client.pay.viewmodel.LoginResponseViewModel;

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout relenter;
    private ConstraintLayout conmain;
    private Intent intent;
    private Context mcontext;
    private EditText etusername;
    private EditText etpassword;
    private String username;
    private String userpassword;
    private String errormsg;
    private LoginResponseViewModel loginResponseViewModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        setClickListener();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initUI() {
        mcontext = this;
        relenter = findViewById(R.id.relenter);
        etusername = findViewById(R.id.etusername);
//        etusername.setText("8299543628");
        etpassword = findViewById(R.id.etpassword);
//        etpassword.setText("Vikram8454@");
        conmain = findViewById(R.id.conmain);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setClickListener() {
        relenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initializeViewModel();
                if (validate()) {
                    AppRequest obj = new AppRequest();
                    obj.setUsername(username);
                    obj.setPassword(userpassword);
                    parseAPI(obj);
                } else {
                    Toast.makeText(mcontext, "" + errormsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void parseAPI(AppRequest obj) {

        if (Utilities.getInstance().isNetworkAvailable(mcontext)) {
            Utilities.getInstance().showProgressDialog(mcontext);
            APIService.getInstance().getBaseUrl()
                    .login(obj)
                    .enqueue(new APICallBack<Login_bean>() {
                        @Override
                        protected void success(Login_bean model) {
                            Utilities.getInstance().hideProgressdialog();
                            if (model != null) {
                                if (model.getMessage() == null) {
                                    setData(model);
                                    intent = new Intent(mcontext, DashBoardActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (model.getMessage().equals("Invalid Number")) {
                                        Toast.makeText(mcontext, "User not found", Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(mcontext, "" + model.getMessage(), Toast.LENGTH_SHORT).show();

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

    private void setData(Login_bean login_bean) {

        if (login_bean.getUser_id() != null)
            Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.user_id, login_bean.getUser_id());
        Log.d("User id ", "User id is"+Utilities.getInstance().getPreference(mcontext, SharedPreferenceKeys.user_id));
        Log.d("AgentID is", "agent is" + login_bean.getAgent_id());

        if (login_bean.getAgent_id() != null)
            Log.d("AgentID is", "agent is" + login_bean.getAgent_id());
        Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.agen_id, login_bean.getAgent_id());
        if (login_bean.getBalance() != null) {
            if (login_bean.getBalance().getAeps() != null) {
                Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.aeps_balnce, login_bean.getBalance().getAeps());
                Log.d("apes bal", login_bean.getBalance().getAeps());
            } else {
                Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.aeps_balnce, "0.00");

            }
            if (login_bean.getBalance().getMain() != null) {
                Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.main_balance, login_bean.getBalance().getMain());
            } else {
                Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.aeps_balnce, "0.00");

            }
            if (login_bean.getBalance().getStock() != null) {
                Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.stock_balance, login_bean.getBalance().getStock());
            } else {
                Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.aeps_balnce, "0.00");

            }
        }
        if (login_bean.getName() != null) {
            Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.user_name, login_bean.getName());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initializeViewModel() {
        if (validate()) {
            loginResponseViewModel = ViewModelProviders.of(this).get(LoginResponseViewModel.class);
            loginResponseViewModel.init(mcontext, username, userpassword);

            loginResponseViewModel.getMessage().observe(this, new Observer<Login_bean>() {
                @Override
                public void onChanged(Login_bean login_bean) {
                    Log.d("response", login_bean.getMessage());
                    if (login_bean != null) {
                        Log.d("message", login_bean.getMessage());
                        intent = new Intent(mcontext, DashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        } else {
            Utilities.getInstance().showSnackBar(conmain, errormsg);
        }
    }

    private boolean validate() {
        username = etusername.getText().toString();
        Utilities.getInstance().setPreference(mcontext, SharedPreferenceKeys.phone_number, username);
        userpassword = etpassword.getText().toString();

        if (Utilities.getInstance().isBlankString(username)) {
            errormsg = "Username cannot be blank";
            return false;
        } else if (Utilities.getInstance().isValidPass(userpassword)) {
            errormsg = "Password cannot be blank and it must contain atleast 5 character";
            return false;
        } else {
            return true;
        }

    }
}