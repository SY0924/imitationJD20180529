package com.rookie.imitationjd.model;

import com.rookie.imitationjd.bean.UserInfoBean;
import com.rookie.imitationjd.presenter.IUserPresenter;
import com.rookie.imitationjd.utils.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public class UserModel implements IUserModel {
    @Override
    public void getUserInfo(String uid, final IUserPresenter iUserPresenter) {
        Observable<UserInfoBean> userInfoBeanObservable = RetrofitUtils.getInstance().getApi().userInfo(uid);
        userInfoBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoBean value) {
                        iUserPresenter.onSuccess(value);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
