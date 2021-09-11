package com.Client.pay.retrofit;

import androidx.annotation.NonNull;

import com.Client.pay.utils.Utilities;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Custom API Callback class to handle response and failure from APIs
 *
 * @param <M> Custom Model class which are created for the handle response of the API
 */
public abstract class APICallBack<M> implements Callback<M> {
    private final String TAG = com.Client.pay.retrofit.APICallBack.class.getSimpleName();

    /**
     * @param call     call
     * @param response response of the API
     */
    @Override
    public void onResponse(@NonNull Call<M> call, @NonNull Response<M> response) {
        M model = response.body();

        Utilities.getInstance().showLog("URL: " + response.raw().request().url());

        if (model != null) {
            //  Utility.getInstance().showLog("Response: " + new Gson().toJson(response.body()));
            success(model);
        }
//        else
//            failure(ApplicationDetails.getInstance().getContext().getResources().getString(R.string.general_err_msg));

    }

    /**
     * @param call      call
     * @param throwable throw an errors
     */
    @Override
    public void onFailure(@NonNull Call<M> call, @NonNull Throwable throwable) {
        String errorType;
        String errorDesc;
        if (throwable instanceof SocketTimeoutException) {
            errorType = "Timeout";
            errorDesc = String.valueOf(throwable.getCause());
        } else if (throwable instanceof IOException) {
            errorType = "IOException";
            errorDesc = String.valueOf(throwable.getCause());
        } else if (throwable instanceof IllegalStateException) {
            errorType = "ConversionError";
            errorDesc = String.valueOf(throwable.getCause());
        } else {
            errorType = "Other Error";
            errorDesc = String.valueOf(throwable.getLocalizedMessage());
        }

//        onFailure(ApplicationDetails.getInstance().getContext().getResources().getString(R.string.general_err_msg));
        onFailure(throwable.getMessage());

    }

    /**
     * @param model if not null then sends model class to the activity
     */
    protected abstract void success(M model);

    /**
     * @param errorMsg if model class is null then send error message
     */
    protected abstract void failure(String errorMsg);

    /**
     * @param failureReason issues from thowable errors above
     */
    protected abstract void onFailure(String failureReason);
}
