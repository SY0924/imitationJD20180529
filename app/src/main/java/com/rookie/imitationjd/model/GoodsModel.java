package com.rookie.imitationjd.model;

import com.rookie.imitationjd.bean.GoodsBean;
import com.rookie.imitationjd.presenter.IGoodsPresenter;
import com.rookie.imitationjd.utils.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 暗夜 on 2018/5/25.
 */

public class GoodsModel implements IGoodsModel {
    @Override
    public void goodsParams(String pscid, String page, String sort, final IGoodsPresenter iGoodsPresenter) {
        Observable<GoodsBean> goods = RetrofitUtils.getInstance().getApi().getGoods(pscid, page, sort);
        goods.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GoodsBean value) {
                        List<GoodsBean.DataBean> data = value.getData();
                        iGoodsPresenter.onSuccess(data);

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
