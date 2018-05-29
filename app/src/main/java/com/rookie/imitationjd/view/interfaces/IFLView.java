package com.rookie.imitationjd.view.interfaces;



import com.rookie.imitationjd.bean.ClassBean;
import com.rookie.imitationjd.bean.SYNavBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/4/28.
 */

public interface IFLView {

    //分类左边导航栏展示
    void showFLLeftData(List<SYNavBean.DataBean> data);

    //分类右边导航栏展示
    void showFLRightData(List<ClassBean.DataBean> data);

    //得到左边导航栏的cid
    String getCid();

}
