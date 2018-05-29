package com.rookie.imitationjd.utils;


import com.rookie.imitationjd.httpconfig.Api;
import com.rookie.imitationjd.httpconfig.HttpApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 暗夜 on 2018/5/17.
 */

public class RetrofitUtils {
    private static RetrofitUtils retrofitUtils=null;
    private final Retrofit retrofit;

    public RetrofitUtils() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().addInterceptor(new CommonInterceptor());
        okHttpClient.readTimeout(3000, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(3000,TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(HttpApi.BaseUrl)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
    public static RetrofitUtils getInstance(){
        if(null==retrofitUtils){
            synchronized (RetrofitUtils.class){
                if(null==retrofitUtils){
                    retrofitUtils=new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }

}
