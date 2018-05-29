package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.LoginBean;
import com.rookie.imitationjd.bean.UserInfoBean;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface IUserPresenter {
    void userParams(String uid);
    void onSuccess(UserInfoBean userInfoBean);
    void onFailed(String error);
    void onDestory();
}
