package com.rookie.imitationjd.presenter;

import android.util.Log;

import com.rookie.imitationjd.bean.ShopCarBean;
import com.rookie.imitationjd.model.GWCModel;
import com.rookie.imitationjd.view.interfaces.IGWCView;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public class GWCPresenter implements IGWCPresenter {
    private static final String TAG ="GWCPresenter----" ;
    private IGWCView igwcView;
    private final GWCModel gwcModel;

    public GWCPresenter(IGWCView igwcView) {
        this.igwcView = igwcView;
        gwcModel = new GWCModel();
    }
//查询购物车
    @Override
    public void getCarUid(String uid) {
        gwcModel.getCar(uid,this);
    }

    @Override
    public void onSuccess(List<ShopCarBean.DataBean> data) {
        igwcView.showCarData(data);

    }

    @Override
    public void onFailed(String error) {

        Log.d(TAG, "onFailed: "+error);

    }

    @Override
    public void onDestory() {
        if(null!=igwcView){
            igwcView=null;
        }

    }
//删除购物车
    @Override
    public void getDelCar(String uid, String pid) {
        gwcModel.delCar(uid,pid,this);
    }

    @Override
    public void delSuccess(String msg) {
        igwcView.delCarData(msg);

    }
}
