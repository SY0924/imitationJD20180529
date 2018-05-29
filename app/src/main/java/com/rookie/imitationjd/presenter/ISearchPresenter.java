package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.InfoBean;
import com.rookie.imitationjd.bean.SearchBean;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/26.
 */

public interface ISearchPresenter {
    void searchParams(String keywords,String page);
    void onSuccess(List<SearchBean.DataBean> data);
    void onFailed(String error);
    void onDestory();
}
