package com.rookie.imitationjd.view.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.adapter.SYAdapter;
import com.rookie.imitationjd.adapter.SYTJAdapter;
import com.rookie.imitationjd.bean.SYBannerBean;
import com.rookie.imitationjd.bean.SYNavBean;
import com.rookie.imitationjd.bean.UserInfoBean;
import com.rookie.imitationjd.model.SYModel;
import com.rookie.imitationjd.presenter.SYPresenter;
import com.rookie.imitationjd.presenter.UserPresenter;
import com.rookie.imitationjd.utils.ThemeChangeUtil;
import com.rookie.imitationjd.view.activities.LoginActivity;
import com.rookie.imitationjd.view.activities.NightActivity;
import com.rookie.imitationjd.view.activities.UserActivity;
import com.rookie.imitationjd.view.interfaces.ISYView;
import com.rookie.imitationjd.view.interfaces.IUserView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by 暗夜 on 2018/5/21.
 */

public class WoDeFragment extends Fragment implements IUserView,ISYView {
    //未登录的头部图片
    @BindView(R.id.wd_head)
    ImageView mWdHead;
    //登录成功后的头部图片
    @BindView(R.id.wd_anotherhead)
    ImageView mWdAnotherhead;
    @BindView(R.id.userIcon)
    SimpleDraweeView mUserIcon;
    @BindView(R.id.LoginName)
    TextView mLoginName;
    @BindView(R.id.wRcy)
    RecyclerView mWRcy;
    private View view;
    private RelativeLayout loginReg;
    private Unbinder unbinder;

    private int num;
    private UserPresenter userPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_layout_wd, null);
        unbinder = ButterKnife.bind(this, view);

        LinearLayout night_btn = view.findViewById(R.id.night_btn);
        night_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), NightActivity.class));
            }
        });


        loginReg = view.findViewById(R.id.rlt);
        loginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (num) {
                    case 0:
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent, 100);
                        break;
                    case 1:
                        Intent intents = new Intent(getActivity(), UserActivity.class);
                        startActivity(intents);
                        break;
                }
            }
        });
            initData();

        return view;
    }

    private void initData() {

        SYPresenter syPresenter = new SYPresenter();
        syPresenter.showBannerToView(new SYModel(),this);
        //设置默认分隔线
        DividerItemDecoration dec = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        DividerItemDecoration decs = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        mWRcy.addItemDecoration(dec);
        mWRcy.addItemDecoration(decs);
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = getContext().getSharedPreferences("user", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        String uid = preferences.getString("uid", "13576");
        if (isLogin) {

            userPresenter = new UserPresenter(this);
            userPresenter.userParams(uid);


        } else {
            num = 0;
            mWdAnotherhead.setVisibility(View.GONE);
            mWdHead.setVisibility(View.VISIBLE);
            mLoginName.setText("登录/注册 >");
            mUserIcon.setImageResource(R.drawable.user);
        }

        Log.e(TAG, "onResume: " + isLogin + uid);
    }


    @Override
    public void getUserSuccess(UserInfoBean userInfoBean) {
        UserInfoBean.DataBean data = userInfoBean.getData();
        String code = userInfoBean.getCode();
        if ("0".equals(code)) {
            num = 1;
            mLoginName.setText((String) data.getNickname());
            String icon = data.getIcon();
            mUserIcon.setImageURI(icon);
            mWdAnotherhead.setVisibility(View.VISIBLE);
            mWdHead.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), userInfoBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        userPresenter.onDestory();
    }

    @Override
    public void showSYNavData(List<SYNavBean.DataBean> data) {

    }

    @Override
    public void showSYBannerData(SYBannerBean syBannerBean) {

        List<SYBannerBean.TuijianBean.ListBean> list = syBannerBean.getTuijian().getList();
        SYTJAdapter SYTJAdapter =new SYTJAdapter(getActivity(),list);
        mWRcy.setAdapter(SYTJAdapter);
        mWRcy.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));


    }
}
