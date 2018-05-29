package com.rookie.imitationjd.presenter;


import android.util.Log;


import com.google.gson.Gson;
import com.rookie.imitationjd.bean.SYBannerBean;
import com.rookie.imitationjd.bean.SYNavBean;
import com.rookie.imitationjd.httpconfig.HttpApi;
import com.rookie.imitationjd.model.ISYModel;
import com.rookie.imitationjd.model.SetModelListener;
import com.rookie.imitationjd.view.interfaces.ISYView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 暗夜 on 2018/4/17.
 */

public class SYPresenter implements ISYPresenter {



    private static final String TAG = "SYPresenter----";




    //首页导航栏数据
    @Override
    public void showNavToView(ISYModel isyModel, final ISYView isyView) {
        Map<String, String> params = new HashMap<>();
        isyModel.getSYNavData(HttpApi.NavUrl, params, new SetModelListener() {
            @Override
            public void getSuccess(String json) {
                Log.d(TAG, "SYNavPresenter: "+json);
                Gson gson=new Gson();
                SYNavBean syNavBean = gson.fromJson(json,SYNavBean.class);
                List<SYNavBean.DataBean> data = syNavBean.getData();

                isyView.showSYNavData(data);
            }

            @Override
            public void getError(String error) {
                Log.d(TAG, "getShouYeError: "+error);
            }
        });

    }

    //首页轮播图数据
    @Override
    public void showBannerToView(ISYModel isyModel, final ISYView isyView) {
        Map<String, String> params = new HashMap<>();
        isyModel.getShouYeData(HttpApi.BannerUrl, params, new SetModelListener() {
            @Override
            public void getSuccess(String json) {
                Gson gson=new Gson();
                //首页广告（轮播图+京东秒杀+最底部的为你推荐）
                SYBannerBean syBannerBean = gson.fromJson(json, SYBannerBean.class);
                // 轮播图
                // 京东秒杀
                //首页推荐
                //将数据传入ISYView中
              isyView.showSYBannerData(syBannerBean);


            }

            @Override
            public void getError(String error) {
                Log.d(TAG, "失败: "+error);
            }
        });
    }
}
