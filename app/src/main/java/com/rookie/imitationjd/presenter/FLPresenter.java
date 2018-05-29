package com.rookie.imitationjd.presenter;

import android.util.Log;
import com.google.gson.Gson;
import com.rookie.imitationjd.bean.ClassBean;
import com.rookie.imitationjd.bean.SYNavBean;
import com.rookie.imitationjd.httpconfig.HttpApi;
import com.rookie.imitationjd.model.IFLModel;
import com.rookie.imitationjd.model.SetModelListener;
import com.rookie.imitationjd.view.interfaces.IFLView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 暗夜 on 2018/4/28.
 */

public class FLPresenter implements IFLPresenter {

    private static final String TAG ="FLPresenter----" ;




    //分类左边导航栏数据
    @Override
    public void showLeftToView(IFLModel iflModel, final IFLView iflView) {
        Map<String, String> params = new HashMap<>();
        iflModel.getFLLeftData(HttpApi.NavUrl, params, new SetModelListener() {
            @Override
            public void getSuccess(String json) {
                Log.d(TAG, "getSuccess: "+json);
                Gson gson=new Gson();
                SYNavBean syNavBean = gson.fromJson(json,SYNavBean.class);
                List<SYNavBean.DataBean> data = syNavBean.getData();

                iflView.showFLLeftData(data);
            }

            @Override
            public void getError(String error) {

            }
        });

    }

    //分类右边导航栏数据
    @Override
    public void showRightToView(IFLModel iflModel, final IFLView iflView) {
        Map<String, String> map = new HashMap<>();
        Log.d(TAG, "showRightToView: "+iflView.getCid());
        map.put("cid",iflView.getCid());
        iflModel.getFLRightData(HttpApi.ClassUrl, map, new SetModelListener() {
            @Override
            public void getSuccess(String json) {
                Log.d(TAG, "getSuccess: "+json);
                Gson gson=new Gson();
                ClassBean classBean = gson.fromJson(json, ClassBean.class);
                List<ClassBean.DataBean> data = classBean.getData();
                iflView.showFLRightData(data);

            }

            @Override
            public void getError(String error) {
                Log.d(TAG, "getError: "+error);
            }
        });

    }
}
