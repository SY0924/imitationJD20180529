package com.rookie.imitationjd.view.interfaces;

import com.rookie.imitationjd.bean.LoginBean;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface ILoginView {
    void getLoginSuccess(LoginBean loginBean);
    void getLoginFail(String error);
}
