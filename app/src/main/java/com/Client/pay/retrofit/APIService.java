package com.Client.pay.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    //    public final String webHost = "https://saumyapaypoint.com/webroot/api/app/";
    public final String webHost = "http://utipanbanking.online//webroot/api/app/";

    
    private final long HTTP_TIMEOUT = 50;

    private static APIService apiService = null;

    public static APIService getInstance() {
        return (apiService == null) ? apiService = new APIService() : apiService;
    }

    private Retrofit retrofit = null;

    public RetrofitAPI getBaseUrl() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(webHost)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return retrofit.create(RetrofitAPI.class);
    }

    private OkHttpClient getClient() {
        final OkHttpClient.Builder okHttpClientBuilder = new
                OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }
}
