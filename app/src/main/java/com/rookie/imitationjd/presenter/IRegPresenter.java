package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.LoginBean;
import com.rookie.imitationjd.bean.RegisterBean;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface IRegPresenter {
    void regParams(String mobile,String password);
    void onSuccess(RegisterBean registerBean);
    void onFailed(String error);
    void onDestory();
}
