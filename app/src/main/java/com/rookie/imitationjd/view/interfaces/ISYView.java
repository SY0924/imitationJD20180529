package com.rookie.imitationjd.view.interfaces;


import com.rookie.imitationjd.bean.SYBannerBean;
import com.rookie.imitationjd.bean.SYNavBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/4/17.
 */

public interface ISYView {

    //首页导航栏展示
    void showSYNavData(List<SYNavBean.DataBean> data);
    //首页轮播图展示
    //首页秒杀展示
    //首页推荐展示
    void showSYBannerData(SYBannerBean syBannerBean);

}
