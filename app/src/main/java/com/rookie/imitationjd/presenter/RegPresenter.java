package com.rookie.imitationjd.presenter;

import android.util.Log;

import com.rookie.imitationjd.bean.RegisterBean;
import com.rookie.imitationjd.model.RegModel;
import com.rookie.imitationjd.view.interfaces.IRegView;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public class RegPresenter implements IRegPresenter {

    private static final String TAG ="RegPresenter----" ;
    private IRegView iRegView;
    private final RegModel regModel;

    public RegPresenter(IRegView iRegView) {
        this.iRegView = iRegView;
        regModel = new RegModel();
    }

    @Override
    public void regParams(String mobile, String password) {
        regModel.getReg(mobile,password,this);

    }

    @Override
    public void onSuccess(RegisterBean registerBean) {
        iRegView.showRegData(registerBean);

    }

    @Override
    public void onFailed(String error) {
        Log.d(TAG, "onFailed: "+error);

    }

    @Override
    public void onDestory() {
        if(null!=iRegView){
            iRegView=null;
        }

    }
}
