package com.rookie.imitationjd.view.activities;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.LoginBean;
import com.rookie.imitationjd.presenter.LoginPresenter;
import com.rookie.imitationjd.view.interfaces.ILoginView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private static final String TAG = "LoginActivity----";
    @BindView(R.id.et_mobile)
    EditText mEtMobile;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.register)
    TextView mRegister;
    @BindView(R.id.third_qq)
    Button mThirdQq;
    @BindView(R.id.login_cancel)
    ImageView mLoginCancel;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initQQ();

        loginPresenter = new LoginPresenter(this);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile = mEtMobile.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();

                Log.d(TAG, "onClick: " + mobile + "-----" + password);
                //登录判断
                boolean b = isPhoneNumber(mobile);

                if (mobile.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!b) {
                    Toast.makeText(LoginActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "密码不能少于六位数", Toast.LENGTH_SHORT).show();
                } else {

                    //如果合法就启动登录的方法
                    loginPresenter.loginParams(mobile, password);
                }


            }
        });


    }

    private void initQQ() {
        //Android6.0设置动态权限
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }

    }

    //登录授权的监听
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_LONG).show();

            //如果登录成功，就把状态值记录在sharedPreferences中
           /* SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("isLogin", true);
            edit.commit();
            finish();*/
            loginPresenter.loginParams("13634252028", "123456");

           /* Set<String> keySet = data.keySet();

            for(String key:keySet){
                String value = data.get(key);
                Log.d(TAG,"信息" + platform + "---" + action + "---" + key + "----" + value);
                userInfo.append("信息" + platform + "---" + action + "---" + key + "----" + value+"\r\n");

            }*/
        }
        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
        }
        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


    }
    // QQ,新浪
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    //正则表达式
    private boolean isPhoneNumber(String phoneStr) {
        //定义电话格式的正则表达式
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        //设定查看模式
        Pattern p = Pattern.compile(regex);
        //判断Str是否匹配，返回匹配结果
        Matcher m = p.matcher(phoneStr);
        return m.find();
    }

    @Override
    public void getLoginSuccess(LoginBean loginBean) {
        String code = loginBean.getCode();
        if ("0".equals(code)) {

            //如果登录成功，就把状态值记录在sharedPreferences中
            SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("isLogin", true);
            int uid = loginBean.getData().getUid();
            edit.putString("uid", uid + "");
            edit.commit();
            finish();
            Toast.makeText(LoginActivity.this, loginBean.getMsg().toString(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(LoginActivity.this, loginBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getLoginFail(String error) {
        Toast.makeText(LoginActivity.this, "登录失败！请检查登录信息", Toast.LENGTH_SHORT).show();
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestory();
    }


    @OnClick({R.id.login_cancel, R.id.register, R.id.third_qq})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_cancel:
                finish();
                break;
            case R.id.register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
                //QQ第三方登录
            //登录授权的点击事件
            case R.id.third_qq:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
        }
    }
}
