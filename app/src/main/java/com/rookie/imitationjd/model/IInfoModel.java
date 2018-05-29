package com.rookie.imitationjd.model;

import com.rookie.imitationjd.presenter.IInfoPresenter;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public interface IInfoModel {
    //商品详情
    void infoParams(String pid,IInfoPresenter iInfoPresenter);
    //添加购物车
    void addCars(String uid,String pid,IInfoPresenter iInfoPresenter);

}
