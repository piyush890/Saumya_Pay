package com.Client.pay.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.Client.pay.model.AppRequest;
import com.Client.pay.model.Login_bean;

public class LoginResponseRepository {
    private static LoginResponseRepository instance;
    private String message;
    private String msgnew;
    MutableLiveData<Login_bean> data = new MutableLiveData<>();
    private Context mcontext;


    public static LoginResponseRepository getInstance() {
//        mContext = mcontext;
        if (instance == null) {
            instance = new LoginResponseRepository();
        }
        return instance;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MutableLiveData<Login_bean> getResponse(Context mcontext, String username, String userpassword) {

        AppRequest obj = new AppRequest();
//        obj.setUsername(username);
//        obj.setPassword(userpassword);
//        if (Utilities.getInstance().isNetworkAvailable(ApplicationDetails.getInstance().getContext())) {
//            Utilities.getInstance().showProgressDialog(mcontext);
//            APIService.getInstance().getBaseUrl()
//                    .login(obj)
//                    .enqueue(new APICallBack<Login_bean>() {
//                        @Override
//                        protected void success(Login_bean model) {
//                            if (model != null) {
//                                Utilities.getInstance().hideProgressdialog();
//                                if (model.getMessage() != null) {
//                                    msgnew = model.getMessage().toString();
//                                    Log.d("message", username + " " + userpassword);
//                                    data.postValue(model);
//
//                                }
//                            } else {
//                                message = "OOPS Something went wrong";
//                                Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        protected void failure(String errorMsg) {
//                            Utilities.getInstance().hideProgressdialog();
//                            message = errorMsg;
//                            Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        protected void onFailure(String failureReason) {
//                            Utilities.getInstance().hideProgressdialog();
//                            message = failureReason;
//                            Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//        } else {
//            message = "Please check network connection";
//            Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
//
//        }
//
//        Log.d("message", data.toString());

        return data;

    }
}
