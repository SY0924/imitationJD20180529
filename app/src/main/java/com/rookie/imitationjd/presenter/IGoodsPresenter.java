package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.GoodsBean;
import com.rookie.imitationjd.bean.ShopCarBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public interface IGoodsPresenter {

    void getParams(String pscid,String page,String sort);
    void onSuccess(List<GoodsBean.DataBean> data);
    void onFailed(String error);
    void onDestory();
}
