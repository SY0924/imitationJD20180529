package com.rookie.imitationjd.view.interfaces;

import com.rookie.imitationjd.bean.GoodsBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public interface IGoodsView {
    void showGoodsData(List<GoodsBean.DataBean> newslist);
}
