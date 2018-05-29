package com.rookie.imitationjd.model;

import com.rookie.imitationjd.bean.SearchBean;
import com.rookie.imitationjd.presenter.ISearchPresenter;
import com.rookie.imitationjd.utils.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 暗夜 on 2018/5/26.
 */

public class SearchModel implements ISearchModel{
    @Override
    public void getSearch(String keywords, String page, final ISearchPresenter iSearchPresenter) {
        Observable<SearchBean> search = RetrofitUtils.getInstance().getApi().search(keywords, page);
        search.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchBean value) {
                        List<SearchBean.DataBean> data = value.getData();
                        iSearchPresenter.onSuccess(data);

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
