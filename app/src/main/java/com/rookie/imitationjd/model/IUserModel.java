package com.rookie.imitationjd.model;

import com.rookie.imitationjd.presenter.IUserPresenter;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface IUserModel {
    void getUserInfo(String uid,IUserPresenter iUserPresenter);
}
