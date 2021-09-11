package com.Client.pay.viewmodel;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Client.pay.model.Login_bean;
import com.Client.pay.repository.LoginResponseRepository;


public class LoginResponseViewModel extends ViewModel {

    private MutableLiveData<Login_bean> message;
    private LoginResponseRepository loginResponseRepository;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(Context mcontext, String username, String userpassword) {
        if (message != null) {
            return;
        }
        loginResponseRepository = LoginResponseRepository.getInstance();
        message = loginResponseRepository.getResponse(mcontext,username,userpassword);
    }

    public LiveData<Login_bean> getMessage(){
        return message;
    }

}
