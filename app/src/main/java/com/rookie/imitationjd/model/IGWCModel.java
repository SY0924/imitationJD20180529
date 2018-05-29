package com.rookie.imitationjd.model;

import com.rookie.imitationjd.presenter.IGWCPresenter;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface IGWCModel {
    //查询购物车
    void getCar(String uid,IGWCPresenter igwcPresenter);
    //删除购物车
    void delCar(String uid,String pid,IGWCPresenter igwcPresenter);
}
