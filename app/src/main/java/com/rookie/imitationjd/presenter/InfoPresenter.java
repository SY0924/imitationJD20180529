package com.rookie.imitationjd.presenter;

import android.util.Log;

import com.rookie.imitationjd.bean.AddCarBean;
import com.rookie.imitationjd.bean.InfoBean;
import com.rookie.imitationjd.model.InfoModel;
import com.rookie.imitationjd.view.interfaces.IInfoView;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public class InfoPresenter implements IInfoPresenter {
    private static final String TAG ="InfoPresenter---" ;
    private IInfoView iInfoView;
    private final InfoModel infoModel;

    public InfoPresenter(IInfoView iInfoView) {
        this.iInfoView = iInfoView;
        infoModel = new InfoModel();
    }
//商品详情
    @Override
    public void infos(String pid) {
        infoModel.infoParams(pid,this);
    }

    @Override
    public void onSuccess(InfoBean infoBean) {
        iInfoView.showInfoData(infoBean);

    }

    @Override
    public void onFailed(String error) {
        Log.d(TAG, "onFailed: "+error.toString());

    }

    @Override
    public void onDestory() {
        if(null!=iInfoView){
            iInfoView=null;
        }

    }
//添加购物车
    @Override
    public void addCarParams(String uid,String pid) {

        infoModel.addCars(uid,pid,this);

    }

    @Override
    public void addCarSuccess(AddCarBean addCarBean) {
        iInfoView.getAddCarData(addCarBean);

    }
}
