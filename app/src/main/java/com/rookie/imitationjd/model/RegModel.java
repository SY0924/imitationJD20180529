package com.rookie.imitationjd.model;

import com.rookie.imitationjd.bean.RegisterBean;
import com.rookie.imitationjd.presenter.IRegPresenter;
import com.rookie.imitationjd.utils.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public class RegModel implements IRegModel {
    @Override
    public void getReg(String mobile, String password, final IRegPresenter iRegPresenter) {
        Observable<RegisterBean> register = RetrofitUtils.getInstance().getApi().register(mobile, password);
        register.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterBean value) {
                        iRegPresenter.onSuccess(value);

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
