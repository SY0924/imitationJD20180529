package com.rookie.imitationjd.model;

import com.rookie.imitationjd.presenter.IUploadPresenter;

import okhttp3.MultipartBody;

/**
 * Created by 暗夜 on 2018/5/24.
 */

public interface IUploadModel {
    //上传头像
    void uploadParams(String uid, MultipartBody.Part file,IUploadPresenter iUploadPresenter);
    //修改昵称
    void updateName(String uid,String nickname,IUploadPresenter iUploadPresenter);
}
