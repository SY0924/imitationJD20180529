package com.rookie.imitationjd.model;

import android.util.Log;

import com.rookie.imitationjd.bean.AddCarBean;
import com.rookie.imitationjd.bean.InfoBean;
import com.rookie.imitationjd.presenter.IInfoPresenter;
import com.rookie.imitationjd.utils.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public class InfoModel implements IInfoModel {
    private static final String TAG ="InfoModel----" ;
//商品详情
    @Override
    public void infoParams(String pid, final IInfoPresenter iInfoPresenter) {
        Observable<InfoBean> info = RetrofitUtils.getInstance().getApi().getInfo(pid);
        info.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InfoBean value) {
                        Log.d(TAG, "onNext: "+value);
                        iInfoPresenter.onSuccess(value);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
//添加购物车
    @Override
    public void addCars(String uid, String pid, final IInfoPresenter iInfoPresenter) {


        Observable<AddCarBean> addCarBeanObservable = RetrofitUtils.getInstance().getApi().addCar(uid, pid);

        addCarBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddCarBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddCarBean value) {
                        iInfoPresenter.addCarSuccess(value);

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
