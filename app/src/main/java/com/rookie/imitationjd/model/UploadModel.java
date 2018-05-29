package com.rookie.imitationjd.model;

import com.rookie.imitationjd.bean.UpdateNameBean;
import com.rookie.imitationjd.bean.UploadBean;
import com.rookie.imitationjd.presenter.IUploadPresenter;
import com.rookie.imitationjd.utils.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * Created by 暗夜 on 2018/5/24.
 */

public class UploadModel implements IUploadModel {
//上传头像
    @Override
    public void uploadParams(String uid, MultipartBody.Part file, final IUploadPresenter iUploadPresenter) {
        Observable<UploadBean> upload = RetrofitUtils.getInstance().getApi().upload(uid, file);
        upload.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadBean value) {
                        iUploadPresenter.onSuccess(value);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
//修改昵称
    @Override
    public void updateName(String uid, String nickname, final IUploadPresenter iUploadPresenter) {
        Observable<UpdateNameBean> updateNameBeanObservable = RetrofitUtils.getInstance().getApi().updateName(uid, nickname);
        updateNameBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateNameBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpdateNameBean value) {
                        iUploadPresenter.onupdateSuccess(value);

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
