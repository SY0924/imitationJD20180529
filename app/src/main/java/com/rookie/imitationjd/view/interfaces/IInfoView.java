package com.rookie.imitationjd.view.interfaces;

import com.rookie.imitationjd.bean.AddCarBean;
import com.rookie.imitationjd.bean.InfoBean;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public interface IInfoView {
    //商品详情
    void showInfoData(InfoBean infoBean);

    //添加购物车
    void getAddCarData(AddCarBean addCarBean);
}
