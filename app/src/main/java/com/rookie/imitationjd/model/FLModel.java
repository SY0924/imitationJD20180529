package com.rookie.imitationjd.model;

import android.util.Log;


import com.rookie.imitationjd.utils.OkHttpUtils;
import com.rookie.imitationjd.utils.OnFinishListener;

import java.util.Map;

/**
 * Created by 暗夜 on 2018/4/28.
 */

public class FLModel implements IFLModel {

    private static final String TAG = "FLModel----";

    //分类左边导航栏数据
    @Override
    public void getFLLeftData(String url, Map<String, String> params, final SetModelListener setModelListener) {
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
        okHttpUtils.doGet(url, params, new OnFinishListener() {
            @Override
            public void onSuccess(String json) {
                Log.d(TAG, "onSuccess: "+json);

                setModelListener.getSuccess(json);
            }

            @Override
            public void onFailed(String error) {
                setModelListener.getError(error);
            }
        });

    }
    //分类右边导航栏数据
    @Override
    public void getFLRightData(String url, Map<String, String> params, final SetModelListener setModelListener) {
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
        okHttpUtils.doGet(url, params, new OnFinishListener() {
            @Override
            public void onSuccess(String json) {
                Log.d(TAG, "onSuccess: "+json);

                setModelListener.getSuccess(json);
            }

            @Override
            public void onFailed(String error) {
                setModelListener.getError(error);
            }
        });
    }
}

