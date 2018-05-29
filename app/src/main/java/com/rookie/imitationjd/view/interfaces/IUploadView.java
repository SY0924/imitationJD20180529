package com.rookie.imitationjd.view.interfaces;

import com.rookie.imitationjd.bean.UpdateNameBean;
import com.rookie.imitationjd.bean.UploadBean;

/**
 * Created by 暗夜 on 2018/5/24.
 */

public interface IUploadView {
    //上传头像
    void showUploadData(UploadBean uploadBean);
        //修改昵称
    void showUpdateNameData(UpdateNameBean updateNameBean);
}
