package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.ShopCarBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface IGWCPresenter {
    void getCarUid(String uid);
    void onSuccess(List<ShopCarBean.DataBean> data);
    void onFailed(String error);
    void onDestory();

    void getDelCar(String uid,String pid);
    void delSuccess(String msg);
}
