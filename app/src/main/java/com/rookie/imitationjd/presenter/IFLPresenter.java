package com.rookie.imitationjd.presenter;


import com.rookie.imitationjd.model.IFLModel;
import com.rookie.imitationjd.view.interfaces.IFLView;

/**
 * Created by 暗夜 on 2018/4/28.
 */

public interface IFLPresenter {

    //分类左边导航栏数据
    void showLeftToView(IFLModel iflModel, IFLView iflView);
    //分类右边导航栏数据
    void showRightToView(IFLModel iflModel, IFLView iflView);
}
