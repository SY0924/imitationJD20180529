package com.rookie.imitationjd.utils;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public interface OnFinishListener {
    void onSuccess(String json);
    void onFailed(String error);
}
