package com.rookie.imitationjd.presenter;

import android.util.Log;

import com.rookie.imitationjd.bean.UpdateNameBean;
import com.rookie.imitationjd.bean.UploadBean;
import com.rookie.imitationjd.model.UploadModel;
import com.rookie.imitationjd.view.interfaces.IUploadView;

import okhttp3.MultipartBody;

/**
 * Created by 暗夜 on 2018/5/24.
 */

public class UploadPresenter implements IUploadPresenter {

    private static final String TAG ="UploadPresenter----" ;
    private IUploadView iUploadView;
    private final UploadModel uploadModel;

    public UploadPresenter(IUploadView iUploadView) {
        this.iUploadView = iUploadView;
        uploadModel = new UploadModel();
    }
//上传头像
    @Override
    public void uploads(String uid, MultipartBody.Part file) {
        uploadModel.uploadParams(uid,file,this);
    }

    @Override
    public void onSuccess(UploadBean uploadBean) {
        iUploadView.showUploadData(uploadBean);

    }

    @Override
    public void onFailed(String error) {
        Log.d(TAG, "onFailed: "+error.toString());
    }

    @Override
    public void onDestory() {
        if(null!=iUploadView){
            iUploadView=null;
        }

    }
//修改昵称
    @Override
    public void updateParams(String uid, String nickname) {
        uploadModel.updateName(uid,nickname,this);
    }

    @Override
    public void onupdateSuccess(UpdateNameBean updateNameBean) {
        iUploadView.showUpdateNameData(updateNameBean);
    }
}
