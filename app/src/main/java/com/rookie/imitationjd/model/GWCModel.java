package com.rookie.imitationjd.model;

import com.rookie.imitationjd.bean.DelCarBean;
import com.rookie.imitationjd.bean.ShopCarBean;
import com.rookie.imitationjd.presenter.IGWCPresenter;
import com.rookie.imitationjd.utils.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public class GWCModel implements IGWCModel {
    //查询购物车
    @Override
    public void getCar(String uid, final IGWCPresenter igwcPresenter) {
        Observable<ShopCarBean> cars = RetrofitUtils.getInstance().getApi().getCars(uid);
        cars.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopCarBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShopCarBean value) {
                        List<ShopCarBean.DataBean> data = value.getData();
                        igwcPresenter.onSuccess(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        igwcPresenter.onFailed(e.getMessage());

                    }
                    @Override
                    public void onComplete() {

                    }
                });

    }
        //删除购物车
    @Override
    public void delCar(String uid, String pid, final IGWCPresenter igwcPresenter) {
        Observable<DelCarBean> dels = RetrofitUtils.getInstance().getApi().getDels(uid, pid);
        dels.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DelCarBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DelCarBean value) {
                        String msg = value.getMsg();
                        igwcPresenter.delSuccess(msg);


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
