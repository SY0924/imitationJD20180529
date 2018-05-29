package com.rookie.imitationjd.model;

import java.util.Map;

/**
 * Created by 暗夜 on 2018/4/17.
 */

public interface ISYModel {
    //首页轮播图数据
    void getShouYeData(String url, Map<String, String> params, SetModelListener setModelListener);
    //首页导航栏数据
    void getSYNavData(String url, Map<String, String> params, SetModelListener setModelListener);


}
