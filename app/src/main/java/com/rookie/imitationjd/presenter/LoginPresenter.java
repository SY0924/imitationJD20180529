package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.LoginBean;
import com.rookie.imitationjd.model.LoginModel;
import com.rookie.imitationjd.view.interfaces.ILoginView;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public class LoginPresenter implements ILoginPresenter {
    private ILoginView iLoginView;
    private final LoginModel loginModel;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        loginModel = new LoginModel();

    }

    @Override
    public void loginParams(String mobile, String password) {
        loginModel.getLogin(mobile,password,this);
    }

    @Override
    public void onSuccess(LoginBean loginBean) {

        iLoginView.getLoginSuccess(loginBean);

    }

    @Override
    public void onFailed(String error) {

        iLoginView.getLoginFail(error);

    }

    @Override
    public void onDestory() {
        if(null!=iLoginView){
            iLoginView=null;
        }

    }
}
