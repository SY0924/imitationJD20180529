package com.rookie.imitationjd.presenter;

import com.rookie.imitationjd.bean.UpdateNameBean;
import com.rookie.imitationjd.bean.UploadBean;
import com.rookie.imitationjd.bean.UserInfoBean;

import okhttp3.MultipartBody;

/**
 * Created by 暗夜 on 2018/5/24.
 */

public interface IUploadPresenter {
    void uploads(String uid, MultipartBody.Part file);
    void onSuccess(UploadBean uploadBean);
    void onFailed(String error);
    void onDestory();

    void updateParams(String uid,String nickname);
    void onupdateSuccess(UpdateNameBean updateNameBean);

}
