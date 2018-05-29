package com.rookie.imitationjd.presenter;


import com.rookie.imitationjd.model.ISYModel;
import com.rookie.imitationjd.view.interfaces.ISYView;

/**
 * Created by 暗夜 on 2018/4/17.
 */

public interface ISYPresenter {
    //首页轮播图数据
    void showBannerToView(ISYModel isyModel, ISYView isyView);
    //首页导航栏数据
    void showNavToView(ISYModel isyModel, ISYView isyView);

}
