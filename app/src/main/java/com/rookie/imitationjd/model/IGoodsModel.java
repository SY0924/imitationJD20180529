package com.rookie.imitationjd.model;

import com.rookie.imitationjd.presenter.IGoodsPresenter;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public interface IGoodsModel {
    void goodsParams(String pscid,String page,String sort,IGoodsPresenter iGoodsPresenter);
}
