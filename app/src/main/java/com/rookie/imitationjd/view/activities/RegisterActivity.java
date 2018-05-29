package com.rookie.imitationjd.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.RegisterBean;
import com.rookie.imitationjd.presenter.RegPresenter;
import com.rookie.imitationjd.view.interfaces.IRegView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements IRegView {

    private static final String TAG ="RegisterActivity----";
    @BindView(R.id.regist_back)
    ImageView mRegistBack;
    @BindView(R.id.reg_tel)
    EditText mRegTel;
    @BindView(R.id.reg_password)
    EditText mRegPassword;
    @BindView(R.id.btn_regist)
    Button mBtnRegist;
    private RegPresenter regPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        regPresenter = new RegPresenter(this);

        mRegistBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regMobile = mRegTel.getText().toString().trim();
                String regPassword = mRegPassword.getText().toString().trim();

                Log.d(TAG, "onClick: " + regMobile + "-----" + regPassword);
                    //注册判断
                boolean b = isPhoneNumber(regMobile);
                if (regMobile.isEmpty() || regPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!b) {
                    Toast.makeText(RegisterActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
                } else if (regPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "密码不能少于六位数", Toast.LENGTH_SHORT).show();
                } else {
                    //如果合法就注册
                    regPresenter.regParams(regMobile,regPassword);
                }
            }
        });


    }
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
    public void showRegData(RegisterBean registerBean) {

        String code = registerBean.getCode();
        if("0".equals(code)){
            Toast.makeText(RegisterActivity.this, registerBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(RegisterActivity.this, registerBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
        }


    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        regPresenter.onDestory();

    }
}
