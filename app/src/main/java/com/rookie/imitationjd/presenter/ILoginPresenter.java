package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.LoginBean;
import com.rookie.imitationjd.bean.ShopCarBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface ILoginPresenter {
    void loginParams(String mobile,String password);
    void onSuccess(LoginBean loginBean);
    void onFailed(String error);
    void onDestory();
}
