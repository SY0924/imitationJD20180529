package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.AddCarBean;
import com.rookie.imitationjd.bean.InfoBean;
import com.rookie.imitationjd.bean.LoginBean;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public interface IInfoPresenter {
//商品详情
    void infos(String pid);
    void onSuccess(InfoBean infoBean);
    void onFailed(String error);
    void onDestory();
//添加购物车
    void addCarParams(String uid,String pid);
    void addCarSuccess(AddCarBean addCarBean);
}
