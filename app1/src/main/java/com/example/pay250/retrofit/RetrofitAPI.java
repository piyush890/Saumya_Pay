package com.Client.pay.retrofit;

import com.Client.pay.model.AppRequest;
import com.Client.pay.model.Login_bean;
import com.Client.pay.model.RecharageModel;
import com.Client.pay.model.ReportBean;
import com.Client.pay.model.ReportModel;
import com.Client.pay.model.SettlementModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("login")
    Call<Login_bean> login(@Body AppRequest request);

    @POST("recharge")
    Call<ResponseBody> mobileRrechrage(@Body RecharageModel request);

    @POST("stmt")
    Call<ResponseBody> stmt(@Body SettlementModel request);

    @POST("report")
    Call<ArrayList<ReportBean>> report(@Body ReportModel request);

}
