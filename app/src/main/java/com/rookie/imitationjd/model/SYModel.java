package com.rookie.imitationjd.model;


import android.util.Log;


import com.google.gson.Gson;
import com.rookie.imitationjd.bean.SYNavBean;
import com.rookie.imitationjd.utils.OkHttpUtils;
import com.rookie.imitationjd.utils.OnFinishListener;

import java.util.List;
import java.util.Map;

/**
 * Created by 暗夜 on 2018/4/17.
 */

public class SYModel implements ISYModel {
    private static final String TAG ="SYModel---" ;

    //首页轮播图数据
    @Override
    public void getShouYeData(String url,Map<String, String> params,final SetModelListener setModelListener) {
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
        okHttpUtils.doPost(url, params, new OnFinishListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson=new Gson();
                SYNavBean syNavBean = gson.fromJson(json,SYNavBean.class);
                List<SYNavBean.DataBean> data = syNavBean.getData();
                setModelListener.getSuccess(json);
            }

            @Override
            public void onFailed(String error) {
                setModelListener.getError(error);
            }
        });

    }
    //首页导航栏数据
    @Override
    public void getSYNavData(String url, Map<String, String> params, final SetModelListener setModelListener) {

            OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
            okHttpUtils.doPost(url, params, new OnFinishListener() {
                @Override
                public void onSuccess(String json) {
                    setModelListener.getSuccess(json);
                }

                @Override
                public void onFailed(String error) {
                    setModelListener.getError(error);
                }
            });

        }
    }



