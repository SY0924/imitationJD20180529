package com.rookie.imitationjd.view.interfaces;

import com.rookie.imitationjd.bean.ShopCarBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface IGWCView {
    //查询购物车
    void showCarData(List<ShopCarBean.DataBean> data);

    //删除购物车
    void delCarData(String msg);

}
