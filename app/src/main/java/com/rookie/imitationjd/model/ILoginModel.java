package com.rookie.imitationjd.model;

import com.rookie.imitationjd.presenter.ILoginPresenter;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface ILoginModel {
    void getLogin(String mobile,String password,ILoginPresenter iLoginPresenter);
}
