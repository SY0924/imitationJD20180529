package com.rookie.imitationjd.presenter;

import android.util.Log;

import com.rookie.imitationjd.bean.UserInfoBean;
import com.rookie.imitationjd.model.UserModel;
import com.rookie.imitationjd.view.interfaces.IUserView;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public class UserPresenter implements IUserPresenter {

    private static final String TAG ="UserPresenter----" ;
    private IUserView iUserView;
    private final UserModel userModel;

    public UserPresenter(IUserView iUserView) {
        this.iUserView = iUserView;
        userModel = new UserModel();

    }

    @Override
    public void userParams(String uid) {
        userModel.getUserInfo(uid,this);
    }

    @Override
    public void onSuccess(UserInfoBean userInfoBean) {
        iUserView.getUserSuccess(userInfoBean);

    }

    @Override
    public void onFailed(String error) {
        Log.d(TAG, "onFailed: "+error);
    }

    @Override
    public void onDestory() {
        if(null!=iUserView){
            iUserView=null;
        }

    }
}
