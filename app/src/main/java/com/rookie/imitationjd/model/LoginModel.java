package com.rookie.imitationjd.model;

import com.rookie.imitationjd.bean.LoginBean;
import com.rookie.imitationjd.presenter.ILoginPresenter;
import com.rookie.imitationjd.utils.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public class LoginModel implements ILoginModel {
    @Override
    public void getLogin(String mobile, String password, final ILoginPresenter iLoginPresenter) {
        Observable<LoginBean> login = RetrofitUtils.getInstance().getApi().login(mobile, password);
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean value) {
                        iLoginPresenter.onSuccess(value);

                    }

                    @Override
                    public void onError(Throwable e) {

                        iLoginPresenter.onFailed(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
