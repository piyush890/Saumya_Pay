package com.Client.pay.retrofit;

import com.Client.pay.model.AcCheck;
import com.Client.pay.model.AccountDisplayRequest;
import com.Client.pay.model.AccountRequest;
import com.Client.pay.model.AppRequest;
import com.Client.pay.model.Balance;
import com.Client.pay.model.BalanceModel;
import com.Client.pay.model.BankDetailModel;
import com.Client.pay.model.BankDetailRequest;
import com.Client.pay.model.DisplayAcModel;
import com.Client.pay.model.Login_bean;
import com.Client.pay.model.RecharageModel;
import com.Client.pay.model.ReportBean;
import com.Client.pay.model.ReportModel;
import com.Client.pay.model.SattlementModel;
import com.Client.pay.model.SattlementReqest;
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

    @POST("ac_ckeck")
    Call<AcCheck> accountCheck(@Body AccountRequest acRequest);

    @POST("ac_aeps")
    Call<BankDetailModel> bankDetails(@Body BankDetailRequest bankDetailRequest);

    @POST("balance")
    Call<Balance> balance(@Body BalanceModel balanceModel);

    @POST("money_stmt_adac")
    Call<SattlementModel> stmtAddAccount(@Body SattlementReqest sattlementReqest);

    @POST("money_stmt_account")
    Call<ArrayList<DisplayAcModel>> allStmtAccount(@Body AccountDisplayRequest accountDisplayRequest);

    @POST("money_dlt")
    Call<SattlementModel> delAccount(@Body AccountDisplayRequest accountDisplayRequest);

    @POST("money_pay")
    Call<ResponseBody> pay(@Body SettlementModel request);

}
