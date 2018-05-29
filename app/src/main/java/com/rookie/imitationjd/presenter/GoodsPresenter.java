package com.rookie.imitationjd.presenter;

import android.util.Log;

import com.rookie.imitationjd.bean.GoodsBean;
import com.rookie.imitationjd.model.GoodsModel;
import com.rookie.imitationjd.view.interfaces.IGoodsView;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public class GoodsPresenter implements IGoodsPresenter {
    private static final String TAG = "GoodsPresenter----";
    private IGoodsView iGoodsView;
    private final GoodsModel goodsModel;

    public GoodsPresenter(IGoodsView iGoodsView) {
        this.iGoodsView = iGoodsView;
        goodsModel = new GoodsModel();
    }


    @Override
    public void getParams(String pscid, String page, String sort) {
        goodsModel.goodsParams(pscid,page,sort,this);
    }

    @Override
    public void onSuccess(List<GoodsBean.DataBean> data) {
        iGoodsView.showGoodsData(data);

    }

    @Override
    public void onFailed(String error) {

        Log.d(TAG, "onFailed: "+error.toString());

    }

    @Override
    public void onDestory() {
        if(null!=iGoodsView){
            iGoodsView=null;
        }

    }
}
