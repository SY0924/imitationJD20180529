package com.rookie.imitationjd.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.UserInfoBean;
import com.rookie.imitationjd.presenter.UserPresenter;
import com.rookie.imitationjd.view.interfaces.IUserView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity implements IUserView {

    @BindView(R.id.user_back)
    ImageView mUserBack;
    @BindView(R.id.user_name)
    TextView mUserName;
    //用户信息条目
    @BindView(R.id.user_lin_item)
    LinearLayout mUserLinItem;
    //退出登录
    @BindView(R.id.login_off)
    Button mLoginOff;
    @BindView(R.id.user_icon)
    SimpleDraweeView mUserIcon;
    private UserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        userPresenter = new UserPresenter(this);


    }


    @OnClick({R.id.user_back, R.id.user_lin_item, R.id.login_off})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.user_back:
                finish();
                break;
            case R.id.user_lin_item:
                Intent intent = new Intent(UserActivity.this, PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.login_off:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定退出登录吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean("isLogin", false);
                        edit.commit();
                        finish();

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create();
                builder.show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences data = getSharedPreferences("user", Context.MODE_PRIVATE);
        String uid = data.getString("uid", "");
        userPresenter.userParams(uid);
    }

    @Override
    public void getUserSuccess(UserInfoBean userInfoBean) {
        String code = userInfoBean.getCode();
        if (userInfoBean != null) {
            if("0".equals(code)){
                String icon = userInfoBean.getData().getIcon();
                mUserIcon.setImageURI(icon);
                mUserName.setText((String)userInfoBean.getData().getNickname());
            }else{
                Toast.makeText(UserActivity.this,userInfoBean.getMsg().toString(),Toast.LENGTH_SHORT).show();
            }

        }


    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPresenter.onDestory();
    }
}
