package com.rookie.imitationjd.model;

import java.util.Map;

/**
 * Created by 暗夜 on 2018/4/28.
 */

public interface IFLModel {

    //分类左边导航栏数据
    void getFLLeftData(String url, Map<String, String> params, SetModelListener setModelListener);
    //分类右边导航栏数据
    void getFLRightData(String url, Map<String, String> params, SetModelListener setModelListener);
}
