package com.rookie.imitationjd.model;

import com.rookie.imitationjd.presenter.ISearchPresenter;

/**
 * Created by 暗夜 on 2018/5/26.
 */

public interface ISearchModel {
    void getSearch(String keywords,String page,ISearchPresenter iSearchPresenter);
}
