package com.rookie.imitationjd.presenter;

import android.util.Log;

import com.rookie.imitationjd.bean.SearchBean;
import com.rookie.imitationjd.model.SearchModel;
import com.rookie.imitationjd.view.interfaces.ISYView;
import com.rookie.imitationjd.view.interfaces.ISearchView;

import java.util.List;

/**
 * Created by 暗夜 on 2018/5/26.
 */

public class SearchPresenter implements ISearchPresenter {

    private static final String TAG ="SearchPresenter----" ;
    private ISearchView iSearchView;
    private final SearchModel searchModel;

    public SearchPresenter(ISearchView iSearchView) {
        this.iSearchView = iSearchView;
        searchModel = new SearchModel();
    }

    @Override
    public void searchParams(String keywords, String page) {
        searchModel.getSearch(keywords,page,this);
    }

    @Override
    public void onSuccess(List<SearchBean.DataBean> data) {
        iSearchView.showSearchData(data);

    }

    @Override
    public void onFailed(String error) {
        Log.d(TAG, "onFailed: "+error.toString());

    }

    //解绑
    @Override
    public void onDestory() {
        if(null!=iSearchView){
            iSearchView=null;
        }

    }
}
