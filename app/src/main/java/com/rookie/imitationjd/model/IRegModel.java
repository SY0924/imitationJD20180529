package com.rookie.imitationjd.model;

import com.rookie.imitationjd.presenter.IRegPresenter;

/**
 * Created by 暗夜 on 2018/5/23.
 */

public interface IRegModel {
    void getReg(String mobile,String password,IRegPresenter iRegPresenter);
}
