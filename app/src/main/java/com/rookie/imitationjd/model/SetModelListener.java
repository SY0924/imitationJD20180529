package com.rookie.imitationjd.model;

/**
 * Created by 暗夜 on 2018/4/17.
 */

public interface SetModelListener {
    //获取成功
    void getSuccess(String json);
    //获取失败
    void getError(String error);
}
